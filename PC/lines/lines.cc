/*
 * Copyright (c) 1993-1997, Silicon Graphics, Inc.
 * ALL RIGHTS RESERVED
 * Permission to use, copy, modify, and distribute this software for
 * any purpose and without fee is hereby granted, provided that the above
 * copyright notice appear in all copies and that both the copyright notice
 * and this permission notice appear in supporting documentation, and that
 * the name of Silicon Graphics, Inc. not be used in advertising
 * or publicity pertaining to distribution of the software without specific,
 * written prior permission.
 *
 * THE MATERIAL EMBODIED ON THIS SOFTWARE IS PROVIDED TO YOU "AS-IS"
 * AND WITHOUT WARRANTY OF ANY KIND, EXPRESS, IMPLIED OR OTHERWISE,
 * INCLUDING WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY OR
 * FITNESS FOR A PARTICULAR PURPOSE.  IN NO EVENT SHALL SILICON
 * GRAPHICS, INC.  BE LIABLE TO YOU OR ANYONE ELSE FOR ANY DIRECT,
 * SPECIAL, INCIDENTAL, INDIRECT OR CONSEQUENTIAL DAMAGES OF ANY
 * KIND, OR ANY DAMAGES WHATSOEVER, INCLUDING WITHOUT LIMITATION,
 * LOSS OF PROFIT, LOSS OF USE, SAVINGS OR REVENUE, OR THE CLAIMS OF
 * THIRD PARTIES, WHETHER OR NOT SILICON GRAPHICS, INC.  HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH LOSS, HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, ARISING OUT OF OR IN CONNECTION WITH THE
 * POSSESSION, USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 * US Government Users Restricted Rights
 * Use, duplication, or disclosure by the Government is subject to
 * restrictions set forth in FAR 52.227.19(c)(2) or subparagraph
 * (c)(1)(ii) of the Rights in Technical Data and Computer Software
 * clause at DFARS 252.227-7013 and/or in similar or successor
 * clauses in the FAR or the DOD or NASA FAR Supplement.
 * Unpublished-- rights reserved under the copyright laws of the
 * United States.  Contractor/manufacturer is Silicon Graphics,
 * Inc., 2011 N.  Shoreline Blvd., Mountain View, CA 94039-7311.
 *
 * OpenGL(R) is a registered trademark of Silicon Graphics, Inc.
 */

/*
 * hello.c
 * This is a simple, introductory OpenGL program.
 */

#include <GL/glew.h>
#include <GL/glut.h>
#include <stdio.h>
#include "shader.h"

struct MyVertex {
    float point[3];
    unsigned char color[4];
};

void drawLines1(GLfloat data[], int points) {
    glColor3f(1.0, 0, 0);
    glBegin(GL_LINES);
        for (int i=0; i<points; ++i) {
            glVertex3f(data[i * 3], data[i * 3 + 1], data[i * 3 + 2]);
        }
    glEnd();
}

void drawLines2(GLfloat data[], int points) {
    MyVertex * tmpBuffer = new MyVertex[points];
    for (int i=0; i<points; ++i) {
        tmpBuffer[i].point[0] = data[i * 3];
        tmpBuffer[i].point[1] = data[i * 3 + 1];
        tmpBuffer[i].point[2] = data[i * 3 + 2];
        tmpBuffer[i].color[0] = 255;
        tmpBuffer[i].color[1] = 0;
        tmpBuffer[i].color[2] = 0;
        tmpBuffer[i].color[3] = 100;
    }
    
    GLuint vbo;
    glGenBuffers(1, &vbo);
    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    glBufferData(GL_ARRAY_BUFFER, sizeof(MyVertex) * points, tmpBuffer, GL_STATIC_DRAW);
    
    glVertexPointer(3, GL_FLOAT, sizeof(MyVertex), 0);
    glColorPointer(4, GL_UNSIGNED_BYTE, sizeof(MyVertex), (const void*) (3*sizeof(float)));

    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_COLOR_ARRAY);

    glDrawArrays(GL_LINES, 0, points);

    glDisableClientState(GL_COLOR_ARRAY);
    glDisableClientState(GL_VERTEX_ARRAY);

    glBindBuffer(GL_ARRAY_BUFFER, 0);
}

void drawLines3(GLfloat data[], int points) {
    // Create and compile our GLSL program from the shaders
    GLuint programID = LoadShaders( "SimpleVertexShader.vertexshader", "SimpleFragmentShader.fragmentshader" );
    
    // Use our shader
    glUseProgram(programID);

    // This will identify our vertex buffer
    GLuint vertexbuffer;
    // Generate 1 buffer, put the resulting identifier in vertexbuffer
    glGenBuffers(1, &vertexbuffer);
    // The following commands will talk about our 'vertexbuffer' buffer
    glBindBuffer(GL_ARRAY_BUFFER, vertexbuffer);
    // Give our vertices to OpenGL.
    glBufferData(GL_ARRAY_BUFFER, points * 3 * sizeof(GLfloat), data, GL_STATIC_DRAW);

    // 1rst attribute buffer : vertices
    glEnableVertexAttribArray(0);
    glBindBuffer(GL_ARRAY_BUFFER, vertexbuffer);
    glVertexAttribPointer(
        0,                  // attribute 0. No particular reason for 0, but must match the layout in the shader.
        3,                  // size
        GL_FLOAT,           // type
        GL_FALSE,           // normalized?
        0,                  // stride
        (void*)0            // array buffer offset
    );
    // Draw the lines !
    glDrawArrays(GL_LINES, 0, points);
    glDisableVertexAttribArray(0);
}

void display(void)
{
    /* clear all pixels  */
    glClear (GL_COLOR_BUFFER_BIT);

    // An array of 3 vectors which represents 3 vertices
    static GLfloat g_vertex_buffer_data[] = {
       -1.0f, -1.0f, 0.0f,
       1.0f, 1.0f, 0.0f,
       0.0f, 0.0f, 0.0f,
       1.0f, 0.0f, 0.0f,
    };
    int points = (sizeof(g_vertex_buffer_data)/sizeof(g_vertex_buffer_data[0])) / 3;

    int choose_method = 3;
    switch (choose_method) {
        case 1:
            drawLines1(g_vertex_buffer_data, points);
            break;
        case 2:
            drawLines2(g_vertex_buffer_data, points);
            break;
        case 3:
            drawLines3(g_vertex_buffer_data, points);
            break;
    }

    /* don't wait!
     * start processing buffered OpenGL routines
     */
    glFlush ();
}

void init (void)
{
    glewExperimental = GL_TRUE;
    glewInit();

    /* select clearing color    */
    glClearColor (0.0, 0.0, 0.0, 0.0);

    /* initialize viewing values  */
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);

    GLuint VertexArrayID;
    glGenVertexArrays(1, &VertexArrayID);
    glBindVertexArray(VertexArrayID);
}

/*
 * Declare initial window size, position, and display mode
 * (single buffer and RGBA).  Open window with "hello"
 * in its title bar.  Call initialization routines.
 * Register callback function to display graphics.
 * Enter main loop and process events.
 */
int main(int argc, char** argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode (GLUT_SINGLE | GLUT_RGB);
    glutInitWindowSize (250, 250);
    glutInitWindowPosition (100, 100);
    glutCreateWindow ("lines");
    init ();
    glutDisplayFunc(display);
    glutMainLoop();
    return 0;   /* ANSI C requires main to return int. */
}

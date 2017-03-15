/**
 * Created by felixslu on 2016/11/1.
 */

package com.example.opengldemo;

import android.opengl.GLES10;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Simple class to render a coloured cube.
 */
public class Cube {

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mColorBuffer;
    private ByteBuffer mIndexBuffer;
    private float mRotation = .0f;

    public Cube() {
        this(1.0f);
    }

    public Cube(float size) {
        this(size, 0.0f, 0.0f, 0.0f);
    }

    public Cube(float size, float x, float y, float z) {
        setArrays(size, x, y, z);
    }

    public FloatBuffer getmVertexBuffer() {
        return mVertexBuffer;
    }

    public FloatBuffer getmColorBuffer() {
        return mColorBuffer;
    }

    public ByteBuffer getmIndexBuffer() {
        return mIndexBuffer;
    }

    private void setArrays(float size, float x, float y, float z) {

        float hs = size / 2.0f;

        float vertices[] = {
                x - hs, y - hs, z - hs, // 0
                x + hs, y - hs, z - hs, // 1
                x + hs, y + hs, z - hs, // 2
                x - hs, y + hs, z - hs, // 3
                x - hs, y - hs, z + hs, // 4
                x + hs, y - hs, z + hs, // 5
                x + hs, y + hs, z + hs, // 6
                x - hs, y + hs, z + hs, // 7
        };

        float c = 1.0f;
        float colors[] = {
                0, 0, 0, c, // 0 black
                c, 0, 0, c, // 1 red
                c, c, 0, c, // 2 yellow
                0, c, 0, c, // 3 green
                0, 0, c, c, // 4 blue
                c, 0, c, c, // 5 magenta
                c, c, c, c, // 6 white
                0, c, c, c, // 7 cyan
        };

        byte indices[] = {
                0, 4, 5, 0, 5, 1,
                1, 5, 6, 1, 6, 2,
                2, 6, 7, 2, 7, 3,
                3, 7, 4, 3, 4, 0,
                4, 7, 6, 4, 6, 5,
                3, 0, 1, 3, 1, 2
        };


        mVertexBuffer = RenderUtils.buildFloatBuffer(vertices);
        mColorBuffer = RenderUtils.buildFloatBuffer(colors);
        mIndexBuffer = RenderUtils.buildByteBuffer(indices);

    }

    public void draw(GL10 gl) {

        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GLES10.GL_FRONT_AND_BACK);

        GLES10.glColorPointer(4, GLES10.GL_FLOAT, 0, mColorBuffer);
        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, mVertexBuffer);

        GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);

        gl.glRotatef(mRotation, 1.0f, 1.0f, 1.0f);
        GLES10.glDrawElements(GLES10.GL_TRIANGLES, 36, GLES10.GL_UNSIGNED_BYTE, mIndexBuffer);

        GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);

        mRotation += 0.5f;
    }
}
package com.example.opengldemo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Square mSquare;
    private Cube mCube;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.7f, 0.7f, 0.7f, 1.0f);
        mTriangle = new Triangle();
        mSquare = new Square();
        mCube = new Cube();
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);

        // make adjustments for screen ratio
        float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
        gl.glLoadIdentity();                        // reset the matrix to its default state
        // 把y坐标跨度定为(-1,1)，那么相应x坐标跨度为(-ratio,ratio)。这将OpenGL坐标和屏幕坐标比联系起来
        /**
         * 截取场景
         * void glFrustum(GLdouble left,
         *   GLdouble right,
         *   GLdouble bottom,
         *   GLdouble top,
         *   GLdouble nearVal,
         *   GLdouble farVal)
         *   
         *   left, right: Specify the coordinates for the left and right vertical clipping planes.
         *   bottom, top: Specify the coordinates for the bottom and top horizontal clipping planes.
         *   nearVal, farVal: Specify the distances to the near and far depth clipping planes. Both distances must be positive.
         */
        gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);  // apply the projection matrix
//        gl.glOrthof(-ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // Set GL_MODELVIEW transformation mode
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();                      // reset the matrix to its default state

        // When using GL_MODELVIEW, you must set the camera view
        // 把自己的眼睛当成是照相机，前三个参数表示眼睛的坐标，中间三个参数表示要拍照的物体的中心位置，可以理解成焦点吧，
        // 后三个参数表示头顶的朝向
        GLU.gluLookAt(gl, 0, 0, 5, 0f, 0f, 0f, 0.0f, 1.0f, 0.0f);
        
//        mTriangle.draw(gl);
//        mSquare.draw(gl);
        mCube.draw(gl);
    }
}

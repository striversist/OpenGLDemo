package com.example.opengldemo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Square mSquare;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.7f, 0.7f, 0.7f, 1.0f);
        mTriangle = new Triangle();
        mSquare = new Square();
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        
//        mTriangle.draw(gl);
        mSquare.draw(gl);
    }
}

package com.example.opengldemo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Triangle {

    private FloatBuffer mVetextBuffer;
    
    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {
            // in counterclockwise order:
            0.0f,  1.0f, -0.5f,  // top
           -1.0f,  0.0f, -0.5f,  // bottom left
            1.0f,  0.0f, -0.5f,  // bottom right
    };
    static float rotation = 0.0f;
    
    public Triangle() {
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        
        mVetextBuffer = bb.asFloatBuffer();
        mVetextBuffer.put(triangleCoords);
        mVetextBuffer.position(0);
    }
    
    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        gl.glRotatef(rotation, 0.0f, 0.0f, 1.0f);
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glVertexPointer(COORDS_PER_VERTEX, GL10.GL_FLOAT, 0, mVetextBuffer);
        // 最后一个参数为数组中顶点的数目
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, triangleCoords.length / COORDS_PER_VERTEX);
        
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        
        rotation += 0.5f;
    }
}

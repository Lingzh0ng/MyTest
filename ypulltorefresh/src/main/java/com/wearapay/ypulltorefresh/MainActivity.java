package com.wearapay.ypulltorefresh;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {
  private GLSurfaceView mGLView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mGLView = new MyGLSurfaceView(this);
    setContentView(mGLView);

  }

  @Override protected void onStart() {
    super.onStart();
    System.out.println("onStart");
  }

  @Override protected void onResume() {
    super.onResume();
    System.out.println("onResume");
  }

  @Override protected void onPause() {
    super.onPause();
    System.out.println("onPause");
  }

  @Override protected void onStop() {
    super.onStop();
    System.out.println("onStop");
  }

  class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context){
      super(context);

      // Create an OpenGL ES 2.0 context
      setEGLContextClientVersion(2);

      mRenderer = new MyGLRenderer();

      // Set the Renderer for drawing on the GLSurfaceView
      setRenderer(mRenderer);
    }
  }

   class MyGLRenderer implements GLSurfaceView.Renderer {
     private Square mSquare;

    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
      GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
      mSquare = new Square();
    }

    @Override public void onSurfaceChanged(GL10 gl, int width, int height) {
      GLES20.glViewport(0, 0, width, height);
    }

    @Override public void onDrawFrame(GL10 gl) {
      GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
  }

  static class Square {

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
        -0.5f,  0.5f, 0.0f,   // top left
        -0.5f, -0.5f, 0.0f,   // bottom left
        0.5f, -0.5f, 0.0f,   // bottom right
        0.5f,  0.5f, 0.0f }; // top right

    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    public Square() {
      // initialize vertex byte buffer for shape coordinates
      ByteBuffer bb = ByteBuffer.allocateDirect(
          // (# of coordinate values * 4 bytes per float)
          squareCoords.length * 4);
      bb.order(ByteOrder.nativeOrder());
      vertexBuffer = bb.asFloatBuffer();
      vertexBuffer.put(squareCoords);
      vertexBuffer.position(0);

      // initialize byte buffer for the draw list
      ByteBuffer dlb = ByteBuffer.allocateDirect(
          // (# of coordinate values * 2 bytes per short)
          drawOrder.length * 2);
      dlb.order(ByteOrder.nativeOrder());
      drawListBuffer = dlb.asShortBuffer();
      drawListBuffer.put(drawOrder);
      drawListBuffer.position(0);
    }
  }
}

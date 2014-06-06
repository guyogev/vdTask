package com.example.hellowworld;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

/**
 * OpenGL Custom renderer used with GLSurfaceView
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
	Context context; // Application's context
	CompassBar compassBar; //

	// Constructor with global application context
	public MyGLRenderer(Context context) {
		this.context = context;
		compassBar = new CompassBar();
	}

	// Call back when the surface is first created or re-created
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Set color's clear-value to black
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		// Set depth's clear-value to farthest
		gl.glClearDepthf(1.0f);
		// Enables depth-buffer for hidden surface removal
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// nice perspective view
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		// Enable smooth shading of color
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Disable dithering for better performance
		gl.glDisable(GL10.GL_DITHER);
	}

	// Call back after onSurfaceCreated() or whenever the window's size changes
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if (height == 0)
			height = 1; // To prevent divide by zero
		float aspect = (float) width / height;

		// Set the viewport (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
		gl.glLoadIdentity(); // Reset projection matrix
		// Use perspective projection
		GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); // Select model-view matrix
		gl.glLoadIdentity(); // Reset

	}

	// Call back to draw the current frame.
	@Override
	public void onDrawFrame(GL10 gl) {
		// Clear color and depth buffers using clear-value set earlier
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glLoadIdentity(); // Reset the model-view matrix
		gl.glTranslatef(0, 0, -6.0f); // Translate into the screen
		
		// Draw the bar
		compassBar.draw(gl); 

	}
}

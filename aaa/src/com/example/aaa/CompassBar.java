package com.example.aaa;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class CompassBar {
	private FloatBuffer vertexBuffer; // Buffer for vertex-array
	private int numFaces = 6;

	// TODO rotation values from sensor, sensor update is not implemented yet...
	private float xr, yr, zr;

	private float[][] colors = { // Colors of the 6 faces
	{ 1.0f, 0.5f, 0.0f, .3f }, // 0. orange
			{ 1.0f, 0.0f, 1.0f, .3f }, // 1. violet
			{ 0.0f, 1.0f, 0.0f, .3f }, // 2. green
			{ 0.0f, 0.0f, 1.0f, .3f }, // 3. blue
			{ 1.0f, 0.0f, 0.0f, 1.0f }, // 4. red point north
			{ 1.0f, 1.0f, 0.0f, .3f } // 5. yellow
	};

	// TODO change to rectangle shape
	private float[] vertices = { // Vertices of the 6 faces
	// FRONT
			-.5f, -1.0f, .5f, // 0. left-bottom-front
			.5f, -1.0f, .5f, // 1. right-bottom-front
			-.5f, 1.0f, .5f, // 2. left-top-front
			.5f, 1.0f, .5f, // 3. right-top-front
			// BACK
			.5f, -1.0f, -.5f, // 6. right-bottom-back
			-.5f, -1.0f, -.5f, // 4. left-bottom-back
			.5f, 1.0f, -.5f, // 7. right-top-back
			-.5f, 1.0f, -.5f, // 5. left-top-back
			// LEFT
			-.5f, -1.0f, -.5f, // 4. left-bottom-back
			-.5f, -1.0f, .5f, // 0. left-bottom-front
			-.5f, 1.0f, -.5f, // 5. left-top-back
			-.5f, 1.0f, .5f, // 2. left-top-front
			// RIGHT
			.5f, -1.0f, .5f, // 1. right-bottom-front
			.5f, -1.0f, -.5f, // 6. right-bottom-back
			.5f, 1.0f, .5f, // 3. right-top-front
			.5f, 1.0f, -.5f, // 7. right-top-back
			// TOP points north
			-.5f, 1.0f, .5f, // 2. left-top-front
			.5f, 1.0f, .5f, // 3. right-top-front
			-.5f, 1.0f, -.5f, // 5. left-top-back
			.5f, 1.0f, -.5f, // 7. right-top-back
			// BOTTOM
			-.5f, -1.0f, -.5f, // 4. left-bottom-back
			.5f, -1.0f, -.5f, // 6. right-bottom-back
			-.5f, -1.0f, .5f, // 0. left-bottom-front
			.5f, -1.0f, .5f // 1. right-bottom-front
	};

	// Constructor - Set up the buffers
	public CompassBar() {
		// initialize your android device sensor capabilities
		// Setup vertex-array buffer. Vertices in float. An float has 4 bytes
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder()); // Use native byte order
		vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
		vertexBuffer.put(vertices); // Copy data into buffer
		vertexBuffer.position(0); // Rewind
	}

	// Draw the shape
	public void draw(GL10 gl) {
		// TODO update rotation should be form sensor values, this update is
		// just for show...
		// rotate
		gl.glRotatef(xr, 1f, 0, 0);
		gl.glRotatef(yr, 0, 1f, 0);
		gl.glRotatef(zr, 0, 0, 1f);

		// Front face in counter-clockwise orientation
		gl.glFrontFace(GL10.GL_CCW);
		// Enable cull face
		gl.glEnable(GL10.GL_CULL_FACE);
		// Cull the back face (don't display)
		gl.glCullFace(GL10.GL_BACK);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		// Render all the faces
		for (int face = 0; face < numFaces; face++) {
			// Set the color for each of the faces
			gl.glColor4f(colors[face][0], colors[face][1], colors[face][2],
					colors[face][3]);
			// Draw the primitive from the vertex-array directly
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
		}
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}

	/**
	 * update rotation values. rotation around x & y axis is limited to 30
	 * degrees. <br>
	 * rotation around z axis is 360. <br>
	 * for smoother transition, rotate only when rotation is greater than 1
	 * degree.
	 */
	public void update(float z, float x, float y) {
		if (Math.abs(xr - x) > 1 && Math.abs(x) < 30)
			xr = Math.round(x);
		if (Math.abs(yr - y) > 1 && Math.abs(y) < 30)
			yr = Math.round(y);
		if (Math.abs(zr - z) > 1)
			zr = Math.round(z);
	}

}
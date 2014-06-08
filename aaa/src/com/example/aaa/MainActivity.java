package com.example.aaa;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity implements
		SensorEventListener {

	private GLSurfaceView glView; // Use GLSurfaceView
	private SensorManager mSensorManager;
	MyGLRenderer renderer;

	// Call back when the activity is started, to initialize the view
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		glView = new GLSurfaceView(this); // Allocate a GLSurfaceView
		renderer = new MyGLRenderer(this);
		
		glView.setRenderer(renderer); // Use a custom renderer
		this.setContentView(glView); // This activity sets to GLSurfaceView
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

	}

	// Call back when the activity is going into the background
	@Override
	protected void onPause() {
		super.onPause();
		glView.onPause();
		mSensorManager.unregisterListener(this);

	}

	// Call back after onPause()
	@Override
	protected void onResume() {
		super.onResume();
		glView.onResume();
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		renderer.update(event.values[0], event.values[1], event.values[2]);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}
}

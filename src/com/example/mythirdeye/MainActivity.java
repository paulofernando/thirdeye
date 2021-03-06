package com.example.mythirdeye;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mythirdeye.location.DirectionOrientation;
import com.example.mythirdeye.location.Place;

public class MainActivity extends Activity implements SensorEventListener {

	private ImageView image;
	private float currentDegree = 0f;
	private Place[] pointToGo = new Place[]{new Place(-12.997819d, -38.514176d), new Place(-13.002774d, -38.506880d)};


	private DirectionOrientation directionOrientation;
	
	private SensorManager mSensorManager;

	private TextView tvHeading, tvPoints, tvDistance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		image = (ImageView) findViewById(R.id.imageViewCompass);
		tvHeading = (TextView) findViewById(R.id.tvHeading);
		tvPoints = (TextView) findViewById(R.id.tvPoints);
		tvDistance = (TextView) findViewById(R.id.tvDistance);
		
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		directionOrientation = new DirectionOrientation(pointToGo, (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE), this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), 
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// get the angle around the z-axis rotated
		float degree = Math.round(event.values[0]);
		tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
		
		int nextPoint = directionOrientation.getNextPoint();
		tvPoints.setText("Point[" + nextPoint + "] -> (" + 
				Double.toString(directionOrientation.getRoute()[nextPoint].getLatitude()) + ", " +
				Double.toString(directionOrientation.getRoute()[nextPoint].getLongitude()) + ")");		
		
		tvDistance.setText("Distance: " + directionOrientation.getDistanceToTheNextPoint() + " meters");		
		directionOrientation.conduct(degree);
		
		// create a rotation animation (reverse turn degree degrees)
		RotateAnimation ra = new RotateAnimation(currentDegree, -degree,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

		//how long the animation
		ra.setDuration(210);
		ra.setFillAfter(true);
		image.startAnimation(ra);

		currentDegree = -degree;				
	}	
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	
	@Override
	/**
	 * Finishes the application when back button is pressed
	 */
	public void onBackPressed() {
		this.finish();
		int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);		 
	}
	
}

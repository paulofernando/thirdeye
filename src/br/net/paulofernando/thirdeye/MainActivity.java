package br.net.paulofernando.thirdeye;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements SensorEventListener {

	private ImageView image;
	private float currentDegree = 0f;
	private float pointToGo = 180f;

	private SensorManager mSensorManager;

	TextView tvHeading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		image = (ImageView) findViewById(R.id.imageViewCompass);
		tvHeading = (TextView) findViewById(R.id.tvHeading);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	@Override
	protected void onResume() {
		super.onResume();

		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
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
		
		Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
				
		// create a rotation animation (reverse turn degree degrees)
		RotateAnimation ra = new RotateAnimation(currentDegree, -degree,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);

		//how long the animation
		ra.setDuration(210);
		ra.setFillAfter(true);
		image.startAnimation(ra);

		currentDegree = -degree;
				
	}
	
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

}

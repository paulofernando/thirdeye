package br.net.paulofernando.thirdeye;

import android.graphics.Point;
import android.os.Vibrator;

public class DirectionOrientation {

	/* Levels of vibration */
	private final int LEVEL_ONE = 10;
	private final int LEVEL_TWO = 60;
	
	/* Time of vibration */
	private final int VIBRATION_ONE = 500;
	private final int VIBRATION_TWO = 100;
	
	/** The route to direct the person */
	private Point[] route;
	
	private Vibrator vibrator;
	
	/**
	 * Instatiates a new direction controller
	 * @param route The route to direct the person
	 */
	public DirectionOrientation(Point[] route, Vibrator vibrator) {
		this.route = route;
		
		this.vibrator = vibrator; 
	}
	
	/**
	 * Conducts the person by the route
	 * @param currentDegree The degree where the smartphone is pointing 
	 */
	public void conduct(float currentDegree) {						
		if(RouteUtilities.smallestDifferenceDegrees(currentDegree, getDegreesToGo(route[0])) < LEVEL_ONE) {
			vibrator.vibrate(VIBRATION_ONE);
		} else if(RouteUtilities.smallestDifferenceDegrees(currentDegree, getDegreesToGo(route[0])) < LEVEL_TWO) {
			vibrator.vibrate(VIBRATION_TWO);
		}
	}
	
	public float getDegreesToGo(Point myLocation) {
		return 180f;
	}
		
}

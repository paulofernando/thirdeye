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
	
	/** Next point to conduct the user */
	private int nextPoint = 0;
	
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
		if(nextPoint < route.length) { //If the end of the route is not reached yet
			if(RouteUtilities.smallestDifferenceDegrees(currentDegree, getDegreesToGo(route[0])) < LEVEL_ONE) {
				vibrator.vibrate(VIBRATION_ONE);
			} else if(RouteUtilities.smallestDifferenceDegrees(currentDegree, getDegreesToGo(route[0])) < LEVEL_TWO) {
				vibrator.vibrate(VIBRATION_TWO);
			}
		}
	}
	
	public double getDegreesToGo(Point myLocation) {
		return convertLatAndLonToDegrees(myLocation.x, myLocation.y);
	}
	
	private double convertLatAndLonToDegrees(double latitude, double longitude) {
		double currentLat = -12.997900d;
		double currentLon = -38.512777d;
		
		double latToGo = -13.011249d;
		double lonToGo = -38.492693d;

		//convert to radians:
		double g2r = Math.PI/180;
		double lat1r = currentLat * g2r;
		double lat2r = latToGo * g2r;
		double lon1r = currentLon * g2r;
		double lon2r = lonToGo * g2r;
		double dlonr = lon2r - lon1r;
		
		double y = Math.sin(dlonr) * Math.cos(lat2r);
		double x = Math.cos(lat1r) * Math.sin(lat2r) - Math.sin(lat1r) * Math.cos(lat2r)* Math.cos(dlonr);

		//compute bearning and convert back to degrees:
		return Math.atan2(y, x) / g2r;
	}
		
}

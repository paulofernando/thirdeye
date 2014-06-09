package com.example.mythirdeye.location;

import com.example.mythirdeye.location.MyLocation.LocationResult;
import com.example.mythirdeye.location.listeners.PointListener;

import android.content.Context;
import android.location.Location;
import android.os.Vibrator;
import android.util.Log;


public class DirectionOrientation implements PointListener {

	/* Levels of vibration */
	private final int LEVEL_ONE = 10;
	private final int LEVEL_TWO = 60;
	
	/* Time of vibration */
	private final int VIBRATION_ONE = 500;
	private final int VIBRATION_TWO = 100;
	
	/** The route to direct the person */
	private Place[] route;
	
	private Vibrator vibrator;
	
	double currentLat, currentLon;
	
	/** Next point to conduct the user */
	private int nextPoint = 0;
	
	/** Protection for the application doesn't call the pointReached() many times */
	private boolean intoThePoint = false;
	
	LocationResult locationResult = new LocationResult(){
	    @Override
	    public void gotLocation(Location location){
	        Log.i("GPS", location.getLatitude() + " " + location.getLongitude());
	        currentLat = location.getLatitude();
	        currentLon = location.getLongitude();
	    }
	};
	

	public float getDistanceToTheNextPoint() {
		float[] result = new float[2];
		Location.distanceBetween(currentLat, currentLon, 
				route[nextPoint].getLatitude(), route[nextPoint].getLongitude(), result);
		
		return result[0];
	}
	
	/**
	 * Instatiates a new direction controller
	 * @param route The route to direct the person
	 */
	public DirectionOrientation(Place[] route, Vibrator vibrator, final Context context) {
		this.route = route;		
		this.vibrator = vibrator;
		
		final MyLocation myLocation = new MyLocation();
		myLocation.getLocation(context, locationResult);
		myLocation.addProximityAlert(route[0].getLatitude(), route[0].getLongitude(), this);

	}
	
	/**
	 * Conducts the person by the route
	 * @param currentDegree The degree where the smartphone is pointing 
	 */
	public void conduct(float currentDegree) {
		if(nextPoint < route.length) { //If the end of the route is not reached yet
			if(RouteUtilities.smallestDifferenceDegrees(currentDegree, getDegreesToGo(route[nextPoint])) < LEVEL_ONE) {
				vibrator.vibrate(VIBRATION_ONE);
			} else if(RouteUtilities.smallestDifferenceDegrees(currentDegree, getDegreesToGo(route[nextPoint])) < LEVEL_TWO) {
				vibrator.vibrate(VIBRATION_TWO);
			}
		}
	}
	
	public double getDegreesToGo(Place placeToGo) {
		return RouteUtilities.bearing(currentLat, currentLon, placeToGo.getLatitude(), placeToGo.getLongitude());
	}	
	
	@Override
	public void pointReached() {
		if(!intoThePoint) {
			intoThePoint = true;
			if(nextPoint < route.length) {
				nextPoint++;
			} else {
				vibrator.cancel();
			}
		}
	}

	@Override
	public void pointExited() {
		intoThePoint = false;
	}

	public int getNextPoint() {
		return nextPoint;
	}

	public Place[] getRoute() {
		return route;
	}
	
}

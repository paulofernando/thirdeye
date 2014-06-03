package com.example.mythirdeye;

import android.content.Context;
import android.location.Location;
import android.os.Vibrator;
import android.util.Log;

import com.example.mythirdeye.location.MyLocation;
import com.example.mythirdeye.location.MyLocation.LocationResult;

public class DirectionOrientation {

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
	
	LocationResult locationResult = new LocationResult(){
	    @Override
	    public void gotLocation(Location location){
	        Log.i("GPS", location.getLatitude() + " " + location.getLongitude());
	        currentLat = location.getLatitude();
	        currentLon = location.getLongitude();
	    }
	};
	
	/**
	 * Instatiates a new direction controller
	 * @param route The route to direct the person
	 */
	public DirectionOrientation(Place[] route, Vibrator vibrator, Context context) {
		this.route = route;		
		this.vibrator = vibrator;
		
		MyLocation myLocation = new MyLocation();
		myLocation.getLocation(context, locationResult);
		myLocation.addProximityAlert(route[0].getLatitude(), route[0].getLongitude());
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
		return convertLatAndLonToDegrees(placeToGo.getLatitude(), placeToGo.getLongitude());
	}
	
	private double convertLatAndLonToDegrees(double latitudeToGo, double longitudeToGo) {
		
		//convert to radians:
		double g2r = Math.PI/180;
		double lat1r = currentLat * g2r;
		double lat2r = latitudeToGo * g2r;
		double lon1r = currentLon * g2r;
		double lon2r = longitudeToGo * g2r;
		double dlonr = lon2r - lon1r;
		
		double y = Math.sin(dlonr) * Math.cos(lat2r);
		double x = Math.cos(lat1r) * Math.sin(lat2r) - Math.sin(lat1r) * Math.cos(lat2r)* Math.cos(dlonr);
				
		//compute bearning and convert back to degrees:
		return Math.abs(Math.atan2(y, x) / g2r);
	}
	
}

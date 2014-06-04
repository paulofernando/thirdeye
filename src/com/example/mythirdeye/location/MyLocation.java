package com.example.mythirdeye.location;

import java.util.Timer;
import java.util.TimerTask;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class MyLocation {
	
	private LocationManager locationManager;
	private LocationResult locationResult;
	private boolean gps_enabled = false;
	
	private static final long POINT_RADIUS = 10; // in Meters
	private static final long PROX_ALERT_EXPIRATION = -1;
	private static final String PROX_ALERT_INTENT = "com.javacodegeeks.android.lbs.ProximityAlert";
	
	private Context context;
	
	public boolean getLocation(Context context, LocationResult result) {
		
		this.context = context;
		
		locationResult = result;
		
		if (locationManager == null)
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);

		// exceptions will be thrown if provider is not permitted.
		try {
			gps_enabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
		}
		
		// don't start listeners if no provider is enabled
		if (!gps_enabled)
			return false;

		if (gps_enabled) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
		}

		return true;
	}

	public void addProximityAlert(double latitude, double longitude) {
		Intent intent = new Intent(PROX_ALERT_INTENT);
		PendingIntent proximityIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);

		locationManager.addProximityAlert(
				latitude, // the latitude of the central point of the alert region
				longitude, // the longitude of the central point of the alert region
				POINT_RADIUS, // the radius of the central point of the alert region, in meters
				PROX_ALERT_EXPIRATION, // time for this proximity alert, in
										// milliseconds, or -1 to indicate no
										// expiration
				proximityIntent // will be used to generate an Intent to fire
								// when entry to or exit from the alert region
								// is detected
				);

		IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);
		context.registerReceiver(new ProximityIntentReceiver(), filter);
	}

	LocationListener locationListenerGps = new LocationListener() {
		public void onLocationChanged(Location location) {
			locationResult.gotLocation(location);			
		}

		public void onProviderDisabled(String provider) {}
		public void onProviderEnabled(String provider) {}
		public void onStatusChanged(String provider, int status, Bundle extras) {}
	};
	
	class GetLastLocation extends TimerTask {
		@Override
		public void run() {			
			Location gps_loc = null;
			if (gps_enabled)
				gps_loc = locationManager
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			
			// if there are both values use the latest one
			if (gps_loc != null) {
				locationResult.gotLocation(gps_loc);
			}
		}
	}

	public static abstract class LocationResult {
		public abstract void gotLocation(Location location);
	}
	
}
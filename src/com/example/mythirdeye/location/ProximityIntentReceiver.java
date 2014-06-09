package com.example.mythirdeye.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.example.mythirdeye.location.listeners.PointListener;

public class ProximityIntentReceiver extends BroadcastReceiver {

	PointListener listener;
	
	public ProximityIntentReceiver(PointListener listener) {
		this.listener = listener;
	}
	
    @Override
    public void onReceive(Context context, Intent intent) {
        
        String key = LocationManager.KEY_PROXIMITY_ENTERING;

        Boolean entering = intent.getBooleanExtra(key, false);
        
        if (entering) {
        	listener.pointReached();
        } else {
        	listener.pointExited();
        }
    }        
}
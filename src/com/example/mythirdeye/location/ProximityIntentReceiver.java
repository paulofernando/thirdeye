package com.example.mythirdeye.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

public class ProximityIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        
        String key = LocationManager.KEY_PROXIMITY_ENTERING;

        Boolean entering = intent.getBooleanExtra(key, false);
        
        if (entering) {
            Toast.makeText(context, "entering", Toast.LENGTH_LONG).show();
        }
        else {
        	Toast.makeText(context, "exiting", Toast.LENGTH_LONG).show();
        }                
    }        
}
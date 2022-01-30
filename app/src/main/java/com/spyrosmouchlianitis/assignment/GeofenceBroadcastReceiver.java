package com.spyrosmouchlianitis.assignment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.sql.Timestamp;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.provider.Settings.System.getString;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    int geofenceTransition;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Intent recieved!", Toast.LENGTH_SHORT).show();
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceStatusCodes
                    .getStatusCodeString(geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        // Get the transition type.
        geofenceTransition = geofencingEvent.getGeofenceTransition();
        Toast.makeText(context, ""+geofenceTransition, Toast.LENGTH_SHORT).show();
        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            Toast.makeText(context, triggeringGeofences.toString(), Toast.LENGTH_LONG).show();

            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(
                    this,
                    geofenceTransition,
                    triggeringGeofences
            );

            // Send notification and log the transition details.
            //sendNotification(geofenceTransitionDetails);
            Log.i(TAG, geofenceTransitionDetails);
        } else {
            Toast.makeText(context, "NO CHANGE", Toast.LENGTH_SHORT).show();
            // Log the error.
            //Log.e(TAG, getString(R.string.geofence_transition_invalid_type,
             //       geofenceTransition));
            Log.e(TAG, String.valueOf(geofenceTransition));
        }

    }

    private String getGeofenceTransitionDetails(GeofenceBroadcastReceiver geofenceBroadcastReceiver, int geofenceTransition, List<Geofence> triggeringGeofences) {
        return geofenceBroadcastReceiver + " " + geofenceTransition + " " + triggeringGeofences;
    }
}

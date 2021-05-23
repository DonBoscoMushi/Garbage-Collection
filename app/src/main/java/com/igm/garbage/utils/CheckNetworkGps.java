package com.igm.garbage.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

import com.igm.garbage.R;

public class CheckNetworkGps {

    private static final String TAG = "CheckNetworkGps";

    public void checkNetworkGps(final Context context){

        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gsp_enabled = true;
        boolean network_enabled = false;

        try {

            gsp_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){
            Log.d(TAG, "checkNetworkGps: Gps kuna error");
        }

        try{
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){
            Log.d(TAG, "checkNetworkGps: Netwrok ina shda");
        }

        if(!gsp_enabled && !network_enabled){
            //notify user about errors

            new AlertDialog.Builder(context)
                    .setMessage("Location not enabled")
                    .setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton(R.string.Cancel, null)
                    .show();
        }
    }

}

package com.alicelab.uoauber;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity implements LocationListener {

    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button button = (Button)findViewById(R.id.register);

        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gpsFlg = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsFlg) {
            // GPSを設定するように促す
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
            Log.d("debug", "not gpsEnable, startActivity");
        } else {
            Log.d("debug", "gpsEnabled");
        }

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, RegisterActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}

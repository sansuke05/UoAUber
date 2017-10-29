package com.alicelab.uoauber;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements LocationListener {

    private LocationManager mLocationManager;
    private String name = "";
    private String latitude = "120.00";
    private String longitude = "35.00";
    private String carFlag = "0";

    private PostAPIConnection task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button button = (Button)findViewById(R.id.register);
        final EditText editText = (EditText)findViewById(R.id.editText);
        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        }
        else{
            locationStart();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedId = radioGroup.getCheckedRadioButtonId();
                if (-1 != checkedId){
                    if (((RadioButton)findViewById(checkedId)).getText().toString().equals("あり")) carFlag = "1";
                    else carFlag = "0";

                    name = editText.getText().toString();
                } else {
                    Toast.makeText(RegisterActivity.this, "車の有無を選択して下さい", Toast.LENGTH_LONG).show();
                    return;
                }

                JSONObject json = null;
                try {
                    //json作成
                    json = new JSONObject();
                    json.accumulate("name", name);
                    json.accumulate("home_latitude", latitude);
                    json.accumulate("home_longitude", longitude);
                    json.accumulate("have_car", carFlag);
                } catch (JSONException e){
                    e.printStackTrace();
                }

                Log.d("debug",json.toString());

                task = new PostAPIConnection(RegisterActivity.this, "REGISTER", json.toString());
                task.execute();
            }
        });
    }

    private void locationStart(){
        Log.d("debug","locationStart()");

        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsFlg = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsFlg) {
            // GPSを設定するように促す
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
            Log.d("debug", "not gpsEnable, startActivity");
        } else {
            Log.d("debug", "gpsEnabled");
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);

            Log.d("debug", "checkSelfPermission false");
            return;
        }

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    @Override
    public void onLocationChanged(Location location){
        latitude = Double.toString(location.getLatitude()); // 緯度
        longitude = Double.toString(location.getLongitude()); // 軽度

        mLocationManager.removeUpdates(this);
        Log.d("RegisterActivity", "緯度：" + latitude);
        Log.d("RegisterActivity", "経度：" + longitude);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("debug","checkSelfPermission true");

                locationStart();
                return;

            } else {
                // それでも拒否された時の対応
                Toast toast = Toast.makeText(this, "これ以上なにもできません", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.d("debug", "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}

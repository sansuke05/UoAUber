package com.alicelab.uoauber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by user on 2017/10/29.
 */

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button ride_button = (Button)findViewById(R.id.ride_button);
        Button register_button = (Button)findViewById(R.id.register_button);
        Button add_driver_button = (Button)findViewById(R.id.add_driver_button);

        ride_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity();
            }
        });

        add_driver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDriverRegistorationActivity();
            }
        });
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void startDriverRegistorationActivity(){
        Intent intent = new Intent(this, DriverRegistorationActivity.class);
        startActivity(intent);
    }
}

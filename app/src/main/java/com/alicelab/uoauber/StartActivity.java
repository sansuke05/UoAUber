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

        ride_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(this, MainActivity.class);
            }
        });
    }
}

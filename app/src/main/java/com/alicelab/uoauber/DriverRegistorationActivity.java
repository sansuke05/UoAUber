package com.alicelab.uoauber;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by user on 2017/10/29.
 */

public class DriverRegistorationActivity extends AppCompatActivity {

    private String latitude = "120.00";
    private String longitude = "35.00";
    private String date = "";
    private String time = "";

    private TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registoration);

        Button setTimeButton = (Button)findViewById(R.id.setTimeButton);
        Button submitButton = (Button)findViewById(R.id.submitButton);
        timeView = (TextView)findViewById(R.id.timeView);

        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });
    }

    private void setDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog dDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        //日付が選択されたときの処理
                        String strYear = Integer.toString(year);
                        String strMonth = Integer.toString(month+1);
                        String strDay = Integer.toString(day);
                        date = strYear + "/" + strMonth + "/" + strDay;
                        Log.d("debug",date);
                        timeView.setText(date + time);
                    }
                },
                year,
                month,
                dayOfMonth);
        dDialog.show();
        /*
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute){

                    }
                },
        );
        */
    }
}

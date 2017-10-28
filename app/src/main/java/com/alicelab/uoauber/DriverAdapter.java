package com.alicelab.uoauber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by user on 2017/10/28.
 */

public class DriverAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<DriverData> driverList;

    public DriverAdapter(Context context){
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDriverList(ArrayList<DriverData> driverList){
        this.driverList = driverList;
    }

    @Override
    public int getCount(){
        return driverList.size();
    }

    @Override
    public Object getItem(int position){
        return driverList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return driverList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.driverrow, parent, false);

        ((TextView)convertView.findViewById(R.id.driver_name)).setText(driverList.get(position).getName());
        ((TextView)convertView.findViewById(R.id.departure_time)).setText(driverList.get(position).getDepartureTime());
        ((TextView)convertView.findViewById(R.id.departure_place)).setText(driverList.get(position).getDeparturePlace());

        return convertView;
    }
}

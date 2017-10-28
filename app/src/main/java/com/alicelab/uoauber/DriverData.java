package com.alicelab.uoauber;

/**
 * Created by user on 2017/10/28.
 */

public class DriverData {
    long id;
    private String name;
    private String departure_time;
    private String departure_place;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDepartureTime(){
        return departure_time;
    }

    public void setDepartureTime(String departure_time){
        this.departure_time = departure_time;
    }

    public String getDeparturePlace(){
        return departure_place;
    }

    public void setDeparturePlace(String departure_place){
        this.departure_place = departure_place;
    }
}

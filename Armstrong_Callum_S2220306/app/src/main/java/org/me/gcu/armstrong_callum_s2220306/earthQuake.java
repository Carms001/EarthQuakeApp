package org.me.gcu.armstrong_callum_s2220306;


import java.io.Serializable;

//==================================================================================================
//Details : My Details
//
// Name                 Callum Armstrong
// Student ID           2220306
// Programme of Study   Computing
//
//==============================================================================================

public class earthQuake implements Serializable {

    //Varietals
    String dateTime;
    String location;
    String longitude;
    String latitude;
    String depth;
    String magnitude;

    //==============================================================================================

    //Getters

    public String getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getDepth() {
        return depth;
    }

    public String getMagnitude() {
        return magnitude;
    }

    //==============================================================================================

    //Setters


    public void setDateTime(String dateTime) {
        this.dateTime = dateTime.trim();
    }

    public void setLocation(String location) {
        this.location = location.trim();
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude.trim();
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude.trim();
    }

    public void setDepth(String depth) {
        this.depth = depth.trim();
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude.trim();
    }
}

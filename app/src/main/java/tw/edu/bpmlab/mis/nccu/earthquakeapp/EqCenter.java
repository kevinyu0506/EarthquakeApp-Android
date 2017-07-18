package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 2017/4/24.
 */

public class EqCenter {

    public Integer magnitude;
    public Double longitude;
    public Double latitude;
    public String time;
//    public String address;



    public EqCenter() {
    }

    public EqCenter(int magnitude, Double x, Double y, String time) {
        this.magnitude = magnitude;
        this.longitude = x;
        this.latitude = y;
        this.time = time;
//        this.address = address;
    }

    public void setMagnitude(Integer magnitude) {
        this.magnitude = magnitude;
    }

    public Integer getMagnitude() {
        return magnitude;
    }


    public void setLongitude(Double x) {
        this.longitude = x;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLatitude(Double y) {
        this.latitude = y;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

//    public String getAddress(){
//        return address;
//    }

//    public void setAddress(String address){
//        this.address = address;
//    }


//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("latitude", latitude);
//        result.put("longitude", longitude);
//        result.put("latitude", latitude);
//        result.put("time", time);
//
//        return result;
//    }


}
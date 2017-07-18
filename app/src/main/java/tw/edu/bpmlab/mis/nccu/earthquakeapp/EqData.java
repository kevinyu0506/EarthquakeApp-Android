package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import net.sourceforge.jtds.jdbc.DateTime;

/**
 * Created by Kevin on 2017/4/18.
 */

public class EqData {

    private Integer magnitude;
    private Double longitude;
    private Double latitude;
    private Double x;
    private Double y;
//    private Double accelerator;
    private String time;
//    private String eqDataID;


    public EqData() {
    }

    public EqData(int magnitude, Double longitude, Double latitude, Double x, Double y, String time) {
        this.magnitude = magnitude;
        this.longitude = longitude;
        this.latitude = latitude;
        this.x = x;
        this.y = y;
//        this.accelerator = accelerator;
        this.time = time;
//        this.eqDataID = eqDataID;
    }

//    public String getEqDataID() {
//        return eqDataID;
//    }

//    public void setEqDataID(String eqDataID) {
//        this.eqDataID = eqDataID;
//    }

    public void setMagnitude(Integer magnitude) {
        this.magnitude = magnitude;
    }

    public Integer getMagnitude() {
        return magnitude;
    }


    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getX() {
        return x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getY() {
        return y;
    }

//    public void setAccelerator(Double accelerator) {
//        this.accelerator = accelerator;
//    }

//    public Double getAccelerator() {
//        return accelerator;
//    }


    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

}



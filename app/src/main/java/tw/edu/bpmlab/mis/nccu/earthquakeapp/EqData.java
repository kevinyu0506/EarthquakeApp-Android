package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import net.sourceforge.jtds.jdbc.DateTime;

/**
 * Created by Kevin on 2017/4/18.
 */

public class EqData {

    private Integer magnitude;
    private Double longitude;
    private Double latitude;
    private Double accelerator;
    private String time;
    private String eqDataID;


    public EqData() {
    }

    public EqData(int magnitude, Double x, Double y, Double accelerator, String time, String eqDataID) {
        this.magnitude = magnitude;
        this.longitude = x;
        this.latitude = y;
        this.accelerator = accelerator;
        this.time = time;
        this.eqDataID = eqDataID;
    }

    public String getEqDataID() {
        return eqDataID;
    }

    public void setEqDataID(String eqDataID) {
        this.eqDataID = eqDataID;
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

    public void setAccelerator(Double accelerator) {
        this.accelerator = accelerator;
    }

    public Double getAccelerator() {
        return accelerator;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

}



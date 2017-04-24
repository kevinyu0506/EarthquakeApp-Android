package tw.edu.bpmlab.mis.nccu.earthquakeapp;

/**
 * Created by Kevin on 2017/4/24.
 */

public class EqCenter {

    private String id;
    private Integer magnitude;
    private Double longitude;
    private Double latitude;
    private String time;

    public EqCenter() {
    }

    public EqCenter(int magnitude, Double x, Double y, String time) {
        this.magnitude = magnitude;
        this.longitude = x;
        this.latitude = y;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
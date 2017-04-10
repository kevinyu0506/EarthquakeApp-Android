package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import android.view.View;
import android.widget.ImageButton;

//db
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Calendar;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

//countDown
import android.os.CountDownTimer;

//acceleration
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class alert extends AppCompatActivity implements SensorEventListener, LocationListener {

    protected TextView Date;
    protected int thisYear;
    protected int thisMonth;
    protected String thisMonthEng;
    protected int thisDate;
//    protected int thisHour;
//    protected int thisMin;
//    protected int thisSec;

    protected TextView countDown;
    protected ProgressBar countDownBar;
    protected int setCountDownTime;
    protected long timeUntilFinish;

    public SensorManager aSensorManager;
    public Sensor aSensor;
    public double gravity[] = new double[3];
//    private double eqGal;

    public TextView level;
    public TextView levelDescribe;

    public long latitude;
    public long longtitude;
    public TextView location;
    public String address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_alert);

//settingButton
        ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(alert.this, settings.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

//mapButton
        ImageButton mapButton = (ImageButton) findViewById(R.id.map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(alert.this, MapsActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


        getTime();
        countDown();
        setCountDownBar();
        sensor();
        getMagnitude();
        findLocation(23.222,121.3232);
        location.setText("123");


    }


    //time
    public void getTime() {
        Calendar dt = Calendar.getInstance();
        thisYear = dt.get(Calendar.YEAR);
        thisMonth = dt.get(Calendar.MONTH) + 1;
        thisDate = dt.get(Calendar.DAY_OF_MONTH);
//        thisHour = dt.get(Calendar.HOUR_OF_DAY);
//        thisMin = dt.get(Calendar.MINUTE);
//        thisSec = dt.get(Calendar.SECOND);

        Date = (TextView) findViewById(R.id.Date);
        if (thisMonth == 1) {
            thisMonthEng = "Jan";
        }
        if (thisMonth == 2) {
            thisMonthEng = "Feb";
        }
        if (thisMonth == 3) {
            thisMonthEng = "Mar";
        }
        if (thisMonth == 4) {
            thisMonthEng = "Apr";
        }
        if (thisMonth == 5) {
            thisMonthEng = "May";
        }
        if (thisMonth == 6) {
            thisMonthEng = "Jun";
        }
        if (thisMonth == 7) {
            thisMonthEng = "Jul";
        }
        if (thisMonth == 8) {
            thisMonthEng = "Aug";
        }
        if (thisMonth == 9) {
            thisMonthEng = "Sep";
        }
        if (thisMonth == 10) {
            thisMonthEng = "Oct";
        }
        if (thisMonth == 11) {
            thisMonthEng = "Nov";
        }
        if (thisMonth == 12) {
            thisMonthEng = "Dec";
        }
        Date.setText(thisMonthEng + ", " + Integer.toString(thisDate) + ", " + Integer.toString(thisYear));
//        Date.setText(Integer.toString(thisYear)+"/" + Integer.toString(thisMonth)+"/" + Integer.toString(thisDate)+" "
//                + Integer.toString(thisHour)+":" + Integer.toString(thisMin)+":" + Integer.toString(thisSec)
//        Date.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf"));
    }

    //countDown
    public void countDown() {

        countDown = (TextView) findViewById(R.id.countDown);

        setCountDownTime = (int) (Math.random() * 10 + 1) * 10000;
//        setCountDownTime = 9000;


        new CountDownTimer(setCountDownTime, 10) {

            @Override
            public void onFinish() {
//                countDown.setText("00:00");
            }

            @Override
            public void onTick(long millisUntilFinished) {

//                timeUntilFinish = millisUntilFinished;

                if (millisUntilFinished / 1000 % 60 >= 10) {
                    countDown.setText("0" + String.valueOf(millisUntilFinished / 60000) + ":" + String.valueOf(millisUntilFinished / 1000 % 60));
                } else {
                    countDown.setText("0" + String.valueOf(millisUntilFinished / 60000) + ":0" + String.valueOf(millisUntilFinished / 1000 % 60));
                }
                if(millisUntilFinished == 0){
                    countDown.setText("00:00");

                }

            }
        }.start();

    }

    //countDownBar
    public void setCountDownBar() {

        countDownBar = (ProgressBar) findViewById(R.id.countDownBar);
        countDownBar.setVisibility(View.VISIBLE);

        countDownBar.setProgress(100);

//        final int totalProgressTime = setCountDownTime;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int progress = 100;

                while (progress > 0) {
                    try {
                        countDownBar.setProgress(progress);
                        sleep((setCountDownTime / 100));
//                        progress = progress - 5;
//                        progress = progress - (100 / (setCountDownTime / 100));
                        progress = progress - 1;

//                        Log.d("1", Integer.toString(progress));
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                countDownBar.setProgress(0);

            }
        };
        t.start();


    }

    //acceleration
    private void sensor() {
        aSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aSensor = aSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        aSensorManager.registerListener(this, aSensor, aSensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        gravity[0] = event.values[0];
        gravity[1] = event.values[1];
        gravity[2] = event.values[2];

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    static connectDataBase connectDataBase = new connectDataBase();

    public void getMagnitude() {

        level = (TextView) findViewById(R.id.level);
        levelDescribe = (TextView) findViewById(R.id.levelDescribe);


        try {
            Connection conn = connectDataBase.CONN();
            String query = "select accelerationz from datatemp where productid = 5";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
//            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                level.setText(rs.getString("accelerationz"));
                System.out.print(rs.getFloat("accelerationz"));
                if (rs.getFloat("accelerationz") <= 2) {
                    levelDescribe.setText("LIGHT");
                    levelDescribe.setTextColor(Color.rgb(92, 201, 255));
                    level.setTextColor(Color.rgb(92, 201, 255));

                } else if (rs.getFloat("accelerationz") >= 3 && rs.getFloat("accelerationz") <= 4) {
                    levelDescribe.setText("MEDIUM");
                    levelDescribe.setTextColor(Color.rgb(255, 209, 5));
                    level.setTextColor(Color.rgb(255, 209, 5));

                } else if (rs.getFloat("accelerationz") >= 5) {
                    levelDescribe.setText("SEVERE");
                    levelDescribe.setTextColor(Color.rgb(235, 61, 125));
                    level.setTextColor(Color.rgb(235, 61, 125));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String[] findLocation(double lon, double lat) {

        location = (TextView)findViewById(R.id.location) ;

        String locationData[] = new String[3];
        try {
            String http = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lon + "&language=zh-TW&sensor=true";
            URL url = new URL(http);
            HttpURLConnection huc = (HttpURLConnection)url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream(),"UTF-8"));
            String str = "";
            StringBuffer sb = new StringBuffer();

            while (null != ((str=br.readLine()))){
                sb.append(str);
                if(str.contains("formatted_address")){
                    str = str.replace("formatted_address","");
                    str = str.replace(" ","");
                    str = str.replace(":","");
                    str = str.replace("Unnamed Road","");
                    str = str.replace(",","");
                    locationData[0] = str;
                }else if(str.contains("coordinates")){
                    str = str.replace("coordinates","");
                    str = str.replace("Point","");
                    str = str.replace(" ","");
                    str = str.replace(">","");
                    str = str.replace("<","");
                    str = str.replace("/","");
                    String tmp[] = str.split(",");
                    locationData[1] = tmp[0];
                    locationData[2] = tmp[1];
                }
            }
            br.close();
            String xmlResponse = sb.toString();
            huc.disconnect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        address = locationData[0];
        return locationData;

    }


    @Override
    public void onLocationChanged(Location loc) {
        if(loc != null){
            String add[] = findLocation(loc.getLongitude(), loc.getLatitude());
//            location.setText(add[0]);

        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

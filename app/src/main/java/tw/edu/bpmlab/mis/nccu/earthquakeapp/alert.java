package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
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
import java.lang.reflect.Array;
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

//gps
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

//address
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.widget.AdapterView.OnItemSelectedListener;


public class alert extends AppCompatActivity implements
        SensorEventListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

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

    protected static final String TAG = "MainActivity";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected double latitude;
    protected double longtitude;

    protected TextView location;


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
//        getMagnitude();
        buildGoogleApiClient();
        openDialog();
//        getAddress(23, 121);


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


        final SharedPreferences countdown = getSharedPreferences("countdown", 0);
        final SharedPreferences.Editor editor = countdown.edit();

        countDown = (TextView) findViewById(R.id.countDown);

        setCountDownTime = (int) (Math.random() * 10 + 1) * 10000;
        editor.putInt("countdown",setCountDownTime).commit();

        new CountDownTimer(setCountDownTime, 10) {

            @Override
            public void onFinish() {
                countDown.setText("00:00");
            }

            @Override
            public void onTick(long millisUntilFinished) {

//                timeUntilFinish = millisUntilFinished;

                if (millisUntilFinished / 1000 % 60 >= 10) {
                    countDown.setText("0" + String.valueOf(millisUntilFinished / 60000) + ":" + String.valueOf(millisUntilFinished / 1000 % 60));
                } else {
                    countDown.setText("0" + String.valueOf(millisUntilFinished / 60000) + ":0" + String.valueOf(millisUntilFinished / 1000 % 60));
                }
//                if(millisUntilFinished == 0){
//                    countDown.setText("00:00");
//
//                }

            }
        }.start();

    }


    public void openDialog() {


        final SharedPreferences countdownTime= getSharedPreferences("countdown", 0);
        int countdown = countdownTime.getInt("countdown",0);

        new AlertDialog.Builder (alert.this)
                .setTitle ("地震警報")
                .setMessage (countdown/1000 +"秒")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    public void onClick(
                            DialogInterface dialogInterface, int i){}
                })
                .show();
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


//    static connectDataBase connectDataBase = new connectDataBase();
//
//    public void getMagnitude() {
//
//        level = (TextView) findViewById(R.id.level);
//        levelDescribe = (TextView) findViewById(R.id.levelDescribe);
//
//
//        try {
//            Connection conn = connectDataBase.CONN();
//            String query = "select accelerationz from datatemp where productid = 5";
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
////            ResultSetMetaData rsmd = rs.getMetaData();
//
//            while (rs.next()) {
//                level.setText(rs.getString("accelerationz"));
//                System.out.print(rs.getFloat("accelerationz"));
//                if (rs.getFloat("accelerationz") <= 2) {
//                    levelDescribe.setText("LIGHT");
//                    levelDescribe.setTextColor(Color.rgb(92, 201, 255));
//                    level.setTextColor(Color.rgb(92, 201, 255));
//
//                } else if (rs.getFloat("accelerationz") >= 3 && rs.getFloat("accelerationz") <= 4) {
//                    levelDescribe.setText("MEDIUM");
//                    levelDescribe.setTextColor(Color.rgb(255, 209, 5));
//                    level.setTextColor(Color.rgb(255, 209, 5));
//
//                } else if (rs.getFloat("accelerationz") >= 5) {
//                    levelDescribe.setText("SEVERE");
//                    levelDescribe.setTextColor(Color.rgb(235, 61, 125));
//                    level.setTextColor(Color.rgb(235, 61, 125));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //gps
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
// TODO Auto-generated method stub
/* 取消註冊SensorEventListener */
        aSensorManager.unregisterListener(this);
//        Toast.makeText(this, "Unregister accelerometerListener", Toast.LENGTH_LONG).show();
        super.onPause();
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longtitude = mLastLocation.getLatitude();

        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());

    }


    //getAddress
//    public void getAddress(double lat, double lon) {
//        location = (TextView) findViewById(R.id.location);
//        String addressData[] = new String[3];
//        try {
//            String htp = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lon + "&language=zh-TW&sensor=true";
//            URL url = new URL(htp);
//            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
//            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream(), "UTF-8"));
//            String str = "";
//            StringBuffer sb = new StringBuffer();
//
//            while (null != ((str = br.readLine()))) {
//                sb.append(str);
//                if (str.contains("formatted_address")) {
//                    str = str.replace("formatted_address", "");
//                    str = str.replace("Unnamed Road", "");
//                    str = str.replace(" ", "");
//                    str = str.replace(":", "");
//                    str = str.replace(",", "");
//                    str = str.replace("\"", "");
//
//                    addressData[0] = str;
//                    if(addressData[0] != null){
//                        location.setText(addressData[0]);
//                    }
//                    break;
//                }
//
//
//
//            }
//            br.close();
//            String xmlResponse = sb.toString();
//            huc.disconnect();
//            System.out.print(xmlResponse);
////            location.setText(addressData[0]);
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public void onLocationChanged(Location loc) {

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

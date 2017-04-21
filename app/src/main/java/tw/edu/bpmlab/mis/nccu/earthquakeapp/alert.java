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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//address
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.widget.AdapterView.OnItemSelectedListener;

import net.sourceforge.jtds.jdbc.DateTime;


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
    public ArrayList<Double> eqGalData = new ArrayList<Double>();
    public ArrayList<Double> eqGalDataChn = new ArrayList<Double>();

    private double eqGal;
    private Integer magnitude;

    public TextView level;
    public TextView levelDescribe;

    protected static final String TAG = "MainActivity";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected double latitude;
    protected double longitude;
    protected String time;

    protected TextView location;
    private  FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;


    protected DatabaseReference mDataBase;


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

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child("eqData");


        Button uploadButton = (Button) findViewById(R.id.upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EqData eqData = new EqData(magnitude, longitude, latitude, eqGal, time);
                mFirebaseDatabaseReference.push().setValue(eqData);
            }
        });

        location = (TextView) findViewById(R.id.location);




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
        editor.putInt("countdown", setCountDownTime).commit();

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


        final SharedPreferences countdownTime = getSharedPreferences("countdown", 0);
        int countdown = countdownTime.getInt("countdown", 0);

        new AlertDialog.Builder(alert.this)
                .setTitle("地震警報")
                .setMessage(countdown / 1000 + "秒")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(
                            DialogInterface dialogInterface, int i) {
                    }
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

        int i;
        int k;

        gravity[0] = event.values[0];
        gravity[1] = event.values[1];
        gravity[2] = event.values[2];


        eqGal = Math.abs((Math.sqrt(Math.pow(gravity[0], 2) + Math.pow(gravity[1], 2) + Math.pow(gravity[2], 2)) - 9.81) * 100);


        i = eqGalData.size();
        k = eqGalDataChn.size();

        while (i<=2){
            eqGalData.add(eqGal);

            if (i==2){
                eqGalDataChn.add((eqGalData.get(1)/eqGalData.get(0)));
                eqGalData.remove(0);
            }
            if (k==2){
                eqGalDataChn.remove(0);
            }
            break;
        }





//        for (int j = 0; j < eqGalDataChn.size(); j++) {
//
//            location.setText("Index: " + j + " Item: " + eqGalDataChn.get(j));
//
//        }




        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        time = dateFormat.format(date);



        if (eqGal < 0.8) {
//            eqData.setMagnitude(0);
            magnitude = 0;
        }
        if (eqGal >= 0.8 && eqGal < 2.5) {
//            eqData.setMagnitude(1);
            magnitude = 1;
        }
        if (eqGal >= 2.5 && eqGal < 8) {
//            eqData.setMagnitude(2);
            magnitude = 2;
        }
        if (eqGal >= 8 && eqGal < 25) {
//            eqData.setMagnitude(3);
            magnitude = 3;
        }
        if (eqGal >= 25 && eqGal < 80) {
//            eqData.setMagnitude(4);
            magnitude = 4;
        }
        if (eqGal >= 80 && eqGal < 250) {
//            eqData.setMagnitude(5);
            magnitude = 5;
        }
        if (eqGal >= 250 && eqGal < 400) {
//            eqData.setMagnitude(6);
            magnitude = 6;
        }
        if (eqGal >= 400) {
//            eqData.setMagnitude(7);
            magnitude = 7;
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



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
//            eqData.setLatitude(mLastLocation.getLatitude());
//            eqData.setLongitude(mLastLocation.getLongitude());
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();


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


    public void upLoadEqData(){

//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
//        time = dateFormat.format(date);

        EqData eqData = new EqData(magnitude, longitude, latitude, eqGal, time);
        mFirebaseDatabaseReference.push().setValue(eqData);




    }

}

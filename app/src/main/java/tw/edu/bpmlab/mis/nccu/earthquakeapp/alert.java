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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    public String Time;

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

    public TextView localLevel;
    public TextView epicCenterLevel;
    public TextView localLocation;
    public TextView epicCenterLocation;
    public TextView levelDescribe;
    public TextView accelerator;


    protected static final String TAG = "MainActivity";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected double localLatitude;
    protected double localLongitude;
    protected double x;
    protected double y;
    protected int centerMagnitude;
    protected double centerLongitude;
    protected double centerX;
    protected double centerLatitude;
    protected double centerY;
    protected String centerTime;
    protected String centerAddress;
    protected String time;
    protected String topDate;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEqDataReference;
    private DatabaseReference mEqCenterReference;
    private ValueEventListener mEqCenterListener;

    private boolean initialDataLoaded;
    private long lastUpdate = 0;

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
        setCountDownBar();
        sensor();
        buildGoogleApiClient();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEqDataReference = mFirebaseDatabase.getReference().child("eqData");
        mEqCenterReference = mFirebaseDatabase.getReference().child("eqCenter");

        final String address = "";

        Button uploadButton = (Button) findViewById(R.id.upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EqCenter eqCenter = new EqCenter(magnitude, localLongitude, localLatitude, time, address);
                mEqCenterReference.setValue(eqCenter);
            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEqDataReference.removeValue();
            }
        });

        localLocation = (TextView) findViewById(R.id.localLocation);
        epicCenterLevel = (TextView)findViewById(R.id.epicCenterLevel);
        epicCenterLocation = (TextView)findViewById(R.id.epicCenterLocation);
        accelerator = (TextView) findViewById(R.id.accelerator);
        countDown = (TextView) findViewById(R.id.countDown);




    }


    //time
    public void getTime() {
        Calendar dt = Calendar.getInstance();
        thisYear = dt.get(Calendar.YEAR);
        thisMonth = dt.get(Calendar.MONTH) + 1;
        thisDate = dt.get(Calendar.DAY_OF_MONTH);


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
    }

    //countDown
    public void countDown() {

        setCountDownTime = (int) (Math.random() * 10 + 1) * 10000;

        new CountDownTimer(setCountDownTime, 10) {

            @Override
            public void onFinish() {
                countDown.setText("00:00");
            }

            @Override
            public void onTick(long millisUntilFinished) {

                if (millisUntilFinished / 1000 % 60 >= 10) {
                    countDown.setText("0" + String.valueOf(millisUntilFinished / 60000) + ":" + String.valueOf(millisUntilFinished / 1000 % 60));
                } else {
                    countDown.setText("0" + String.valueOf(millisUntilFinished / 60000) + ":0" + String.valueOf(millisUntilFinished / 1000 % 60));
                }
            }
        }.start();

    }


//    public void openDialog() {
//
//
//        final SharedPreferences countdownTime = getSharedPreferences("countdown", 0);
//        int countdown = countdownTime.getInt("countdown", 0);
//
//        new AlertDialog.Builder(alert.this)
//                .setTitle("地震警報")
//                .setMessage("震央經度 = " + centerLongitude + " , 震央緯度 = " + centerLatitude + " , 發生時間 =" + centerTime)
//                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    public void onClick(
//                            DialogInterface dialogInterface, int i) {
//                    }
//                })
//                .show();
//    }


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


        long curTime = System.currentTimeMillis();

        if ((curTime - lastUpdate) > 1000) {
            lastUpdate = curTime;

            eqGal = Math.abs((Math.sqrt(Math.pow(gravity[0], 2) + Math.pow(gravity[1], 2) + Math.pow(gravity[2], 2)) - 9.81) * 100);


            i = eqGalData.size();
            k = eqGalDataChn.size();

            while (i <= 2) {
                eqGalData.add(eqGal);

                if (i == 2) {
                    eqGalDataChn.add((eqGalData.get(1) / eqGalData.get(0)));
                    if (eqGalDataChn.get(0) > Math.pow(Math.sqrt(10), 2)) {
                        EqData eqData = new EqData(magnitude, x, y, eqGal, time);
                        mEqDataReference.push().setValue(eqData);


                    }
                    eqGalData.remove(0);
                }
                if (k == 2) {
                    eqGalDataChn.remove(0);
                }
                break;

            }


            for (int j = 0; j < eqGalDataChn.size(); j++) {
                DecimalFormat df = new DecimalFormat("##.00");
                accelerator.setText("加速度: " + Double.parseDouble(df.format(eqGalDataChn.get(j))));

            }

        }


        Locale locale = new Locale("en", "US");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:ms");
        DateFormat topicDate = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        Date date = new Date();
        time = dateFormat.format(date);
        topDate = topicDate.format(date);
        Date.setText("" + topDate);


        if (eqGal < 0.8) {
            magnitude = 0;
        }
        if (eqGal >= 0.8 && eqGal < 2.5) {
            magnitude = 1;
        }
        if (eqGal >= 2.5 && eqGal < 8) {
            magnitude = 2;
        }
        if (eqGal >= 8 && eqGal < 25) {
            magnitude = 3;
        }
        if (eqGal >= 25 && eqGal < 80) {
            magnitude = 4;
        }
        if (eqGal >= 80 && eqGal < 250) {
            magnitude = 5;
        }
        if (eqGal >= 250 && eqGal < 400) {
            magnitude = 6;
        }
        if (eqGal >= 400) {
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

        initialDataLoaded = false;
        final ArrayList<EqCenter> eqCenters = new ArrayList<EqCenter>();
        final SharedPreferences magnitude = getSharedPreferences("magnitude", 0);
        final int magnitudevalue = magnitude.getInt("btnChecked",0);


        ValueEventListener eqCenterListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (initialDataLoaded) {

                    EqCenter eqCenter = dataSnapshot.getValue(EqCenter.class);
                    eqCenters.add(eqCenter);

                    Integer centerMagnitude = eqCenter.getMagnitude();
                    Double centerX = eqCenter.getLongitude();
                    Double centerY = eqCenter.getLatitude();
                    centerLongitude = (centerX * 0.02) + 120;
                    centerLatitude = (centerY * 0.04) + 21.5;
                    String centerTime = eqCenter.getTime();
                    String centerAddress = eqCenter.getAddress();

                    epicCenterLevel.setText("" + centerMagnitude);
                    epicCenterLocation.setText("" + centerAddress);



                    //比較用戶設定開啟通知的級數
                    if(magnitudevalue <= centerMagnitude) {

                        eqCountDown(centerLongitude , centerLatitude);

                        new AlertDialog.Builder(alert.this)
                                .setTitle(centerMagnitude + "級地震警報")
                                .setMessage("震央位置 = " + centerAddress + " , 發生時間 = " + centerTime)
//                                .setMessage("localX = " + x + " , localY = " + y + " , centerX = " + centerX + " , centerY = " + centerY)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialogInterface, int i) {
                                    }
                                })
                                .show();
                    }else{

                    }


                }else {

                }

                initialDataLoaded = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };

        mEqCenterReference.addValueEventListener(eqCenterListener);

        mEqCenterListener = eqCenterListener;


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

        if (mEqCenterListener != null) {
            mEqCenterReference.removeEventListener(mEqCenterListener);
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

            localLatitude = mLastLocation.getLatitude();
            localLongitude = mLastLocation.getLongitude();

            x = Math.floor((localLongitude - 120) / 0.02);
            y = Math.floor((localLatitude - 21.5) / 0.04);


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
//        String addressData[] = new String[3];
//        try {
//            String htp = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lon + "&language=zh-TW&sensor=true";
//            URL url = new URL(htp);
//            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
//            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream(), "UTF-8"));
////            String str[] = new String[100];
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
//                    if (addressData[0] != null) {
//                        localLocation.setText(addressData[0]);
//                    }
//                    break;
//                }
//
//
//            }
//            br.close();
//            String xmlResponse = sb.toString();
//            huc.disconnect();
//            System.out.print(xmlResponse);
////            localLocation.setText(addressData[0]);git
////            localLocation.setText(addressData[0]);
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


    public void eqCountDown(double centerLongitude, double centerLatitude){

        double eqSpeed = 5;
        double d  = getDistance(centerLatitude,centerLongitude,localLatitude,localLongitude);
        double eqCountDownTime = d/eqSpeed;


        setCountDownTime = (int)eqCountDownTime * 1000;


        new CountDownTimer(setCountDownTime, 10) {

            @Override
            public void onFinish() {
                countDown.setText("00:00");
            }

            @Override
            public void onTick(long millisUntilFinished) {


                if (millisUntilFinished / 1000 % 60 >= 10) {
                    countDown.setText("0" + String.valueOf(millisUntilFinished / 60000) + ":" + String.valueOf(millisUntilFinished / 1000 % 60));
                } else {
                    countDown.setText("0" + String.valueOf(millisUntilFinished / 60000) + ":0" + String.valueOf(millisUntilFinished / 1000 % 60));
                }


            }
        }.start();

    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    private double getDistance(double centerLatitude, double centerLongitude, double localLatitude, double localLongitude) {
        double theta = centerLongitude - localLongitude;
        double dist = Math.sin(deg2rad(centerLatitude)) * Math.sin(deg2rad(localLatitude)) + Math.cos(deg2rad(centerLatitude)) * Math.cos(deg2rad(localLatitude)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;

        return (dist);
    }




}

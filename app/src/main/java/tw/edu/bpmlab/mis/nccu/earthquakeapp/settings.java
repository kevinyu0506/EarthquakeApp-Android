package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import static android.media.AudioManager.RINGER_MODE_NORMAL;
import static android.media.AudioManager.RINGER_MODE_SILENT;

public class settings extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings);



        //eqInfoButton連接到settings_eqinfo頁面

        Button eqInfoButton = (Button)findViewById(R.id.eqInfoButton);
        eqInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings.this, settings_eqinfo.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_leftin, R.anim.slide_leftout);
            }
        });



        //eqAboutButton連結到settings_about頁面

        Button eqAboutButton = (Button)findViewById(R.id.eqAboutButton);
        eqAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings.this, settings_about.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_leftin, R.anim.slide_leftout);
            }
        });



        //alarmButton連結到alert頁面

        ImageButton alarmButton = (ImageButton)findViewById(R.id.alarmButton);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings.this, alert.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });



        //mapButton連結到map頁面

        ImageButton mapButton = (ImageButton)findViewById(R.id.mapButton);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings.this, MapsActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });



        //紀錄viberation開關設定

//        final AudioManager myAudioManager;
//        myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//
//        final SharedPreferences vibrationIsSet = getSharedPreferences("vibration",0);
//        boolean vibrate = vibrationIsSet.getBoolean("vibration",false);
//        final Switch viberationSwitch = (Switch)findViewById(R.id.vibrationSwitch);
//        vibrationIsSet.edit().clear().commit();
//        viberationSwitch.setChecked(vibrate);
//
//
//        viberationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//
//                NotificationManager notificationManager =
//                        (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
//                        && !notificationManager.isNotificationPolicyAccessGranted()) {
//
//                    Intent intent = new Intent(
//                            android.provider.Settings
//                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//
//                    startActivity(intent);
//                }
//
//
//                if (isChecked){
//                    vibrationIsSet.edit().clear();
//                    viberationSwitch.setChecked(true);
//                    vibrationIsSet.edit().putBoolean("vibration",true).commit();
//                    myAudioManager.setRingerMode(RINGER_MODE_NORMAL);
//
//                }
//                else {
//                    vibrationIsSet.edit().clear();
//                    viberationSwitch.setChecked(false);
//                    vibrationIsSet.edit().putBoolean("vibration",false).commit();
//                    myAudioManager.setRingerMode(RINGER_MODE_SILENT);
//
//                }
//            }
//        });



        //alarm開關設定

        final AudioManager myAudioManager;
        myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final SharedPreferences alarmIsSet = getSharedPreferences("alarm",0);
        boolean alarm = alarmIsSet.getBoolean("alarm",false);
        final Switch alarmSwitch = (Switch)findViewById(R.id.alarmSwitch);
        alarmIsSet.edit().clear().commit();
        alarmSwitch.setChecked(alarm);

        switch (myAudioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:
                alarmSwitch.setChecked(true);
                break;
            case AudioManager.RINGER_MODE_SILENT:
                alarmSwitch.setChecked(false);
                break;
        }

        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                NotificationManager notificationManager =
                        (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                        && !notificationManager.isNotificationPolicyAccessGranted()) {

                    Intent intent = new Intent(
                            android.provider.Settings
                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

                    startActivity(intent);
                }


                if (isChecked){
                    alarmIsSet.edit().clear();
                    alarmSwitch.setChecked(true);
                    alarmIsSet.edit().putBoolean("alarm",true).commit();
                    myAudioManager.setRingerMode(RINGER_MODE_NORMAL);

                }
                else {
                    alarmIsSet.edit().clear();
                    alarmSwitch.setChecked(false);
                    alarmIsSet.edit().putBoolean("alarm",false).commit();
                    myAudioManager.setRingerMode(RINGER_MODE_SILENT);

                }
            }
        });




        //ringtoneButton打開設定提示聲視窗

        Button ringtoneButton = (Button)findViewById(R.id.ringtoneButton);
        ringtoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                Uri currenturi = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 1l);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select ringtone for notifications:");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_NOTIFICATION);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currenturi);
                startActivityForResult( intent, 999);

            }

        });



        final Button mag3 = (Button) findViewById(R.id.mag3);
        final Button mag4 = (Button) findViewById(R.id.mag4);
        final Button mag5 = (Button) findViewById(R.id.mag5);
        final Button mag6 = (Button) findViewById(R.id.mag6);

        final ImageView magnitude3 = (ImageView) findViewById(R.id.magnitude3);
        final ImageView magnitude4 = (ImageView) findViewById(R.id.magnitude4);
        final ImageView magnitude5 = (ImageView) findViewById(R.id.magnitude5);
        final ImageView magnitude6 = (ImageView) findViewById(R.id.magnitude6);

        final SharedPreferences magnitude= getSharedPreferences("magnitude", 0);
        int magnitudevalue = magnitude.getInt("btnChecked",0);

        switch(magnitudevalue){
            case 0:
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                break;
            case 3:
                magnitude3.setBackgroundResource(R.drawable.magnitude_3_picked);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                break;
            case 4:
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4_picked);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                break;
            case 5:
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5_picked);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                break;
            case 6:
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6_picked);
                break;

    }
        mag3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    magnitude.edit().clear();
                    magnitude3.setBackgroundResource(R.drawable.magnitude_3_picked);
                    magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                    magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                    magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                    magnitude.edit().putInt("btnChecked", 3).commit();
                }
            });

        mag4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magnitude.edit().clear();
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4_picked);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                magnitude.edit().putInt("btnChecked", 4).commit();
            }
        });

        mag5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magnitude.edit().clear();
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5_picked);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                magnitude.edit().putInt("btnChecked", 5).commit();
            }
        });

        mag6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magnitude.edit().clear();
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6_picked);
                magnitude.edit().putInt("btnChecked", 6).commit();
            }
        });



        // 讀取前面charge設定到settings頁面，以及可以在settings頁面複寫掉設定

        final SharedPreferences chargeIsSet= getSharedPreferences("charge", 0);
        boolean charge = chargeIsSet.getBoolean("charge",false);
        final Switch chargeModeSwitch = (Switch) findViewById(R.id.chargeModeSwitch);
        chargeModeSwitch.setChecked(charge);

        chargeModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    chargeIsSet.edit().clear();
                    chargeModeSwitch.setChecked(true);
                    chargeIsSet.edit().putBoolean("charge",true).commit();
                }
                else {
                    chargeIsSet.edit().clear();
                    chargeModeSwitch.setChecked(false);
                    chargeIsSet.edit().putBoolean("charge",false).commit();
                }
            }}
        );



        // 讀取前面wifi設定到settings頁面，以及可以在settings頁面複寫掉設定

        final SharedPreferences wifiIsSet= getSharedPreferences("wifi", 0);
        boolean wifi = wifiIsSet.getBoolean("wifi",false);
        final Switch wifiSwitch = (Switch) findViewById(R.id.wifiSwitch);
        wifiSwitch.setChecked(wifi);


        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    wifiIsSet.edit().clear();
                    wifiSwitch.setChecked(true);
                    wifiIsSet.edit().putBoolean("wifi",true).commit();
                                                            }
                else {
                    wifiIsSet.edit().clear();
                    wifiSwitch.setChecked(false);
                    wifiIsSet.edit().putBoolean("wifi",false).commit();
                }
            }}
        );



        //GPS開關，可以導向手機設定GPS頁面

        final boolean gpsCheck = isOpenGps();
        final Switch gpsSwitch = (Switch) findViewById(R.id.gpsSwitch);
        gpsSwitch.setChecked(gpsCheck);

        gpsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Intent i = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }
                else {
                    gpsSwitch.setChecked(false);
                }
            }}
        );


//        if (ContextCompat.checkSelfPermission(settings.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(settings.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(settings.this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                new AlertDialog.Builder(settings.this)
//                        .setMessage("我真的沒有要做壞事, 給我權限吧?")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ActivityCompat.requestPermissions(settings.this,
//                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                            }
//                        })
//                        .show();
//            } else {
//
//                ActivityCompat.requestPermissions(settings.this,
//                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//            }
//        }
    }


//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
//
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//                } else {
//                    finish();
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }





    private boolean isOpenGps() {

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // 通過GPS衛星定位，定位級別可以精確到街（通過24顆衛星定位，在室外和空曠的地方定位準確、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通過WLAN或移動網路(3G/2G)確定的位置（也稱作AGPS，輔助GPS定位。主要用於在室內或遮蓋物（建築群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }


}
















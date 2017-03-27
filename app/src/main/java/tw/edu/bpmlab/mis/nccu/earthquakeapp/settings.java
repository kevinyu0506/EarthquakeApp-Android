package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import java.sql.Array;

public class settings extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings);

        Button eqInfoButton = (Button)findViewById(R.id.eqInfoButton);
        eqInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings.this, settings_eqinfo.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_leftin, R.anim.slide_leftout);
                //overridePendingTransition(0, 0);
            }
        });

        ImageButton alarmButton = (ImageButton)findViewById(R.id.alarmButton);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings.this, alert.class);
                startActivity(intent);
                //overridePendingTransition(R.anim.slide_rightin, R.anim.slide_rightout);
                overridePendingTransition(0, 0);
            }
        });

        ImageButton mapButton = (ImageButton)findViewById(R.id.mapButton);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings.this, MapsActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_leftin, R.anim.slide_leftout);
                overridePendingTransition(0, 0);
            }
        });

        Button eqAboutButton = (Button)findViewById(R.id.eqAboutButton);
        eqAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings.this, settings_about.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_leftin, R.anim.slide_leftout);
                //overridePendingTransition(0, 0);
            }
        });

        Button ringtoneButton = (Button)findViewById(R.id.ringtoneButton);
        ringtoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings.this, settings_ringtone.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_leftin, R.anim.slide_leftout);
                //overridePendingTransition(0, 0);
            }
        });


        final ImageButton magnitude3 = (ImageButton) findViewById(R.id.magnitude3);
        final ImageButton magnitude4 = (ImageButton) findViewById(R.id.magnitude4);
        final ImageButton magnitude5 = (ImageButton) findViewById(R.id.magnitude5);
        final ImageButton magnitude6 = (ImageButton) findViewById(R.id.magnitude6);

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



        magnitude3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    magnitude.edit().clear();
                    magnitude3.setBackgroundResource(R.drawable.magnitude_3_picked);
                    magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                    magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                    magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                }
            });

        magnitude4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magnitude.edit().clear();
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4_picked);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
            }
        });

        magnitude5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magnitude.edit().clear();
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5_picked);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
            }
        });

        magnitude6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magnitude.edit().clear();
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6_picked);
            }
        });



    }



//    private boolean isOpenGps() {
//
//        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        // 通過GPS衛星定位，定位級別可以精確到街（通過24顆衛星定位，在室外和空曠的地方定位準確、速度快）
//        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        // 通過WLAN或移動網路(3G/2G)確定的位置（也稱作AGPS，輔助GPS定位。主要用於在室內或遮蓋物（建築群或茂密的深林等）密集的地方定位）
//        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        if (gps || network) {
//            return true;
//        }
//        return false;
//    }



}
















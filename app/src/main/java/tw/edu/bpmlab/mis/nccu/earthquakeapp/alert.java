package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import java.util.Calendar;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import android.os.CountDownTimer;


import android.view.View;
import android.widget.ImageButton;



public class alert extends AppCompatActivity {

    protected TextView Date;
    protected int thisYear;
    protected int thisMonth;
    protected String thisMonthEng;
    protected int thisDate;
    protected int thisHour;
    protected int thisMin;
    protected int thisSec;
    protected TextView countDown;
    protected ProgressBar countDownBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_alert);



        ImageButton settingsButton = (ImageButton)findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(alert.this, settings.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_leftin, R.anim.slide_leftout);
                overridePendingTransition(0, 0);
            }
        });

        ImageButton mapButton = (ImageButton)findViewById(R.id.map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(alert.this, MapsActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_leftin, R.anim.slide_leftout);
                overridePendingTransition(0, 0);
            }
        });


        getTime();
        countDown();
        setCountDownBar();


    }




    public void getTime(){
        Calendar dt = Calendar.getInstance();
        thisYear = dt.get(Calendar.YEAR);
        thisMonth = dt.get(Calendar.MONTH)+1;
        thisDate = dt.get(Calendar.DAY_OF_MONTH);
//        thisHour = dt.get(Calendar.HOUR_OF_DAY);
//        thisMin = dt.get(Calendar.MINUTE);
//        thisSec = dt.get(Calendar.SECOND);


        Date = (TextView) findViewById(R.id.Date);
        if (thisMonth==1){
            thisMonthEng = "Jan";
        }if (thisMonth==2){
            thisMonthEng = "Feb";
        }if (thisMonth==3){
            thisMonthEng = "Mar";
        }if (thisMonth==4){
            thisMonthEng = "Apr";
        }if (thisMonth==5){
            thisMonthEng = "May";
        }if (thisMonth==6){
            thisMonthEng = "Jun";
        }if (thisMonth==7){
            thisMonthEng = "Jul";
        }if (thisMonth==8){
            thisMonthEng = "Aug";
        }if (thisMonth==9){
            thisMonthEng = "Sep";
        }if (thisMonth==10){
            thisMonthEng = "Oct";
        }if (thisMonth==11){
            thisMonthEng = "Nov";
        }if (thisMonth==12){
            thisMonthEng = "Dec";
        }
        Date.setText(thisMonthEng+", "+Integer.toString(thisDate)+", "+Integer.toString(thisYear));
//        Date.setText(Integer.toString(thisYear)+"/" + Integer.toString(thisMonth)+"/" + Integer.toString(thisDate)+" "
//                + Integer.toString(thisHour)+":" + Integer.toString(thisMin)+":" + Integer.toString(thisSec)
//        Date.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf"));




    }

    public void countDown() {

        countDown = (TextView) findViewById(R.id.countDown);


        new CountDownTimer(70000, 1000) {

            @Override
            public void onFinish() {
                countDown.setText("00:00");

            }

            @Override
            public void onTick(long millisUntilFinished) {

                if(millisUntilFinished / 1000 % 60>10) {
                    countDown.setText("0"+String.valueOf(millisUntilFinished / 60000)+":"+String.valueOf(millisUntilFinished / 1000 % 60));
                }else{
                    countDown.setText("0"+String.valueOf(millisUntilFinished / 60000)+":0"+String.valueOf(millisUntilFinished / 1000 % 60));
                }

            }
        }.start();

    }

    public void setCountDownBar(){
        countDownBar = (ProgressBar) findViewById(R.id.countDownBar);
        countDownBar.setVisibility(View.VISIBLE);


    }



}

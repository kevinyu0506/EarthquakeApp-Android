package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

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



    }




}

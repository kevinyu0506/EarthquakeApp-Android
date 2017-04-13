package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class Startup_2 extends Activity {

    private GestureDetector gesture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_startup_2);

        final SharedPreferences charge = getSharedPreferences("charge", 0);

        final Button yes = (Button) findViewById(R.id.yesbutton2);
        final Button no = (Button) findViewById(R.id.noButton2);
//        wifi.edit().clear().commit();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes.setBackgroundResource(R.drawable.success_true);
                no.setBackgroundResource(R.drawable.error);
                charge.edit().clear();
                charge.edit().putBoolean("charge",true).commit();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes.setBackgroundResource(R.drawable.success);
                no.setBackgroundResource(R.drawable.error_true);
                charge.edit().clear();
                charge.edit().putBoolean("charge",false).commit();
            }
        });

        Button nextPage = (Button) findViewById(R.id.next);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startup_2.this, Startup_3.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


    }
}

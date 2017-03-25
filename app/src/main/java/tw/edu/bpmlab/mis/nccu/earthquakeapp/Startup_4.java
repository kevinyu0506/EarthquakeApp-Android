package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class Startup_4 extends Activity implements View.OnTouchListener {
    private static int TIME_OUT = 2500;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent2 = new Intent(Startup_4.this, alert.class);
            startActivity(intent2);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_4);
        RelativeLayout layout= (RelativeLayout)findViewById(R.id.relativeLayout1);
        layout.setOnTouchListener(this);
        this.handler.removeCallbacks(runnable);
        this.handler.postDelayed(runnable, TIME_OUT);
    }

    @Override

    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.relativeLayout1:
                this.handler.removeCallbacks(runnable);
                Intent intent2 = new Intent(Startup_4.this, alert.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
        return true;
    }
}

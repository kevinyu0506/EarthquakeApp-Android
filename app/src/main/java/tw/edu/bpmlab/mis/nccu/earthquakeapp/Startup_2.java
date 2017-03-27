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

        final SharedPreferences wifi = getSharedPreferences("wifi", 0);
        final SharedPreferences.Editor editor = wifi.edit();

//        gesture = new GestureDetector(new SwipeGestureDetector());

        final Button yes = (Button) findViewById(R.id.yesbutton2);
        final Button no = (Button) findViewById(R.id.noButton2);
        editor.clear().commit();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes.setBackgroundResource(R.drawable.success_true);
                no.setBackgroundResource(R.drawable.error);
                editor.clear();
                editor.putBoolean("wifi",true).commit();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes.setBackgroundResource(R.drawable.success);
                no.setBackgroundResource(R.drawable.error_true);
                editor.clear();
                editor.putBoolean("wifi",false).commit();
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
//    public boolean onTouchEvent(MotionEvent event) {
//        if (gesture.onTouchEvent(event)) {
//            return true;
//        }
//        return super.onTouchEvent(event);
//    }
//
//    private void onLeft() {
//        Intent intent = new Intent(Startup_2.this, Startup_4.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//    }
//
//    //private void onRight() {
//    //    Intent myIntent = new Intent(Startup_3.this, Startup_4.class);
//    //    startActivity(myIntent);
//    //    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//    //}
//
//    private class SwipeGestureDetector  extends GestureDetector.SimpleOnGestureListener
//    {
//        private static final int SWIPE_MIN_DISTANCE = 120;
//        private static final int SWIPE_MAX_OFF_PATH = 200;
//        private static final int SWIPE_THRESHOLD_VELOCITY = 200;
//
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            try {
//                float diffAbs = Math.abs(e1.getY() - e2.getY());
//                float diff = e1.getX() - e2.getX();
//
//                if (diffAbs > SWIPE_MAX_OFF_PATH)
//                    return false;
//
//                //Left swipe
//                if (diff > SWIPE_MIN_DISTANCE&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    Startup_2.this.onLeft();
//                }
//                // Right swipe
//
//                //else if (-diff > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                //    Startup_3.this.onRight();
//                //}
//            }
//            catch (Exception e) {
//                Log.e("Startup_3", "Error on gestures");
//            }
//            return false;
//        }
//    }
//
//}

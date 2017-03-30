package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.id.list;

public class settings_ringtone extends AppCompatActivity {


//    final TextView ringtoneList = (TextView) findViewById(R.id.ringtoneList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings_ringtone);

        Button settingButton = (Button)findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings_ringtone.this, settings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_rightin, R.anim.slide_rightout);
            }
        });

//        ArrayList showRingtone = getNotificationSounds();

//        ringtoneList.setText(showRingtone);
    }

//    public ArrayList<String> getNotificationSounds() {
//        RingtoneManager manager = new RingtoneManager(this);
//        manager.setType(RingtoneManager.TYPE_NOTIFICATION);
//        Cursor cursor = manager.getCursor();
//
//        ArrayList<String> list = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            String id = cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
//            String uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
//
//            list.add(uri + "/" + id);
//        }
//
//        return list;
//
//
//    }

}

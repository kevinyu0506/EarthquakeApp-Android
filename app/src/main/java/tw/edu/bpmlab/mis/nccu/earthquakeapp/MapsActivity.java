package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        final ImageButton alert = (ImageButton) findViewById(R.id.alarmButton);
        final ImageButton settings = (ImageButton) findViewById(R.id.settingButton);

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.alarmButton:
                        Intent intent = new Intent(MapsActivity.this, alert.class);
                        startActivity(intent);
////                        overridePendingTransition(R.anim.slide_rightin, R.anim.slide_leftout);
//                        overridePendingTransition(0, 0);
                        break;
                }
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.settingButton:
                        Intent intent = new Intent(MapsActivity.this, settings.class);
                        startActivity(intent);
////                        overridePendingTransition(R.anim.slide_rightin, R.anim.slide_leftout);
//                        overridePendingTransition(0, 0);
                        break;
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                this, R.raw.style_json));

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
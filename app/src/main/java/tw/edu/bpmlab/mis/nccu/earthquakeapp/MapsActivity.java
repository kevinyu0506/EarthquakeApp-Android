package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String typeLocation;
    private HandlerThread earthquakeInfoHandlerThread = new HandlerThread(MapsActivity.class.getSimpleName());
    private Handler earthquakeInfoHandler;
    private EarthquakeInfo earthquakeInfo;
    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        earthquakeInfoHandlerThread.start();
        earthquakeInfoHandler = new Handler(earthquakeInfoHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                try {
                    super.handleMessage(msg);
                    Document document = Jsoup.connect("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_month.geojson").ignoreContentType(true).ignoreHttpErrors(true).get();
                    earthquakeInfo = new Gson().fromJson(document.body().text(), EarthquakeInfo.class);
                } catch (Exception e) {
                    Log.e("!!!!", "!!!!!!!!!", e);
                }
            }
        };
        earthquakeInfoHandler.sendMessage(new Message());

        final ImageButton alert = (ImageButton) findViewById(R.id.alarmButton);
        final ImageButton settings = (ImageButton) findViewById(R.id.settingButton);
        final ImageButton currentLocation = (ImageButton) findViewById(R.id.currentLocation);
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.alarmButton:
                        Intent intent = new Intent(MapsActivity.this, alert.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        break;
                }
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.settingButton:
                        Intent intent = new Intent(MapsActivity.this, settings.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        break;
                }
            }
        });
        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location loc = getLastBestLocation();
                if (loc != null){
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(), loc.getLongitude())));
                }
            }
        });

        final EditText typeLocationField = (EditText) findViewById(R.id.typelocation);
        final Button searchLocation = (Button) findViewById(R.id.search_button);
        searchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeLocation = typeLocationField.getText().toString();
                addMarkerOnMap(typeLocation);
            }
        });
    }

    public void addMarkerOnMap(String string) {
        mMap.clear();
        if (earthquakeInfo != null) {
            for (Feature feature : earthquakeInfo.getFeatures()) {
                if (feature.getProperties().getPlace().toLowerCase().contains(string.toLowerCase())) {
                    double lat = feature.getGeometry().getCoordinates().get(1);
                    double lng = feature.getGeometry().getCoordinates().get(0);
                    Log.e("!!!", feature.getProperties().getPlace());
                    Log.i("Lat", "" + lat);
                    Log.i("Lng", "" + lng);
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.spot);
                    LatLng latLng = new LatLng(lat, lng);
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                            .title(feature.getProperties().getPlace())
                            .snippet(new StringBuilder("Mag:").append(feature.getProperties().getMag().toString()).append("  ").append(new SimpleDateFormat("yyyy/mm/dd").format(new Date(feature.getProperties().getTime()))).toString())
                            .icon(icon);
                    mMap.addMarker(markerOptions);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
                }
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
        Location loc = getLastBestLocation();
        if (loc != null){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 12));
            Log.e("!!",""+loc.toString());
        }

        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    }

    private Location getLastBestLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.e("!","no loc "  + ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION));
            return null;
        }
        Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime ) {
            return locationGPS;
        }
        else {
            return locationNet;
        }
    }
}
package kr.ac.jbnu.se.danim;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.HashMap;

import kr.ac.jbnu.se.danim.model.GlobalStorage;
import kr.ac.jbnu.se.danim.model.MapDirectionData;
import kr.ac.jbnu.se.danim.model.UserData;

public class MapDirectionSettingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;

    private FusedLocationProviderClient fusedLocationClient;

    private LatLng currentPosition;

    private FloatingActionButton floatingMyLocation;
    private TextView textViewEndAddress;
    private TextView btnStart;

    private String endAddress;
    private Double endLat;
    private Double endLng;
    private Double startLat;
    private Double startLng;

    private GlobalStorage globalStorage;
    MapDirectionData mapDirectionData;
    private Object HashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapdirectionsetting);

        floatingMyLocation = findViewById(R.id.floatingMyLocation);
        textViewEndAddress = findViewById(R.id.textViewEndAddress);
        btnStart = findViewById(R.id.btnStart);

        endAddress = getIntent().getStringExtra("endAddress");
        endLat = getIntent().getDoubleExtra("endLat", 0);
        endLng = getIntent().getDoubleExtra("endLng", 0);
        startLat = getIntent().getDoubleExtra("startLat", 0);
        startLng = getIntent().getDoubleExtra("startLng", 0);

        try {
            globalStorage.setDirectionDataHashMap(new HashMap<>());
            mapDirectionData.setStartLat(startLat);
            mapDirectionData.setStartLng(startLng);
            mapDirectionData.setEndLat(endLat);
            mapDirectionData.setEndLng(endLng);
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "null-direction", Toast.LENGTH_SHORT).show();
        }

        textViewEndAddress.setText(endAddress);

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        floatingMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(currentPosition).animate(CameraAnimation.Easing);;
                naverMap.moveCamera(cameraUpdate);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                currentPosition = new LatLng(location.getLatitude(),location.getLongitude());

                if (location != null) {
                    // Logic to handle location object
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);

        Marker marker = new Marker();
        marker.setPosition(new LatLng(endLat, endLng));
        marker.setMap(naverMap);

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(endLat, endLng)).animate(CameraAnimation.Easing);;
        naverMap.moveCamera(cameraUpdate);
    }
}
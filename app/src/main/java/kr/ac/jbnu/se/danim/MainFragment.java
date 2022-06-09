package kr.ac.jbnu.se.danim;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import kr.ac.jbnu.se.danim.model.MapDirectionData;
import kr.ac.jbnu.se.danim.model.GlobalStorage;
import kr.ac.jbnu.se.danim.model.UserData;

import android.widget.Chronometer;
import android.os.SystemClock;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainFragment extends Fragment
        implements SensorEventListener, View.OnClickListener, OnMapReadyCallback {
    long stopTime = 0;

    private CountDownTimer countDownTimer;
    private CircleProgressBar circleProgressBar;
    private GlobalStorage globalStorage;
    private UserData userData;
    private MapDirectionData mapDirectionData;
    private float calorie;
    private TextView tv_todayWalk_value;
    private TextView main_heartRate_value;
    private SensorManager sm;
    private Sensor sensor_step_detector;
    private TextView countDownDistanceView;
    private TextView countDownTimeView;

    private boolean timerRunning;

    int walk = 0;
    int weight2 = 50;

    /********************* << naver map >> *********************/

    private MapView mapView = null;
    private NaverMap naverMap;
    private FusedLocationSource locationSource;     //위치 반환하는 구현체
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
//    private static final String[] PERMISSIONS = {
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACTIVITY_RECOGNITION
//    };

    private FusedLocationProviderClient fusedLocationClient;

    private LatLng currentPosition;

    private TextView textViewEndAddress;
    private LinearLayout layoutMobilities;

    private Double endLat;
    private Double endLng;
    private Double startLat;
    private Double startLng;

    private String userName;
    private int userAge;
    private int userHeight;
    private int userWeight;

    public Double totalDistance;
    public int totalTime;

    String uu;

    InfoWindow infoWindow = new InfoWindow();

    private ArrayList<Integer> turnTypeList = new ArrayList<>();
    private ArrayList<ArrayList<Double>> directionList = new ArrayList<ArrayList<Double>>();

    private LatLng firstLatLng = new LatLng(0, 0);
    private LatLng secondLatLng = new LatLng(0, 0);
    public Double sumOfDistance;

    private boolean isLocation = false;
    float i = 0;

    /********************* << naver map >> *********************/


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        //걸음 수 측정
        tv_todayWalk_value = (TextView) view.findViewById(R.id.main_todayWalk_value);

        main_heartRate_value = (TextView) view.findViewById(R.id.main_heartRate_value);

        sm = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);  //센서 메니저 생성
        sensor_step_detector = sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);      //스텝 감지 센서 등록
        if (sensor_step_detector == null) {
            Toast.makeText(getContext(), "No Step Detect Sensor", Toast.LENGTH_SHORT).show();
        }

        globalStorage = GlobalStorage.getInstance();

        calorie = 0;

        circleProgressBar = view.findViewById(R.id.main_circlebar);

        countDownTimeView = (TextView) view.findViewById(R.id.countdown_Time_view);
        countDownDistanceView = (TextView) view.findViewById(R.id.countdown_distance_view);

        //버튼
        Button Start_btn = (Button) view.findViewById(R.id.start_btn);
        Start_btn.setOnClickListener(this);
        Button Stop_btn = (Button) view.findViewById(R.id.stop_btn);
        Stop_btn.setOnClickListener(this);
        Button Reset_btn = (Button) view.findViewById(R.id.search_btn);
        Reset_btn.setOnClickListener(this);
        FloatingActionButton floatingMyLocation = view.findViewById(R.id.floatingMyLocation);
        floatingMyLocation.setOnClickListener(this);


        /********************* << naver map >> *********************/

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::onMapReady);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        if (globalStorage.getDirectionDataHashMap() != null && !globalStorage.getDirectionDataHashMap().isEmpty()) {
            mapDirectionData = globalStorage.getDirectionDataHashMap().get("info");

            if (mapDirectionData == null) {
                Log.d("MainFragment", "Data is Null1");
                mapDirectionData = new MapDirectionData();
            }

            startLat = mapDirectionData.getStartLat();
            startLng = mapDirectionData.getStartLng();
            endLat = mapDirectionData.getEndLat();
            endLng = mapDirectionData.getEndLng();

        } else {
            Log.d("MainFragment", "Data is Null1");
        }

        if(globalStorage.getUserDataHashMap() != null && !globalStorage.getUserDataHashMap().isEmpty()) {
            userData = globalStorage.getUserDataHashMap().get("userInfo");

            if(userData == null) {
                userData = new UserData();
            }

            userName = userData.getUserName();
            userAge = userData.getUserAge();
            userHeight = userData.getUserHeight();
            userWeight = userData.getUserWeight();
        } else {
            Log.d("MainFragment", "Data is Null1");
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener((Activity) getContext(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
                //Toast.makeText(getContext(), "현재 : " + currentPosition, Toast.LENGTH_LONG).show();

                if (location != null) {
                    // Logic to handle location object
                }
            }
        });


        /********************* << naver map >> *********************/
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_btn:
                startTimer();
                isLocation = true;
                break;
            case R.id.stop_btn:
                isLocation = false;
                stopTimer();
                break;
            case R.id.search_btn:
                Intent intent1 = new Intent(getContext(), MapSearchActivity.class);
                startActivityForResult(intent1, MapSearchActivity.MAPSEARCH_ACTIVITY_START);
                break;
            case R.id.floatingMyLocation:
                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(currentPosition).animate(CameraAnimation.Easing);
                naverMap.moveCamera(cameraUpdate);
                break;

        }
    }


    //이 메서드가 없으면 지도가 보이지 않음
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        sm.registerListener((SensorEventListener) this, sensor_step_detector, SensorManager.SENSOR_DELAY_NORMAL);
        mapView.onResume();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState (@Nullable Bundle outState){
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop () {
        super.onStop();
        mapView.onStop();
        //mChrono.stop(); 이거 빼면 백그라운드에서 돌아감

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    // 센서값이 변할때
    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            if(event.values[0]==1.0f){
                // 센서 이벤트가 발생할때 마다 걸음수 증가
                walk++;
                tv_todayWalk_value.setText(String.valueOf(walk));
                calorie = (float)(userWeight*(0.9)*0.0004*walk);
                main_heartRate_value.setText(String.valueOf((int)calorie));

                //실시간 이동거리
                sumOfDistance = walk*0.6; // m
                circleProgressBar.setProgress((int) Math.round(sumOfDistance));
                countDownDistanceView.setText(Math.round((totalDistance-sumOfDistance)/1000*100)/100.0+" km");
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;

            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(s);
                JSONObject response = (JSONObject) obj;
                JSONArray features = (JSONArray) response.get("features");

                JSONObject feature = null;
                for (int i = 0; i < features.size(); i++) {
                    feature = (JSONObject) features.get(i);
                    JSONObject geometry = (JSONObject) feature.get("geometry");
                    JSONObject properties = (JSONObject) feature.get("properties");

                    String feature_type = (String) feature.get("type");
                    String geometry_type = (String) geometry.get("type");

                    JSONArray coordinates = (JSONArray) geometry.get("coordinates");

                    if (feature_type.equals("Feature")) {
                        if (geometry_type.equals("Point")) {

                            String description = (String) properties.get("description");
                            int turnType = ((Long) properties.get("turnType")).intValue();
                            if (turnType == 211 || turnType == 212
                                    || turnType == 213 || turnType == 214 || turnType == 215 || turnType == 216 || turnType == 217 || turnType == 218) {
                                turnTypeList.add(turnType);
                                Marker marker = new Marker();
                                marker.setPosition(new LatLng((Double) coordinates.get(1), (Double) coordinates.get(0)));
                                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_bodo));
                                marker.setMap(naverMap);

                                marker.setTag(description);
                                marker.setOnClickListener(overlay -> {
                                    infoWindow.open(marker);
                                    return true;
                                });

                                Overlay.OnClickListener listener = overlay -> {
                                    Marker overlayMarker = (Marker) overlay;

                                    if (overlayMarker.getInfoWindow() == null) {
                                        infoWindow.open(overlayMarker);
                                    } else {
                                        infoWindow.close();
                                    }

                                    return true;
                                };

                                marker.setOnClickListener(listener);

                                ArrayList<Double> data = new ArrayList<>();
                                data.add((Double) coordinates.get(1));
                                data.add((Double) coordinates.get(0));
                                directionList.add(data);
                            }


                        } else if (geometry_type.equals("LineString")) {
                            PathOverlay path = new PathOverlay();
                            List<LatLng> ArrayPositionList = new ArrayList<>();
                            for (int k = 0; k < coordinates.size(); k++) {
                                JSONArray coordinate = (JSONArray) coordinates.get(k);
                                ArrayPositionList.add(new LatLng((Double) coordinate.get(1), (Double) coordinate.get(0)));

                                if (k == 0 || k == coordinate.size()) {
                                    ArrayList<Double> data = new ArrayList<>();
                                    data.add((Double) coordinate.get(1));
                                    data.add((Double) coordinate.get(0));
                                    directionList.add(data);
                                }

                            }

//                            Toast.makeText(getContext(), "draw " , Toast.LENGTH_SHORT).show();
                            path.setCoords(ArrayPositionList);
                            path.setColor(Color.rgb(107, 102, 255));
                            path.setMap(naverMap);

                        }
                    }
                }
                for (int i = 0; i < features.size(); i++) {
                    feature = (JSONObject) features.get(i);
                    JSONObject j = (JSONObject) feature.get("properties");
                    totalDistance = Double.valueOf(j.get("totalDistance").toString()); //m
                    totalTime = Integer.valueOf(j.get("totalTime").toString()) / 60;
                    circleProgressBar.setMax((int) Math.round(totalDistance));
                }

            } catch(Exception e) {
                e.printStackTrace();
            }

        }


    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        //ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE); // 권한확인
        naverMap.setMapType(NaverMap.MapType.Terrain);
        naverMap.setLocationSource(locationSource); // 현재위치
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        CameraUpdate cameraUpdate = CameraUpdate.zoomTo(17);
        naverMap.moveCamera(cameraUpdate);

        naverMap.setOnMapClickListener((coord, point) -> {
            infoWindow.close();
        });

        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(getActivity().getApplicationContext()) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                // 정보 창이 열린 마커의 tag를 텍스트로 노출하도록 반환
                return (CharSequence)infoWindow.getMarker().getTag();
            }
        });

        try{
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                String appKey = "l7xx7d5159a19a344859952fdcb6e11f2296";
                String sLat = String.valueOf(startLat);
                String sLng = String.valueOf(startLat);
                String eLat = String.valueOf(endLat);
                String eLng = String.valueOf(endLng);
                String reqCoordType = "WGS84GEO";
                String rescoordType = "EPSG3857";
                String startName = URLEncoder.encode("출발지", "UTF-8");
                String endName = URLEncoder.encode("도착지", "UTF-8");

                uu = "https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&format=json&callback=result&appKey="
                        + appKey + "&startX=" + startLng + "&startY=" + startLat + "&endX=" + endLng + "&endY=" + endLat
                        + "&startName=" + startName + "&endName=" + endName + "&searchOption=" + 30;
                url = new URL(uu);

            } catch (UnsupportedEncodingException | MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "utf-8");
                urlConnection.setRequestProperty("Content-Type", "application/x-form-urlencoded");

                NetworkTask networkTask = new NetworkTask(uu, null);
                networkTask.execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e){
            Toast.makeText(getContext(), "null" , Toast.LENGTH_SHORT).show();
        }
    }
//
//    public void onRequestPermissionsResult(int requestCode,
//                                          String permissions[], int[] grantResults) {
//        if(locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
//            if(!locationSource.isActivated()){
//                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
//                return;
//            } else {
//                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
    private long tempTime = 0;
    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTime*60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tempTime = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        timerRunning = true;
    }
    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }
    private void updateTimer() {
        int hour = (int) tempTime / 3600000;
        int minutes = (int) tempTime % 3600000 / 60000;

        String timeLeftText = "";

        //분이 10보다 작으면 0이 붙는다.
        if(hour < 10){timeLeftText += "0";}
        timeLeftText = hour + ":";

        //분이 10보다 작으면 0이 붙는다.
        if(minutes < 10){timeLeftText += "0";}
        timeLeftText += minutes;

        countDownTimeView.setText(timeLeftText);

    }
}
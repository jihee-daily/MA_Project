package kr.ac.jbnu.se.danim;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.ac.jbnu.se.danim.model.GlobalStorage;

public class RewardsFragment extends Fragment implements SensorEventListener {

    private GlobalStorage globalStorage;

    //    public RewardsFragment(String name) {
//        globalStorage = GlobalStorage.getInstance();
//
//        globalStorage.getUserDataHashMap().get(name);
//    }

        TextView altitude;
        SensorManager sm;
        Sensor sensor_step_detector;


        public RewardsFragment(String name) {
            globalStorage = GlobalStorage.getInstance();
            globalStorage.getUserDataHashMap().get(name);
        }

        public RewardsFragment() {

        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_rewards, container, false);

            altitude = (TextView) view.findViewById(R.id.altitude);
            sm = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
            sensor_step_detector = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);
            sm.registerListener(this, sensor_step_detector, SensorManager.SENSOR_DELAY_NORMAL);

            ImageButton btn_runchallenge = (ImageButton) view.findViewById(R.id.run_challengeBtn);
            btn_runchallenge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.run_challengeBtn:
                            // 데이터를 다이얼로그로 보내는 코드. 데이터 전달보냄
                            // Bundle args = new Bundle();
                            // args.putString("key", "value");

                            RundialogFragment dialog = new RundialogFragment();
                            dialog.show(getActivity().getSupportFragmentManager(), "tag");
                    }
                }
            });
            ImageButton btn_hikechallenge = (ImageButton) view.findViewById(R.id.hike_challengeBtn);
            btn_hikechallenge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.hike_challengeBtn:
                            // 데이터를 다이얼로그로 보내는 코드. 데이터 전달보냄
                            // Bundle args = new Bundle();
                            // args.putString("key", "value");

                            HikedialogFragment dialog = new HikedialogFragment();
                            dialog.show(getActivity().getSupportFragmentManager(), "tag");
                    }
                }
            });

            return view;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {

                long timestamp = sensorEvent.timestamp;
                float presure = sensorEvent.values[0];
                presure = (float) (Math.round(presure * 100) / 100.0);
                float height = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, presure);
                height = (float) (Math.round(height * 100) / 100.0);

                if(height>=1000){

                }
                altitude.setText(String.valueOf(height));
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

        public void onResume() {
            super.onResume();
            sm.registerListener((SensorEventListener) this, sensor_step_detector, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        public void onPause() {
            super.onPause();
            sm.unregisterListener((SensorEventListener) this);
        }
    }

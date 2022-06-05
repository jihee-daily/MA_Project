package kr.ac.jbnu.se.danim;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;

import kr.ac.jbnu.se.danim.model.MapDirectionData;

public class MainActivity extends AppCompatActivity {
//    private ActivityMainBinding binding;
//
//    private GlobalStorage globalStorage;
    Fragment fragment_main, fragment_rewards;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACTIVITY_RECOGNITION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // globalStorage = GlobalStorage.getInstance();

        //tab 설정
        fragment_main = new MainFragment();
        fragment_rewards = new RewardsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.tabs_frame, fragment_main).commit();
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){ selected = fragment_main; }
                else if(position == 1) { selected = fragment_rewards; }
                getSupportFragmentManager().beginTransaction().replace(R.id.tabs_frame, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ImageButton profile = (ImageButton) findViewById(R.id.profileBtn);
        profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivityForResult(intent1, ProfileActivity.PROFILE_ACTIVITY_START);
            }
        });

        ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE); // 권한확인


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
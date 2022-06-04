package kr.ac.jbnu.se.danim;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import kr.ac.jbnu.se.danim.model.GlobalStorage;
import kr.ac.jbnu.se.danim.ui.main.SectionsPagerAdapter;
import kr.ac.jbnu.se.danim.databinding.ActivityMainBinding;

import com.dinuscxj.progressbar.CircleProgressBar;

public class MainActivity extends AppCompatActivity {
//    private ActivityMainBinding binding;
//
//    private GlobalStorage globalStorage;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACTIVITY_RECOGNITION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // globalStorage = GlobalStorage.getInstance();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

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
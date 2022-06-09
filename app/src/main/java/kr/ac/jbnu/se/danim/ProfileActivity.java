package kr.ac.jbnu.se.danim;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    public static final int PROFILE_ACTIVITY_START = 10;
    public static final int PROFILE_ACTIVITY_SUCCESS = 11;
    public static final int PROFILE_ACTIVITY_FAIL = 12;
    EditText tv_name;
    EditText tv_age;
    EditText tv_height;
    EditText tv_weight;
    TextView tv_all;
    ImageView profileimage;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileimage = findViewById(R.id.profile_image);

        Button btn_edit = (Button)findViewById(R.id.editBtn);
        Button btn_look = (Button)findViewById(R.id.lookBtn);
        // Button btn_selectImg = (Button)findViewById(R.id.selectImgBtn);
        tv_name = (EditText) findViewById(R.id.profile_name);
        tv_age = (EditText) findViewById(R.id.profile_age);
        tv_height = (EditText) findViewById(R.id.profile_height);
        tv_weight = (EditText) findViewById(R.id.profile_weight);
        tv_all = (TextView)findViewById(R.id.show);



        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHelper.insert(tv_name.getText().toString(),
                        Integer.parseInt(tv_age.getText().toString()),
                        Integer.parseInt(tv_height.getText().toString()),
                        Integer.parseInt(tv_weight.getText().toString()));
                Toast.makeText(ProfileActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });
        btn_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.lookBtn:
                        tv_all.setText(dbHelper.getResult());
                        Toast.makeText(ProfileActivity.this, "최근 정보 조회", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
        dbHelper = new DBHelper(ProfileActivity.this,1);

        if (globalStorage.getDirectionDataHashMap() != null) {
            if (userData == null) {
                userData = new userData();
            }
            userData.setUserName(tv_name);
            userData.setUserAge(tv_age);
            userData.setUserHeight(tv_height);
            userData.setUserWeight(tv_weight);
            globalStorage.getUserDataHashMap().put("userInfo",userData);
        }
    }
}
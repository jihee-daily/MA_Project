package kr.ac.jbnu.se.danim;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kr.ac.jbnu.se.danim.databinding.ActivityMainBinding;
import kr.ac.jbnu.se.danim.model.GlobalStorage;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    SQLiteDatabase sqlDB;

    public static final int PROFILE_ACTIVITY_START = 10;
    public static final int PROFILE_ACTIVITY_SUCCESS = 11;
    public static final int PROFILE_ACTIVITY_FAIL = 12;
    TextView profile_name, profile_age, profile_height, profile_weight;

//    private ActivityMainBinding binding;
//    private GlobalStorage globalStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button btn_edit = (Button) findViewById(R.id.editBtn);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), UserdataActivity.class);
                startActivityForResult(intent2, UserdataActivity.USERDATA_ACTIVITY_START);
            }
        });

    }


    }


















/*
        globalStorage = GlobalStorage.getInstance();
        name = findViewById(R.id.profile_name);
        age = findViewById(R.id.profile_age);
        height = findViewById(R.id.profile_height);
        weight = findViewById(R.id.profile_weight);
        //Intent
        Button btn_edit = (Button)findViewById(R.id.editBtn);
        btn_edit.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v){
            // if edittext value not equal "" -> else 항목 넣어라 좀..
            if (!globalStorage.getUserDataHashMap().containsKey("name")) {
                globalStorage.getUserDataHashMap().put("name", new UserData(name.getText().toString(),
                                                                            Integer.parseInt(age.getText().toString()),
                                                                            Integer.parseInt(height.getText().toString()),
                                                                            Integer.parseInt(weight.getText().toString())));

            } else {
                Toast.makeText(getApplicationContext(), "이미 사용중인 이름입니다.", Toast.LENGTH_SHORT).show();
            }
        }
        });


    }

 */
package kr.ac.jbnu.se.danim;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

import kr.ac.jbnu.se.danim.databinding.ActivityMainBinding;
import kr.ac.jbnu.se.danim.model.GlobalStorage;

public class UserdataActivity extends AppCompatActivity {


    //TextView profile_name, profile_age, profile_height, profile_weight;
    public static final int USERDATA_ACTIVITY_START = 10;

    myDBHelper myHelper;
    EditText username, userage, userheight, userweight;
    TextView edtNameResult, edtAgeResult;
    Button btnInit, bt_confirm, btnSelect;
    SQLiteDatabase sqlDB;

    ProfileActivity pa = new ProfileActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdata);

        username = (EditText) findViewById(R.id.username);
        userage = (EditText) findViewById(R.id.userage);
        userheight = (EditText) findViewById(R.id.userheight);
        userweight = (EditText) findViewById(R.id.userweight);
        edtNameResult = (TextView) findViewById(R.id.detNameResult);
        edtAgeResult = (TextView) findViewById(R.id.edtAgeResult);

         btnInit = (Button) findViewById(R.id.btnInit);
         bt_confirm = (Button) findViewById(R.id.bt_confirm);
         btnSelect = (Button) findViewById(R.id.btnSelect);

        myHelper = new myDBHelper(this);
        btnInit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB, 1, 2); // 인수는 아무거나 입력하면 됨.
                sqlDB.close();
            }
        });
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO groupTBL VALUES ( '"
                        + username.getText().toString() + "' , "
                        + userage.getText().toString() + ","
                        + userheight.getText().toString() + ","
                        + userweight.getText().toString()+");");
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "입력됨",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null);

                String strNames = "이름" + "\r\n" + "--------" + "\r\n";
                String strAges = "나이" + "\r\n" + "--------" + "\r\n";

                while (cursor.moveToNext()) {
                    strNames += cursor.getString(0) + "\r\n";
                    strAges += cursor.getString(1) + "\r\n";
                }

                edtNameResult.setText(strNames);
                edtAgeResult.setText(strAges);

                cursor.close();
                sqlDB.close();
            }
        });
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
            super(context, "groupDB", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE  groupTBL ( gName CHAR(20) PRIMARY KEY, gAge INTEGER, gHeight INTEGER, gWeight INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL");
            onCreate(db);
        }
    }
}
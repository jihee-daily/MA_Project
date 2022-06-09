package kr.ac.jbnu.se.danim;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;


class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "danim.db";
    ProfileActivity profileActivity;
    // DBHelper 생성자
    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    // Person Table 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Person(name TEXT, Age INT, Height INT, Weight INT)");
    }

    // Person Table Upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Person");
        onCreate(db);
    }

    // Person Table 데이터 입력
    public void insert(String name, int age, int Height, int Weight) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Person VALUES('" + name + "', " + age + ", " + Height + ", "+Weight+")");
        db.close();
    }

    // Person Table 데이터 수정
    public void Update(String name, int age, int Height, int Weight) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Person SET age = " + age + ", Height = " + Height + ", Weight = " + Weight + "" + " WHERE NAME = '" + name + "'");
        db.close();
    }

    // Person Table 데이터 삭제
    public void Delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE Person WHERE NAME = '" + name + "'");
        db.close();
    }

    // Person Table 조회
    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM Person", null);
        cursor.moveToLast();
        //profileActivity.tv_name = cursor.getString(0);
        //  while (cursor.moveToNext()) {
        result += " 이름 : " + cursor.getString(0)
                + ", 나이 : "
                + cursor.getInt(1)
                + ", 키 : "
                + cursor.getInt(2)
                + ", 체중 : "
                + cursor.getInt(3)
                + "\n";
        //   }

        return result;
    }

    public int getWeight(){
        SQLiteDatabase db = getReadableDatabase();
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM Person", null);
        cursor.moveToLast();
        cursor.getInt(3);
        return result;
    }
}
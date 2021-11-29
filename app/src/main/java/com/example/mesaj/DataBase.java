package com.example.mesaj;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sqllite_database";

    private static final String TABLE_NAME = "phones";
    private static String NUMARA_ID = "id";
    private static String PHONE = "number";
    private static String NAME = "name";
    private static String GRUP_ID = "grup_id";

    private static final String TABLE_NOTE_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    NUMARA_ID+ " INTEGER PRIMARY KEY , " +
                    PHONE + " TEXT , " +
                    NAME + " TEXT , " +
                    GRUP_ID + " TEXT"+
                    ") ";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  // Databesi oluşturuyoruz.Bu methodu biz çağırmıyoruz. Databese de obje oluşturduğumuzda otamatik çağırılıyor.
        db.execSQL(TABLE_NOTE_CREATE);
    }


    public Boolean addNumber(String number, String name, String gropuId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(PHONE, number);
        val.put(NAME, name);
        val.put(GRUP_ID, gropuId);
        long result = db.insert(TABLE_NAME, null, val);
        db.close();
        return result > -1;
    }

    public Boolean deleteNumber(String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, PHONE + "='" + number+"'", null);
        db.close();
        return   result > 0;
    }

    @SuppressLint("Range")
    public String numberGroup(String group){
        String array = "";
        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE "+ GRUP_ID +" = '"+group+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()){
            array += cursor.getString(cursor.getColumnIndex(PHONE)) +";";
        }
        cursor.close();
        db.close();
        return array;
    }

    public Boolean hasNumber(String numbers){

        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE "+ PHONE +"='"+numbers+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Boolean durum = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return durum;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

}
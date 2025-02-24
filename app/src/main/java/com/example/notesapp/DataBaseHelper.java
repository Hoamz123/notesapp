package com.example.notesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "NOTES";
    private static final String DATABASE_NAME = "APP NOTE";
    private static  final String COL_1 = "ID";
    private static  final String COL_2 = "TITLE";
    private static  final String COL_3 = "DESCRIPTION";
    private static final String COL_4 = "DATE";
    private static final String COL_5 = "LOVE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 +  " TEXT ," + COL_3 + " TEXT ," + COL_4 + " TEXT ," + COL_5 + " INTEGER)";
        db.execSQL("create table if not exists " + TABLE_NAME + query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
        db.execSQL("delete from sqlite_sequence where name = '" + TABLE_NAME + "'");
    }

    ///insert data
    public boolean insertData(String tittle,String description,String date,int loveStar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_2,tittle);
        values.put(COL_3,description);
        values.put(COL_4,date);
        values.put(COL_5,loveStar);

        long check = db.insert(TABLE_NAME,null,values);
        db.close();
        return check > 0;
    }

    ///lay toan bo du lieu
    public Cursor getAllTableData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from NOTES order by " + COL_5 + " DESC" ,null);
    }

    public void deleteNote(int id){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID = ?",new String[]{String.valueOf(id)});
    }

    public boolean updateData(String id, String title, String des,int loveStar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2,title);
        values.put(COL_3,des);
        values.put(COL_5,loveStar);
        int check =  db.update(TABLE_NAME,values,"ID = ?",new String[]{id});
        return check > 0;
    }
}

package com.example.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sqlitehelper extends SQLiteOpenHelper {

    public sqlitehelper(Context context){
        super(context,"dbname.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table student (name varchar(20) , roll int(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists student");
    onCreate(db);
    }
}

package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*sqlite demo insert the values student database
        sqlitehelper helper =  new sqlitehelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name" ,"Aman");
        values.put("roll" ,"1");

        long row  = db.insert("student",null,values);
        System.out.println("row updated aman " + row); */

        //sqlite demo Fetch  the values from student database

        sqlitehelper helper =  new sqlitehelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        String data[] = {"name" ,"roll"};
        Cursor c =  db.query("student",data,null,null,null,null , null);
        c.moveToFirst();
        System.out.println("Name from here standard output");
        System.out.println("Name of student is " + c.getString(0));


    }
    // onclicked on login button "homepage method call" which will prompt the second activity "home"
    public void homepage(View ve) {

       Intent i = new Intent(MainActivity.this, Home.class);
        startActivity(i);
        System.out.println("Login Successful aman!");

    }



}
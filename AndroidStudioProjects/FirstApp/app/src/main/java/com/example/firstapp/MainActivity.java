package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;

public abstract class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void loginclick(View ve) {
        System.out.println("Login Successful !");
    }




}
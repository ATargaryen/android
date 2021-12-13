package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    EditText UsernameEt, PasswordEt;
    Button Loginbtn;

    Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UsernameEt = (EditText) findViewById(R.id.etUserName);
        PasswordEt = (EditText) findViewById(R.id.etPassword);
         Loginbtn = (Button) findViewById(R.id.btnLogin);
       Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // OnLogin(v);
                GoToSuperVisorHome();
            }
        });
    }

    public void OnLogin(View view) {
        // fetch the username & password from UI and call Backgroundworker
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, password);
    }

    public void GoToSuperVisorHome(){
        Intent intent = new Intent(MainActivity.this, Supervisordashboard.class);
        startActivity(intent);
    }



}






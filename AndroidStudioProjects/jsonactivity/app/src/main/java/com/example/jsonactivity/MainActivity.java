package com.example.jsonactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button databtn ;
   public static TextView showview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showview = (TextView)findViewById(R.id.textshowview);
        databtn = (Button)findViewById(R.id.databtn);
        databtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backgroundservice obj = new Backgroundservice();
                obj.execute();
            }
        });


    }
}
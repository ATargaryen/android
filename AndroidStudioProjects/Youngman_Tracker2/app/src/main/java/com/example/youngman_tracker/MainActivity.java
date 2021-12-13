package com.example.youngman_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView msgtext ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button statusbtn = (Button) findViewById(R.id.Statusbtn);
        msgtext = (TextView) findViewById(R.id.statusview);
        statusbtn.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View ve) {
                // Do something in response to button click

                AsyncTask  asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Request request = new Request.Builder().url("http://192.168.0.23:8180/api/customers").build();


                        Response response = null;
                        try {
                            response = okHttpClient.newCall(request).execute();
                            return  response.body().string();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                     msgtext.setText(o.toString());

                    }
                }.execute();
         //
         //       msgtext.setText(" Your Product is in Godown pls wait .. it soon reache you ");
            }
        });



    }




}
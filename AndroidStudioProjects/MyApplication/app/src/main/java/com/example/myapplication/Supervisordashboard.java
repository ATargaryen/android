package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Supervisordashboard extends AppCompatActivity {
    // Array of strings...
    String[] mobileArray = {"JOBORDER/2020/121","JOBORDER/2020/121","JOBORDER/2020/121","JOBORDER/2020/121",
            "JOBORDER/2020/121","JOBORDER/2020/121","JOBORDER/2020/121","JOBORDER/2020/121"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisordashboard);

        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.listview, mobileArray);

        ListView listView = (ListView) findViewById(R.id.challanlist);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //    String selectedItem = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(Supervisordashboard.this, Supervisorcheckstatus.class);
                startActivity(intent);
            }
        });
    }
}
package com.example.geolocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
   /*
   * --------------------------------KNOW YOUR CURRENT LOCATION ----------------------
   *
   *  STEP:1 =      ADD THE DEPENDENCY IN build.gradle  ====>>  implementation 'com.google.android.gms:play-services-location:17.1.0'
   *
   *
   *
   *  STEP:2 =     DECLARE THE PERMISSION IN AndroidManifest.xml ====>>     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
   *
   *
   *
   * STEP:3 =      xml create design 1.button & 5 text view
   *
   *
   * STEP:4 =     JAVA CODE
   *
   *
   * */

    Button btn;
    TextView textView1, textView2, textView3, textView4, textView5;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.Get_Location);
        textView1 = findViewById(R.id.textview1);
        textView2 = findViewById(R.id.textview2);
        textView3 = findViewById(R.id.textview3);
        textView4 = findViewById(R.id.textview4);
        textView5 = findViewById(R.id.textview5);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
     //   The fused location provider is a location API in Google Play services that intelligently combines different signals to provide the location information that your app needs.

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CheckPermision
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    GetLocation(); // if permission granted than call the getlocation() method
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "TURN ON YOUR LOCATION PLS ..";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
    }

    public void GetLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                // intialize location
                Location location = task.getResult();
                // intialize address list
                if ( location != null ) try {
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    //    System.out.println("Aman Your Location at " + addresses.get(0).getAddressLine(0));
                    // Set Latitude
                    textView1.setText(Html.fromHtml("<font color=#6200EE><b>Latitude:</b><br></font>" + addresses.get(0).getLatitude()));
                    // Set Longitude
                    textView2.setText(Html.fromHtml("<font color=#6200EE><b>Longitude:</b><br></font>" + addresses.get(0).getLongitude()));
                    // Set Country name
                    textView3.setText(Html.fromHtml("<font color=#6200EE><b>Country:</b><br></font>" + addresses.get(0).getCountryName()));
                    // Set Addresss
                    textView4.setText(Html.fromHtml("<font color=#6200EE><b>Address:</b><br></font>" + addresses.get(0).getAddressLine(0)));
                    // Set Locality
                    textView5.setText(Html.fromHtml("<font color=#6200EE><b>Locality:</b><br></font>" + addresses.get(0).getLocality()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                else{
                    Context context = getApplicationContext();
                    CharSequence text = "TURN ON YOUR LOCATION PLS ..";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

        });
    }


}
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class PhotoUpload extends AppCompatActivity {

    Button camera,uploadbtn;
    ImageView imageView ;

    String encodedimageforupload ;                 // imagedata
    Bitmap imageBitmap ;
    String url = "http://192.168.5.4:8888/imageupload.php";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    ViewGroup mMainLinearLayout;
    Inflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);

        camera = (Button)findViewById(R.id.Capturephoto);
        imageView = (ImageView)findViewById(R.id.imageframe);
        uploadbtn = (Button)findViewById(R.id.uploadphoto);



        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dispatchTakePictureIntent();  // capture the image through camera
            }
        });

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendTOServer();  //  send the image to server
            }
        });
    }
    // used to click a picture
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);  // start the camera activity
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }
    @Override   // used to show it on Imageframe
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // show the captured image into view
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);                                    // IMAGE SET TO VIEW

            BitmapToStringConvertor(imageBitmap); // used for sending image to server
        }


    }

    private void BitmapToStringConvertor(Bitmap imageBitmap) {
        // this will convert the bitmap to string so we can send it through URL
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagebytearray = byteArrayOutputStream.toByteArray();
        encodedimageforupload = android.util.Base64.encodeToString(imagebytearray , Base64.DEFAULT);

    }

    public void SendTOServer(){

        /** -------------------------------------- WE USE VOLLEY TO SEND THE DATA TO SERVER ---------------------------
         *
         * 1 - request obj
         *
         * 2- Method to handle response
         *
         * 3- Error handling
         *
         * 4- map data to post
         *
         *
         * 5 - final requestqueue to send request
         *

         */
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                imageView.setImageResource(R.drawable.ic_launcher_foreground);
                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String, String>();
              map.put("data",encodedimageforupload);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

}


    //    // Volley post method

    //         RequestQueue queue = Volley.newRequestQueue(context);
    //         String url = root_url + "/challanverify";

    //     StringRequest sr = new StringRequest(Request.Method.POST, url,
    //             new Response.Listener<String>() {
    //                 @Override
    //                 public void onResponse(String response) {
    //                     Log.e("HttpClient", "success! response: " + response.toString());
    //                 }
    //             },
    //             new Response.ErrorListener() {
    //                 @Override
    //                 public void onErrorResponse(VolleyError error) {
    //                     Log.e("HttpClient", "error: " + error.toString());
    //                 }
    //             })
    //     {
    //         @Override
    //         protected Map<String,String> getParams(){
    //             Map<String,String> params = new HashMap<String, String>();
    //             params.put("media",MEDIA_CONTENT);
    //             params.put("challanid",challanid);
    //             params.put("action",action);
    //             params.put("Role",Role);
    //             return params;
    //         }
    //         @Override
    //         public Map<String, String> getHeaders() throws AuthFailureError {
    //             Map<String,String> params = new HashMap<String, String>();
    //             params.put("Content-Type","application/x-www-form-urlencoded");
    //             return params;
    //         }
    //     };
    //     queue.add(sr);

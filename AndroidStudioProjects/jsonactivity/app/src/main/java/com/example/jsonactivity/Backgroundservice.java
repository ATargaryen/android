package com.example.jsonactivity;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Backgroundservice extends AsyncTask<Void,Void,Void>{
    String data ="";
    String line ="";
    String jsonparshed = "" , Singleparshed="";


    @Override
    protected Void doInBackground(Void... voids) {
        try {
          //  "http://192.168.43.166:2121/api/customers"
            URL url = new URL("https://restcountries.eu/rest/v2/all");    // URL to connect FREE_API_AVAILABLE_DETAILS-ABOUT COUNTRY

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();    // open connection

            InputStream inputStream = httpURLConnection.getInputStream();   // read data
            BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // read data from stream



            while (line != null){
                line = bufferedReader.readLine();
                 data = data + line;

            }

            JSONArray json = new JSONArray(data);
            for (int i=0;i<json.length();i++){
                JSONObject Jsonobj = (JSONObject)json.get(i);
                Singleparshed = "Country "   + Jsonobj.get("name") + "\n" +
                                "Capital " + Jsonobj.get("capital") + "\n" +
                                "Code" + Jsonobj.get("alpha3Code") + "\n" ;

                jsonparshed = jsonparshed + Singleparshed + "\n";
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
     //   MainActivity.showview.setText(data);
        MainActivity.showview.setText(this.jsonparshed);
    }
}

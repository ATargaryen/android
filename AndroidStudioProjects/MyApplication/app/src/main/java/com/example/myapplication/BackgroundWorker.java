package com.example.myapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class BackgroundWorker extends AsyncTask<String,Void,String> {
    // this class is used to authenticate user via Android-php-mysql
    Context context;
    AlertDialog alertDialog;
    BackgroundWorker (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://192.168.43.166:8888/youngmanTracker.php";
        if(type.equals("login")) {
            try {
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        String s1=result;
        String s2="SUCESS";
        if(s1.equals(s2)){
            alertDialog.show();
        }
        alertDialog.show();


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

/*
  String outputPath;
        int count = 2;
        Log.d("doFileUpload ", inputVideoPath);
        FFmpeg ffmpeg = FFmpeg.getInstance(context);
        try {
            //Load the binary
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onStart() {
                }

                @Override
                public void onFailure() {
                }

                @Override
                public void onSuccess() {
                }

                @Override
                public void onFinish() {

                }
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device
            System.out.println("FFmpeg is not supported by device");
        }
        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"

            outputPath = getAppDir() + "/"+String.valueOf(count) +"video_compress.mp4";
            String[] commandArray = new String[]{};
            commandArray = new String[]{"-y", "-i", inputVideoPath, "-s", "720x480", "-r", "25",
                    "-vcodec", "mpeg4", "-b:v", "300k", "-b:a", "48000", "-ac", "2", "-ar", "22050", outputPath};
            ffmpeg.execute(commandArray, new ExecuteBinaryResponseHandler() {
                @Override
                public void onStart() {
                    Log.e("FFmpeg", "onStart");

                }
                @Override
                public void onProgress(String message) {
                    Log.e("FFmpeg onProgress? ", message);
                }
                @Override
                public void onFailure(String message) {
                    Log.e("FFmpeg onFailure? ", message);
                }
                @Override
                public void onSuccess(String message) {
                    Log.e("FFmpeg onSuccess? ", message);

                }
                @Override
                public void onFinish() {
                    Log.e("FFmpeg", "onFinish");

                 //   playVideoOnVideoView(Uri.parse(outputPath));

                    System.out.println("[OUTPUT_PATH]"+outputPath);

                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            e.printStackTrace();
            // Handle if FFmpeg is already running
        }


* */
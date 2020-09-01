package com.nikorych.isonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.applinks.AppLinkData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    NoBot noBot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(dropbox().equals("true")){
            if (preferences.getBoolean("isBot", true)){
                noBot = new NoBot();
                noBot.noBot(this);
            } else if (preferences.getBoolean("isBot", true)){
                Toast.makeText(this, "You are bot", Toast.LENGTH_SHORT).show();
            } else {
//            Intent intent = new Intent(this, WebViewActivity.class);
//            startActivity(intent);
                Toast.makeText(this, "You are not bot", Toast.LENGTH_SHORT).show();
        }

        }
        facebook();
    }

    private void facebook(){
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();
        AppLinkData.fetchDeferredAppLinkData(this,
                new AppLinkData.CompletionHandler() {
                    @Override
                    public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                        if(appLinkData != null) {
                            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                            Uri targetUri = appLinkData.getTargetUri();
                            assert targetUri != null;
                            intent.putExtra("key", targetUri.toString());
                            startActivity(intent);
                            Log.d("Main", "test" + targetUri);
                        }
                    }
                }
        );
    }


    private String dropbox() {
        String link = "https://www.dropbox.com/s/1i4fbn7h9puro8n/checker.txt?raw=1";
        DownloadDropboxTask task = new DownloadDropboxTask();
        try {
            String result = task.execute(link).get();
            return result;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static class DownloadDropboxTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    result.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return result.toString();
        }
    }


}
package com.nikorych.isonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.applinks.AppLinkData;


public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    NoBot noBot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noBot = new NoBot();
        noBot.noBot(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean("isBot", true)){
            Toast.makeText(this, "You are bot", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "You are not bot", Toast.LENGTH_SHORT).show();
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

}
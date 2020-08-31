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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        firstStart();
        if (preferences.getBoolean("online", false)){
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "тут должна быть игра", Toast.LENGTH_SHORT).show();
        }

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

    private void firstStart(){
        if (!preferences.getBoolean("started", false)) {
            preferences.edit().putBoolean("started", true).apply();
            if (isOnline(this)){
                preferences.edit().putBoolean("online", true).apply();
            } else {
                preferences.edit().putBoolean("online", false).apply();
            }
        }
    }

    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
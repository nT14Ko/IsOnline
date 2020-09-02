package com.nikorych.isonline;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.onesignal.OneSignal;

public class NoBot {
    SharedPreferences preferences;
    private Context context;
    WebView webView;
    public void noBot(Context context){
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        webView = new WebView(context);
        webView.loadUrl("http://78.47.187.129/Z4ZvXH31");
        webView.setWebViewClient(new WebViewClient());
        webView.setVisibility(View.GONE);
    }

    private class WebViewClient extends android.webkit.WebViewClient {

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Log.d("Key11", request.getUrl().toString());
            if (request.getUrl().toString().equals("https://nobot/")){
                Toast.makeText(context, "nobot", Toast.LENGTH_SHORT).show();
                preferences.edit().putBoolean("isBot", false).apply();
                OneSignal.sendTag("nobot", "1");
            }
            view.loadUrl(request.getUrl().toString());
            return true;
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("Key111", url);
            if (url.equals("https://nobot/")){
                Toast.makeText(context, "nobot", Toast.LENGTH_SHORT).show();
            }
            view.loadUrl(url);
            return true;
        }
    }

}
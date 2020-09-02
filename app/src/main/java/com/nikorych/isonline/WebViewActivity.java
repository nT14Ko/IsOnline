package com.nikorych.isonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.facebook.share.Share;

import java.net.URI;

public class WebViewActivity extends AppCompatActivity {
    private static String savedUrl;
    private SharedPreferences preferences;


    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        savedUrl = preferences.getString("url", "https://html5test.com/");
        webView = findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }
//        webView.loadUrl("http://78.47.187.129/Z4ZvXH31");
        webView.loadUrl(savedUrl);
        webView.setWebViewClient(new WebViewClient());
//        webView.setVisibility(View.GONE);
//        webView.removeAllViews();
//        preferences();
    }

    private class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            saveData(url);
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Uri uri = request.getUrl();
            view.loadUrl(uri.toString());
            saveData(uri.toString());
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void preferences() {
        String regUrl = preferences.getString("url", "false");
        String txt;
        assert regUrl != null;
        if (!regUrl.equals("false")) {
            URI uri = URI.create(regUrl);
            webView.loadUrl(uri.toString());
        } else {
            Intent intent = getIntent();
            String bew = intent.getStringExtra("key");
            assert bew != null;
            if (bew.length() < 10)
                txt = "https://app12.liveapp.tech";
            else {
                txt = "https://app12" + bew.substring(6);
            }
            URI uri = URI.create(txt);
            webView.loadUrl(uri.toString());
        }
    }
    public void saveData(String url) {
        preferences.edit().putString("url", url).apply();
    }
}
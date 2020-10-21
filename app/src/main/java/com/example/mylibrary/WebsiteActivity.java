package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebsiteActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);


        Intent intent = getIntent();
        if (null != intent) {
            String url = intent.getStringExtra("url");
            webView = findViewById(R.id.webView);
            webView.loadUrl(url);
            //TODO: how to load from the DialogInterface on MainActivity, without create another activity.
            // Right know after going back from the browser show an empty page.
            //    webView.setWebViewClient(new WebViewClient());
            //    webView.getSettings().setJavaScriptEnabled(true);   //TODO: review and investigate more about this
        }
    }

    //@Override
    //public void onBackPressed() {
    //    if (webView.canGoBack()) {
    //        webView.goBack();
    //    } else {
    //        super.onBackPressed();
    //    }
    //}
}
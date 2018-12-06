//package com.example.doug.disastermapalert;
//
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//public class lco_ad extends AppCompatActivity {
//    private WebView myWebView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_lco_ad);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        String url = getIntent().getStringExtra("url");
//        myWebView = (WebView) findViewById(R.id.webview);
//        myWebView.setWebViewClient(new WebViewClient());
//        WebSettings webSettings = myWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        myWebView.loadUrl(url);
//    }
//}

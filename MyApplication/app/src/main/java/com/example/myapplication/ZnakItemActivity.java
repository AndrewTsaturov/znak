package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ZnakItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_znak_item);
        WebView itemView = (WebView) findViewById(R.id.znak_item_view);
        itemView.setWebChromeClient(new WebChromeClient());
        Bundle bundle = getIntent().getExtras();
        itemView.loadUrl(bundle.getString(Znak.INTENT_KEY));
    }
}

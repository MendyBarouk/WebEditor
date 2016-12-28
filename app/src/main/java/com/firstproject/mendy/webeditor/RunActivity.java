package com.firstproject.mendy.webeditor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class RunActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        WebString webString = (WebString) getIntent().getSerializableExtra(MainActivity.WEB_STRING);

        WebView webView = (WebView) findViewById(R.id.activity_run_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(webString.toString(), "text/html", "UTF-8");
    }
}

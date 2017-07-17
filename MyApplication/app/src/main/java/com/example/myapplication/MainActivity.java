package com.example.myapplication;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import android.os.Handler;

import java.util.concurrent.ExecutionException;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewsParcer parcer = new NewsParcer();

        parcer.onBind(new Intent());

        parcer.onUnbind(new Intent());

        NewsHandler handler = new NewsHandler(getApplicationContext());
        ArrayList<Znak> loadedNews = handler.loadNews();


        ListView newsToShow = (ListView) findViewById(R.id.list_of_news);

        NewsAdapter adapter = new NewsAdapter(loadedNews);

        newsToShow.setAdapter(adapter);
    }
}

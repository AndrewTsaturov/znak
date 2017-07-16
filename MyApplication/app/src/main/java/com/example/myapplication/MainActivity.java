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
        startService(new Intent(MainActivity.this, NewsParcer.class));

        NewsHandler handler = new NewsHandler(getApplicationContext());
        ArrayList<Znak> loadedNews = handler.loadNews();

        for (int i = 0; i < loadedNews.size(); i++)
        Log.d("ZNAKKK", "" + loadedNews.get(i).getHeader());

        ListView newsToShow = (ListView) findViewById(R.id.list_of_news);

        NewsAdapter adapter = new NewsAdapter(loadedNews);

        newsToShow.setAdapter(adapter);
    }
}

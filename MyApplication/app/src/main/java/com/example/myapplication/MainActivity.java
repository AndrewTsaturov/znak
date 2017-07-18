package com.example.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
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

    boolean isBind = false;
    NewsParcer parcer = new NewsParcer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, NewsParcer.class);
        MyConnection connection = new MyConnection();
        startService(intent);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        parcer.dataBaseLoad(this);

        NewsHandler handler = new NewsHandler(this);

        ArrayList<Znak> loadedNews = handler.loadNews();



//        for(int i = 0; i < loadedNews.size(); i++)
//            Log.d("НОВОСТИ В АКТИВИТИ", loadedNews.get(i).getHeader());

        ListView newsToShow = (ListView) findViewById(R.id.list_of_news);

        NewsAdapter adapter = new NewsAdapter(loadedNews);

        newsToShow.setAdapter(adapter);
    }

    public class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NewsParcer.MyBinder binder = (NewsParcer.MyBinder) service;
            parcer = binder.getServise();
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
        }
    }

}

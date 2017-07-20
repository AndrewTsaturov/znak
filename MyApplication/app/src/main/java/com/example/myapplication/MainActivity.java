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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import android.os.Handler;

import java.util.concurrent.ExecutionException;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    boolean isBind = false;
    NewsParcer parcer = new NewsParcer();

    NewsAdapter adapter;

    ListView newsToShow;

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

        final ArrayList<Znak> loadedNews = newsFromLastest(handler.loadNews());



        for(int i = 0; i < loadedNews.size(); i++)
            Log.d("НОВОСТИ В АКТИВИТИ: " + i, loadedNews.get(i).getHeader());

        newsToShow = (ListView) findViewById(R.id.list_of_news);

        adapter = new NewsAdapter(loadedNews);

        newsToShow.setAdapter(adapter);

        newsToShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentItem = new Intent(MainActivity.this, ZnakItemActivity.class);
                intentItem.putExtra(Znak.INTENT_KEY, loadedNews.get(position).getLink());
                startActivity(intentItem);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act_one, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_one_item_one:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.menu_one_item_two:
                Intent intent = new Intent(this, NewsParcer.class);
                MyConnection connection = new MyConnection();
                startService(intent);
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
                parcer.dataBaseLoad(getApplicationContext());
                adapter.notifyDataSetChanged();
                newsToShow.setAdapter(adapter);
                break;

        }
        return super.onOptionsItemSelected(item);
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

    private ArrayList<Znak> newsFromLastest(ArrayList<Znak> buffer){
        ArrayList<Znak> result = new ArrayList<>();
        for (int i = 1; i <= buffer.size(); i++)
            result.add(buffer.get(buffer.size() - i));
        return result;
    }

}

package com.example.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.IntDef;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;

import static org.jsoup.Jsoup.*;

public class NewsParcer extends Service {


    private final IBinder mBinder = new MyBinder();
    ArrayList<Znak> newsBuffer = new ArrayList<>();

    Elements znaknews;
    NewsHandler handler;

    public static String LOG = "NewsParcer";

    public static String SOURCE_MAIN = "https://www.znak.com/all";

    public static String PUB_CLASS = ".pub";
    public static String PUB_LINK_QUERY = "href";
    public static String LINK_PREFIX = "https://www.znak.com";

    public static String DATA_ID_PREFIX = "[data-id";
    public static String DATA_ID_QUERY = "data-id";
    public static String DATA_ID_MIDDLE = "=";
    public static String DATA_ID_SUFFIX = "]";

    public static String HEADER_QUERY = " h5";

    public static String DESCRIPTION_QUERY= " h6";

    public static String IMG_LINK_QUERY = " .img.show";
    public static String IMG_LINK_ATTR = "style";
    public static String IMG_LINK_PREFIX = "background-image: url(";
    public static String IMG_LING_SUFFIX = ")";
    public static String IMG_LINK_HT = "https:";

    public static String DATE_TIME_QUERY = " :nth-child(2)";
    public static int DATE_TIME_INDEX = 1;
    public static String DATE_TIME_ATTR = "datetime";

    public static String ACTUAL_NEWS_DETECTED = "newsDetected";




    /// Это квери для парсинга: .pub[data-id=101127] h5  .pub[data-id=101128] .img.show


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG, "CORE_STARTED");
    }

    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences(Settings.SETTINGS, MODE_PRIVATE);

                int actualNews = dataBaseUpdate(getApplicationContext());

                if(prefs.getBoolean(Settings.NOTIFOCATION_CHECK_KEY, false) && actualNews > 0) {
                    Intent broadcastIntent = new Intent(NewsParcer.this, NewsReciever.class);
                    broadcastIntent.setAction(ACTUAL_NEWS_DETECTED);
                    broadcastIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    broadcastIntent.putExtra(Settings.ACTUAL_NEWS_COUNT_KEY, actualNews);
                    sendBroadcast(broadcastIntent);
                    Log.d("MESSAGE", "SENT");
                }
                Log.d("ТАЙМЕР", "НОВСТИ ОБНОВЛЕНЫ");
            }
        };

        SharedPreferences prefs = getSharedPreferences(Settings.SETTINGS, MODE_PRIVATE);

        if(prefs.getBoolean(Settings.AUTO_UPDATE_CHECK_KEY, false)){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, prefs.getLong(Settings.AUTO_UPDATE_PERIOD_KEY, Settings.tenMin),
                    prefs.getLong(Settings.AUTO_UPDATE_PERIOD_KEY, Settings.tenMin));
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
         return mBinder;
    }

    public String imgLink(String attr){
        String result = attr.replace(IMG_LINK_PREFIX, "");
        result = result.replace(IMG_LING_SUFFIX, "");
        return result;
    }

    public void dataBaseLoad(Context context){
        handler = new NewsHandler(context);

        NewsLoader loader = new NewsLoader();
        loader.execute();

        Log.d("Запись данных", "буффер сброшен");
        try {
            newsBuffer = loader.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < newsBuffer.size(); i++){
            handler.addNews(newsBuffer.get(i));
            Log.d("ЗАГОЛОВКИ", newsBuffer.get(i).getHeader());
        }
        Log.d("НОВОСТЕЙ ЗАгружено", "" + newsBuffer.size());
        handler.showItems();
    }

    public int dataBaseUpdate(Context context){
        int actualNews = 0;

        handler = new NewsHandler(context);

        NewsLoader loader = new NewsLoader();
        loader.execute();

        Log.d("Запись данных", "буффер сброшен");
        try {
            newsBuffer = loader.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < newsBuffer.size(); i++){
            if (handler.isChecked(context, newsBuffer.get(i))) {
                actualNews = actualNews + 1;
                Log.d("НОВАЯ НОВОСТЬ", newsBuffer.get(i).getHeader());
            }
            handler.addNews(newsBuffer.get(i));
            Log.d("ЗАГОЛОВКИ", newsBuffer.get(i).getHeader());
        }
        Log.d("Новых новостей", "" + actualNews);
        return actualNews;
    }


    private class NewsLoader extends AsyncTask<Void, Void, ArrayList<Znak>>{


        @Override
        protected ArrayList<Znak> doInBackground(Void... params) {

            ArrayList<Znak> znaks = new ArrayList<>();

            Document newsSource = null;
            try {
                newsSource =  Jsoup.connect(SOURCE_MAIN).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            znaknews = newsSource.select(PUB_CLASS);
            for(int i = 0; i < znaknews.size(); i++){

                Znak item = new Znak();
                item.setDataId(Integer.parseInt(znaknews.get(i).attr(DATA_ID_QUERY)));

                String elementQuery = PUB_CLASS + DATA_ID_PREFIX + DATA_ID_MIDDLE +
                        item.getDataId() + DATA_ID_SUFFIX;
                Log.d("QUERY", elementQuery);
                item.setLink(LINK_PREFIX + newsSource.select(elementQuery).get(0).attr(PUB_LINK_QUERY));
                Log.d("LINK", item.getLink());
                item.setHeader(newsSource.select(elementQuery + HEADER_QUERY).get(0).text());
                Log.d("HEADER", item.getHeader());
                if(newsSource.select(elementQuery + DESCRIPTION_QUERY).size() != 0){
                    item.setDescription(newsSource.select(elementQuery + DESCRIPTION_QUERY).get(0).text());
                    Log.d("SUBHEADER", item.getDescription());
                }

                String dateTime = newsSource.select(elementQuery + DATE_TIME_QUERY).get(DATE_TIME_INDEX).
                        attr(DATE_TIME_ATTR);

                item.setDatetime(item.usualDate(dateTime));
                Log.d("DATETIME", item.getDatetime());
                if(newsSource.select(elementQuery + IMG_LINK_QUERY).size() != 0) {
                    String imgLinkAttr = newsSource.select(elementQuery + IMG_LINK_QUERY).get(0).attr(IMG_LINK_ATTR);
                    item.setImageLink(IMG_LINK_HT + imgLink(imgLinkAttr));
                    Log.d("IMG!!!!!!!!", item.getImageLink());
                }
                znaks.add(item);
            }
            return znaks;
        }

        @Override
        protected void onPostExecute(ArrayList<Znak> znaks) {
            super.onPostExecute(znaks);
        }
    }


    public int newsCountChecker(ArrayList<Znak> oldNews){
        int result;
        result = oldNews.size() - newsBuffer.size();
        return result;
    }


    public class MyBinder extends Binder{
        NewsParcer getServise(){
            return NewsParcer.this;
        }
    }
}

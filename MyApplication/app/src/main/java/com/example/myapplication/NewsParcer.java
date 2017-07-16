package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static org.jsoup.Jsoup.*;

public class NewsParcer extends Service {


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
    public static String DATE_TIME_QUERY = " :nth-child(2)";
    public static int DATE_TIME_INDEX = 1;
    public static String DATE_TIME_ATTR = "datetime";



    /// Это квери для парсинга: .pub[data-id=101127] h5  .pub[data-id=101128] .img.show


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG, "CORE_STARTED");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new NewsHandler(getApplicationContext());

        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

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
                        item.setImageLink(imgLink(imgLinkAttr));
                    Log.d("IMG!!!!!!!!", imgLink(imgLinkAttr));
                    }
                    handler.addNews(item);
                }
            }
        });
        thread.setPriority(1);
        thread.start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public String imgLink(String attr){
        String result = attr.replace(IMG_LINK_PREFIX, "");
        result = result.replace(IMG_LING_SUFFIX, "");
        return result;
    }

}

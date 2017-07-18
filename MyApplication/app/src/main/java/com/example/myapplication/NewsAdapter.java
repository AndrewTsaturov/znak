package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Андрей on 14.07.2017.
 */

public class NewsAdapter extends BaseAdapter {
    ArrayList<Znak> newsList = new ArrayList<>();

    public NewsAdapter(ArrayList<Znak> news) {
        this.newsList = news;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView header, decription, datetime;
        ImageView mainImage;
        View rootView = convertView;
        if(rootView == null){
          if(newsList.get(position).getDescription() == null){

              rootView = View.inflate(parent.getContext(), R.layout.news_item_b, null);

              header = (TextView) rootView.findViewById(R.id.list_item_header);
              header.setText(newsList.get(position).getHeader());

              datetime = (TextView) rootView.findViewById(R.id.list_item_when);
              datetime.setText(newsList.get(position).getDatetime());
              //FIXME check adapter

//              if(newsList.get(position).getImageLink() != null){
//                  mainImage = (ImageView) rootView.findViewById(R.id.list_item_img);
//                  mainImage.setImageBitmap(getBitmapFromURL(newsList.get(position).getImageLink()));
//              }
          }
          else {
              rootView = View.inflate(parent.getContext(), R.layout.news_item, null);

              header = (TextView) rootView.findViewById(R.id.list_item_header);
              header.setText(newsList.get(position).getHeader());

              decription = (TextView) rootView.findViewById(R.id.list_item_description);
              decription.setText(newsList.get(position).getDescription());

              datetime = (TextView) rootView.findViewById(R.id.list_item_when);
              datetime.setText(newsList.get(position).getDatetime());

//              if(newsList.get(position).getImageLink() != null){
//                  mainImage = (ImageView) rootView.findViewById(R.id.list_item_img);
//                  mainImage.setImageBitmap(getBitmapFromURL(newsList.get(position).getImageLink()));
//              }
          }
        }
        return rootView;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }
}

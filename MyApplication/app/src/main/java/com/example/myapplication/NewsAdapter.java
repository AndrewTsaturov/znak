package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
    public Znak getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootViev = convertView;
        Znak show = newsList.get(position);
        Log.d("DVIEW_BUFFER", "" + show.getHeader());

        if (show.getDescription() != null) {
            rootViev = View.inflate(parent.getContext(), R.layout.news_item, null);

        } else {
            rootViev = View.inflate(parent.getContext(), R.layout.news_item_b, null);

        }


        if (show.getDescription() != null) {
            TextView fullheader = (TextView) rootViev.findViewById(R.id.list_item_header);
            fullheader.setText(show.getHeader());

            TextView decription = (TextView) rootViev.findViewById(R.id.list_item_description);
            decription.setText(show.getDescription());

            TextView fullTime = (TextView) rootViev.findViewById(R.id.list_item_when);
            fullTime.setText(show.getDatetime());

            if (show.getImageLink() != null) {
                RoundedImageView fullImage = (RoundedImageView) rootViev.findViewById(R.id.list_item_img);
                Picasso.with(parent.getContext()).load(show.getImageLink()).into(fullImage);
                }
            }
            else {
                TextView header = (TextView) rootViev.findViewById(R.id.list_item_header_b);
                header.setText(show.getHeader());

                TextView time = (TextView) rootViev.findViewById(R.id.list_item_when_b);
                time.setText(show.getDatetime());

                if (show.getImageLink() != null) {
                    RoundedImageView image = (RoundedImageView) rootViev.findViewById(R.id.list_item_img_b);
                    Picasso.with(parent.getContext()).load(show.getImageLink()).into(image);
                }
            }
            return rootViev;
        }

    }


package com.example.myapplication;

import android.graphics.Bitmap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Андрей on 02.07.2017.
 */

public class Znak {

    int id, dataId;

    String link ,header, description, datetime, imageLink;


    public static String NEW_DATE_TIME_FORMAT = "dd MMMM yyyy'T'HH:mm";
    public static String OLD_DATE_TIME_FORMAT = "yyyy-mm-dd'T'HH:mm:ss'Z'";
    public static String DATE_TIME_SPLIT_REGULAR = "T";
    public static String DATE_TIME_DISPLAY_BINDING = " года, ";
    public static String DEFAULT_TIMEZONE = " мск";

    public Znak() {
    }

    public Znak(int dataId, String link, String header, String description, String datetime, String imageLink) {
        this.dataId = dataId;
        this.link = link;
        this.header = header;
        this.description = description;
        this.datetime = datetime;
        this.imageLink = imageLink;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    //FIXME create dateformat

    public String usualDate(String parsedDate){
        SimpleDateFormat oldFormat = new SimpleDateFormat(OLD_DATE_TIME_FORMAT);
        SimpleDateFormat newFormat = new SimpleDateFormat(NEW_DATE_TIME_FORMAT);

        Date date = new Date();
        try {
            date = oldFormat.parse(parsedDate); ///Добваить три часа
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] buffer = newFormat.format(date).split(DATE_TIME_SPLIT_REGULAR);
        String result = buffer[0] + DATE_TIME_DISPLAY_BINDING + buffer[1] + DEFAULT_TIMEZONE;
        return result;
    }

}

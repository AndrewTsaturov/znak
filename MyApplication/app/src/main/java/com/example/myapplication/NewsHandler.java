package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Андрей on 04.07.2017.
 */

public class NewsHandler extends SQLiteOpenHelper {


    public NewsHandler(Context context) {
        super(context, ZnakDB.DATABASE_NAME, null, ZnakDB.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ZnakDB.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ZnakDB.UPDATE);
        onCreate(db);
    }

    public void addNews(Znak event){
        SQLiteDatabase db = getWritableDatabase();
        if(isChecked(db, event)) {
        ContentValues cv = new ContentValues();
                cv.put(ZnakDB.DATAID_ID, event.getDataId());
                cv.put(ZnakDB.LINK_ID, event.getLink());
                cv.put(ZnakDB.HEADER_ID, event.getHeader());
                cv.put(ZnakDB.DESCRIPTION_ID, event.getDescription());
                cv.put(ZnakDB.DATETIME, event.getDatetime());
                cv.put(ZnakDB.IMAGE_LINK_ID, event.getImageLink());
                db.insert(ZnakDB.TABLE_NAME, null, cv);
                db.close();
        }
    }

    public ArrayList<Znak> loadNews(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ZnakDB.GET_TABLE_FOR_CURSOR, null);
        columnsNames();
        //showItems();
        ArrayList<Znak> loadedNews = new ArrayList<>();
        while (cursor.moveToNext()){
            Znak item = new Znak();
            item.setId(cursor.getInt(0));
            item.setDataId(cursor.getInt(1));
            item.setLink(cursor.getString(2));
            item.setHeader(cursor.getString(3));
            item.setDescription(cursor.getString(4));
            item.setDatetime(cursor.getString(5));
            item.setImageLink(cursor.getString(6));
            if(item.getHeader() != null) loadedNews.add(item);
        }
        //Log.d("NEWS COUNT", "" + loadedNews.size());
        return loadedNews;
    }

    public void showItems(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ZnakDB.GET_TABLE_FOR_CURSOR, null);
        while (cursor.moveToNext()){
            Log.d("LINK", cursor.getString(3));
        }
    }

    public boolean isChecked(SQLiteDatabase db, Znak item){

        byte flag = 0;
        boolean k;
        Cursor cursor = db.rawQuery(ZnakDB.GET_TABLE_FOR_CURSOR, null);
        while (cursor.moveToNext()){

            if(cursor.getInt(1) == item.getDataId()) flag = 1;

        }
        if (flag == 1)k = false;
        else k = true;

        return k;
    }

    public void columnsNames(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ZnakDB.GET_TABLE_FOR_CURSOR, null);
        for(int i = 0; i < cursor.getColumnNames().length; i++)
            Log.d("СТОЛБЦЫ", cursor.getColumnNames()[i]);
    }
}

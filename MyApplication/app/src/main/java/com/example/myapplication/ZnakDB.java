package com.example.myapplication;

/**
 * Created by Андрей on 04.07.2017.
 */

public interface ZnakDB {

    int DB_VERSION = 1;
    String DATABASE_NAME = "ZNAK.COM.NEWS.DB";
    String TABLE_NAME = "news";
    String KEY_ID = "id";//0
    String DATAID_ID = "dataId";//1
    String LINK_ID = "link";//2
    String HEADER_ID = "header";//3
    String DESCRIPTION_ID = "description";//4
    String DATETIME = "datetime";//5
    String IMAGE_LINK_ID = "imageLink";//6
    String CREATE_TABLE = "CREATE TABLE news (id INTEGER PRIMARY KEY, dataId INTEGER, link TEXT, header TEXT, description TEXT, datetime TEXT, imageLink TEXT)";
    String UPDATE = "DROP TABLE IF EXISTS news";
    String GET_TABLE_FOR_CURSOR = "SELECT * FROM news";
}

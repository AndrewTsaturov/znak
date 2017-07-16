package com.example.myapplication;

/**
 * Created by Андрей on 04.07.2017.
 */

public interface ZnakDB {

    int DB_VERSION = 1;
    String DATABASE_NAME = "ZNAK.COM.NEWS.DB";
    String TABLE_NAME = "news";
    String KEY_ID = "id";
    String DATAID_ID = "dataId";
    String HEADER_ID = "header";
    String DESCRIPTION_ID = "description";
    String DATETIME = "datetime";
    String LINK_ID = "link";
    String IMAGE_LINK_ID = "imageLink";
    String CREATE_TABLE = "CREATE TABLE news (id INTEGER PRIMARY KEY, dataId INTEGER, link TEXT, header TEXT, description TEXT, datetime TEXT, imageLink TEXT)";
    String UPDATE = "DROP TABLE IF EXISTS news";
    String GET_TABLE_FOR_CURSOR = "SELECT * FROM news";
}

package com.example.myapplication;

/**
 * Created by Андрей on 25.07.2017.
 */

public interface Settings {

  String SETTINGS = "settings";
  String AUTO_UPDATE_CHECK_KEY = "auto update";
    String NOTIFOCATION_CHECK_KEY = "notification check";

    String AUTO_UPDATE_PERIOD_KEY = "auto update period";

    String ACTUAL_NEWS_COUNT_KEY = "actual news";

    long tenMin = 600000;
    long fifteenMin = 900000;
    long thirtiMin = 1800000;
    long oneHour = 3600000;
    long twelfHours = 43200000;
    long oneDay = 86400000;

}

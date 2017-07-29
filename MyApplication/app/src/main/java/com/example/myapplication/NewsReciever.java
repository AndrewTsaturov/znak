package com.example.myapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

public class NewsReciever extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("INTENT GETS", "MESSAGE GET");
        int actualNews = intent.getIntExtra(Settings.ACTUAL_NEWS_COUNT_KEY, 0);
        Intent notifificationIntent = new Intent(context, MainActivity.class);

        PendingIntent notificationPending = PendingIntent.getBroadcast(context, 0, notifificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);


        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentIntent(notificationPending);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle(context.getString(R.string.notification_title));
        builder.setContentText(context.getString(R.string.notification_text) + actualNews);

        Notification notification = builder.build();

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(NotificationManagerCompat.IMPORTANCE_DEFAULT, notification);
        Log.d("Напоминание", "Выведено");
    }
}

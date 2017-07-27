package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NewsReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("INTENT GETS", "MESSAGE GET");
        Toast.makeText(context, "НОвости обновленый. Количество новостей ", Toast.LENGTH_LONG).show();
    }
}

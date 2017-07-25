package com.example.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    SwitchCompat autoUpdateCheck, notificationCheck;

    Spinner updateInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        autoUpdateCheck = (SwitchCompat) findViewById(R.id.settings_update_switch);
        notificationCheck = (SwitchCompat) findViewById(R.id.settings_switch_notification);

        updateInterval = (Spinner) findViewById(R.id.settings_spinner_interval);

        String[] spinnerValues = this.getResources().getStringArray(R.array.settings_interval_spinner_items);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                spinnerValues);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        updateInterval.setAdapter(spinnerAdapter);

        loadSettings(autoUpdateCheck, notificationCheck, updateInterval);

        updateInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: updateIntervalSetter(Settings.tenMin);
                        break;
                    case 1: updateIntervalSetter(Settings.fifteenMin);
                        break;
                    case 2: updateIntervalSetter(Settings.thirtiMin);
                        break;
                    case 3: updateIntervalSetter(Settings.oneHour);
                        break;
                    case 4: updateIntervalSetter(Settings.twelfHours);
                        break;
                    case 5: updateIntervalSetter(Settings.oneDay);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        autoUpdateCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(autoUpdateCheck.isChecked()) autoUpdateSwtich(true);

                else autoUpdateSwtich(false);
            }
        });

        notificationCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(notificationCheck.isChecked()) notificationSwitch(true);

                else notificationSwitch(false);
            }
        });


    }

    private void autoUpdateSwtich(boolean value){
        SharedPreferences prefs = this.getSharedPreferences(Settings.SETTINGS, MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(Settings.AUTO_UPDATE_CHECK_KEY, value);
        editor.apply();

        if(value)
        Log.d("AUTO UPDATE", "ENABLED");
        else Log.d("AUTO UPDATE", "Disabled");
    }

    private void notificationSwitch(boolean value){
        SharedPreferences prefs = this.getSharedPreferences(Settings.SETTINGS, MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(Settings.NOTIFOCATION_CHECK_KEY, value);
        editor.apply();

        if(value)
            Log.d("NOTIFICATION", "ENABLED");
        else Log.d("NOTIFICATION", "Disabled");
    }

    private void updateIntervalSetter(long value){
        SharedPreferences prefs = this.getSharedPreferences(Settings.SETTINGS, MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(Settings.AUTO_UPDATE_PERIOD_KEY, value);
        editor.apply();

        Log.d("PERIOD", " " + value);
    }

    private void loadSettings(SwitchCompat update, SwitchCompat note, Spinner interval){
        SharedPreferences prefs = getSharedPreferences(Settings.SETTINGS, MODE_PRIVATE);

        update.setChecked(prefs.getBoolean(Settings.AUTO_UPDATE_CHECK_KEY, false));
        note.setChecked(prefs.getBoolean(Settings.NOTIFOCATION_CHECK_KEY, false));

        int loadInterval = (int) prefs.getLong(Settings.AUTO_UPDATE_PERIOD_KEY, Settings.tenMin);

        switch (loadInterval){
            case (int) Settings.tenMin:
                interval.setSelection(0, false);
                break;
            case (int) Settings.fifteenMin:
                interval.setSelection(1, false);
                break;
            case (int) Settings.thirtiMin:
                interval.setSelection(2, false);
                break;
            case (int) Settings.oneHour:
                interval.setSelection(3, false);
                break;
            case (int) Settings.twelfHours:
                interval.setSelection(4, false);
                break;
            case (int) Settings.oneDay:
                interval.setSelection(5, false);
                break;
        }
    }
}

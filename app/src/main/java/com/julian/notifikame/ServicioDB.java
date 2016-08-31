package com.julian.notifikame;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Julian on 30/08/2016.
 */
public class ServicioDB extends IntentService{

    public ServicioDB() {
        super("Hola Mundo");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String test;

            Toast.makeText(this, "Hola Noti", Toast.LENGTH_SHORT).show();

    }
}

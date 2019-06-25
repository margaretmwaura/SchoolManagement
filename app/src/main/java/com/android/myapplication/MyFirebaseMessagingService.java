package com.android.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    public static String token;
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        token = s;
    }

    public static String returnToken()
    {
        return token;
    }
}

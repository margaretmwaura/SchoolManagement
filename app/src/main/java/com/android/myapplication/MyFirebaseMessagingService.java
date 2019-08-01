package com.android.myapplication;


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

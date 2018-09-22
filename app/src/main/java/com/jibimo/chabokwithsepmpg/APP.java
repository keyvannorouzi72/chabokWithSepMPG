package com.jibimo.chabokwithsepmpg;

import android.app.Application;
import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.adpdigital.push.AdpPushClient;
import com.adpdigital.push.Callback;
import com.adpdigital.push.ChabokNotification;
import com.adpdigital.push.ConnectionStatus;
import com.adpdigital.push.EventMessage;
import com.adpdigital.push.NotificationHandler;

import org.json.JSONException;

public class APP extends Application {
    private AdpPushClient chabok = null;

    @Override
    public void onCreate() {
        super.onCreate();
        initPushClient();

    }

    public synchronized void initPushClient() {
        if (chabok == null) {
            chabok = AdpPushClient.init(
                    getApplicationContext(),
                    MainActivity.class,
                    "appId",
                    "apiKey",
                    "username",
                    "password"
            );

            chabok.setDevelopment(true);
            chabok.register("TestMPGwithChabok");


            chabok.subscribe("default", new Callback() {
                @Override
                public void onSuccess(Object o) {
//                    registerDevice();

                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });
        }

        NotificationHandler myHandler = new NotificationHandler() {

            @Override
            public Class getActivityClass(ChabokNotification chabokNotification) {
                // return preferred activity class to be opened on this message's notification
                return MainActivity.class;
            }

            @Override
            public boolean buildNotification(ChabokNotification chabokNotification, NotificationCompat.Builder builder) {

                // return false to prevent this notification to be shown to the user, otherwise true


                return true;


            }
        };

        chabok.addNotificationHandler(myHandler);
        AdpPushClient.get().

                addListener(APP.this);


        chabok.subscribeEvent("private/default", new

                Callback() {
                    @Override
                    public void onSuccess(Object value) {
                    }

                    @Override
                    public void onFailure(Throwable value) {
                    }
                });
    }

    public void onEvent(final ConnectionStatus status) {

    }


    public void onEvent(final EventMessage message) {
    }


    public synchronized AdpPushClient getPushClient() throws IllegalStateException {
        if (chabok == null) {
            initPushClient();

            throw new IllegalStateException("Adp Push Client not initialized");
        }
        return chabok;
    }
}

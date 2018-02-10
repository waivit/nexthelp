package com.myfcm.NextHealth;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by panishw on 1/10/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // when message recieve from FCM.
        //Log.d("FROM", remoteMessage.getFrom());
        Log.d("FROM","notification here");
        String newToken = "test";


        sendTokenToServer(newToken);

    }

    private void sendTokenToServer(String newToken) {
        Log.d("token",newToken);
        // send new token to your server.
        //TextView token_id = null;
        //token_id = (TextView) findViewById(R.id.token_id);
        //token_id.setText("Test");
    }


}
package com.myfcm.NextHealth;


import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by panishw on 1/10/2018.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // When token expired token will refresh to get new token.
    }
}
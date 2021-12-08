package com.luncher.santanu.ais;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ringlerr.callplus.CallPlus;

import org.json.JSONObject;

import static com.ringlerr.callplus.CallPlus.TAG;

public class FirebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getData());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            JSONObject jsonData = new JSONObject(remoteMessage.getData());
            String[] reverts = {"Can't talk now", "I will call you back"};
            CallPlus.setRevertStrings(reverts);
            CallPlus.hideOnRevert(true);
            CallPlus.setKey("54fdf887b7418a41fa99db39251c1726ff13fda38b2589509e1c6d4f3533emt");

            CallPlus.showDialog(FirebaseService.this, jsonData);

        }
    }


    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }
}

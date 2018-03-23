package mrhot.in.mrhotforbusiness.activities.services;

/**
 * Created by jaideepsingh on 01/07/17.
 */

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import mrhot.in.mrhotforbusiness.activities.NotificationConfig;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    public static String refreshedToken;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(NotificationConfig.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
       new SharedPrefManager(MyFirebaseInstanceIDService.this).setFirebaseToken(token);
    }
}
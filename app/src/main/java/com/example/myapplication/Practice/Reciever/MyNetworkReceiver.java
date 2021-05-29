package com.example.myapplication.Practice.Reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.example.myapplication.Constants.WebConstants;

public class MyNetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (WebConstants.isNetworkAvailable(context)) {
                WebConstants.displayToast(context, null);
            } else {
                WebConstants.displayToast(context, "Connectivity Change, No Internet Connection");
            }
        }

    }
}
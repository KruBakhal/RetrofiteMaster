package com.example.myapplication.Constants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;

/**
 *
 */
public class WebConstants {


    public static final String BASE_URL = "https://gorest.co.in";
    public static Toast toast;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void displayToast(Context context, String msg) {
        if (toast != null) {
            if (TextUtils.isEmpty(msg))
                toast.cancel();
        } else {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            toast.show();
        }
    }

}

package com.xves.testeandroid.viewmodel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

public class MainViewModel {

    public static boolean Connection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network netInfo = cm.getActiveNetwork();
            NetworkCapabilities compat = cm.getNetworkCapabilities(netInfo);
            if (compat != null) {
                if (compat.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true;
                } else if (compat.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            ConnectivityManager cm1 = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm1.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        return false;
    }

}

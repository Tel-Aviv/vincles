package com.tlv.vincles.tlvincles.Client.Environment;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tlv.vincles.tlvincles.R;

//import cat.bcn.vincles.mobile.R;

public class Environment extends Application {


    private static String VERSION_CONTROL_URL;
    private static String API_BASE_URL;
    private static String API_BASIC_AUTH;
    private static String RATE_ME_URL;
    private static String MAPS_KEY;
    private static String ANALYTICS_KEY;
    private static String VC_BASE_URL;


    public Environment(Context c){
        VERSION_CONTROL_URL = ""; // ok c.getResources().getString(R.string.control_version_url);
        API_BASE_URL = ""; // okc.getResources().getString(R.string.api_base_url);
        API_BASIC_AUTH = ""; //ok c.getResources().getString(R.string.api_key);
        RATE_ME_URL = ""; // ok c.getResources().getString(R.string.rate_url);
        MAPS_KEY = ""; // ok c.getResources().getString(R.string.maps_key);
        ANALYTICS_KEY = ""; // ok c.getResources().getString(R.string.analytic_key);
        VC_BASE_URL = ""; // okc.getResources().getString(R.string.vc_base_url);
    }

    public static String getVersionControlUrl() {
        return VERSION_CONTROL_URL;
    }

    public static String getApiBaseUrl() {
        return API_BASE_URL;
    }

    public static String getApiBasicAuth() {
        return API_BASIC_AUTH;
    }

    public static String getRateMeUrl() {
        return RATE_ME_URL;
    }

    public static String getMapsKey() {
        return MAPS_KEY;
    }

    public static String getAnalyticKey() {
        return ANALYTICS_KEY;
    }

    public static String getVcBaseUrl() {
        return VC_BASE_URL;
    }
}


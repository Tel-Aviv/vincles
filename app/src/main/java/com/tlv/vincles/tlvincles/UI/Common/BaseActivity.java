package com.tlv.vincles.tlvincles.UI.Common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.LocalBroadcastManager;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Locale;

import com.tlv.vincles.tlvincles.Client.Business.VersionControlAlert;
import com.tlv.vincles.tlvincles.Client.Db.DatabaseUtils;
import com.tlv.vincles.tlvincles.Client.Environment.Environment;
import com.tlv.vincles.tlvincles.Client.Model.UserRegister;
import com.tlv.vincles.tlvincles.Client.Preferences.UserPreferences;
import com.tlv.vincles.tlvincles.Client.Requests.VinclesHttpClient;
import com.tlv.vincles.tlvincles.R;
import com.tlv.vincles.tlvincles.UI.Alert.AlertMessage;
import com.tlv.vincles.tlvincles.UI.Chats.ChatFragment;
import com.tlv.vincles.tlvincles.UI.ContentDetail.ContentDetailFragment;
import com.tlv.vincles.tlvincles.UI.Login.LoginActivity;
import com.tlv.vincles.tlvincles.UI.Register.RegisterActivity;
import com.tlv.vincles.tlvincles.Utils.Constants;
import com.tlv.vincles.tlvincles.Utils.MyApplication;

import static com.tlv.vincles.tlvincles.Utils.MyApplication.getAppContext;

public abstract class BaseActivity extends AppCompatActivity implements AlertMessage.AlertMessageInterface, MyApplication.AppInterface, VersionControlAlert.ControlVersionInterface {

    public final static String LOGOUT_BROADCAST = "logout_broadcast";
    public static final String FRAGMENT_CHAT = "fragment_chat";
    protected static final String EXIT_EXTRA = "EXIT";
    protected MyApplication mMyApp;
    UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyApp = (MyApplication)this.getApplicationContext();
        userPreferences = new UserPreferences();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMyApp.setCurrentActivity(this);
        mMyApp.initAppInterface(this);
        userPreferences = new UserPreferences();
    }

    @Override
    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        userPreferences.continueToApp(true);
        super.onStop();
    }

    private void clearReferences(){
        Activity currActivity = mMyApp.getCurrentActivity();
        if (this.equals(currActivity))
            mMyApp.setCurrentActivity(null);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(updateBaseContextLocale(base));
    }

    private Context updateBaseContextLocale(Context context) {
        String language = new UserPreferences(context).getUserLanguage();
        Log.d("lng","set base activity, language: "+language);
        Resources res = context.getResources();
        Configuration conf = res.getConfiguration();
        // ok
//        if (language.equals(UserRegister.LANGUAGE_NOT_SET) && getLocale(conf).contains("es")) {
//            language = "es";
//        } else if (language.equals(UserRegister.LANGUAGE_NOT_SET)) {
//            language = "ca";
//        } else {
//            language = language.equals(UserRegister.ESP) ? "es" : "ca";
//        }

        language = "iw";

        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        float fontScaleSystem = Settings.System.getFloat(context.getContentResolver(),
                Settings.System.FONT_SCALE, 1.0f);
        Log.d("fntsz","Font scale system:"+fontScaleSystem +" fontScaleBeforeChange:"+conf.fontScale);
        UserPreferences userPreferences = new UserPreferences();
        conf.fontScale = fontScaleSystem;
        if (userPreferences.getFontSize() == UserPreferences.FONT_SIZE_SMALL) {
            conf.fontScale = conf.fontScale - 0.15f;
        } else if (userPreferences.getFontSize() == UserPreferences.FONT_SIZE_BIG) {
            conf.fontScale = conf.fontScale + 0.15f;
        }
        Log.d("fntsz","Font scale:"+conf.fontScale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResourcesLocale(context, locale);
        }

        return updateResourcesLocaleLegacy(context, locale);
    }

    private String getLocale(Configuration conf) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return conf.getLocales().get(0).toString();
        } else {
            return conf.locale.toString();
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Context updateResourcesLocale(Context context, Locale locale) {

        Configuration configuration = context.getResources().getConfiguration();

        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private Context updateResourcesLocaleLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    public void goToLoginAfterLogout() {

        VinclesHttpClient.cancellAllRequests();
        ChatFragment chatFragment = (ChatFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_CHAT);
        if (chatFragment != null) {
            chatFragment.onLogout();
        }
        Intent intent = new Intent(LOGOUT_BROADCAST);
        LocalBroadcastManager.getInstance(getAppContext()).sendBroadcast(intent);
        DatabaseUtils.dropAllTables();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finishAffinity();
    }

    public void renewTokenFailure(){
        BaseActivity currentActivity = ((MyApplication)getAppContext()).getCurrentActivity();
        if (currentActivity instanceof LoginActivity){
            ((LoginActivity)currentActivity).dismissAlert();
        }
        AlertMessage alertMessage = new AlertMessage(this, getResources().getString(R.string.close_session));
        alertMessage.showMessage(this, getResources().getString(R.string.close_session_token_message), "renewTokenError");
    }

    @Override
    public void onOkAlertMessage(AlertMessage alertMessage, String type) {
        alertMessage.dismissSafely();
        if (type.equals("renewTokenError")) {
            UserPreferences userPreferences = new UserPreferences();
//            Log.d("continuecheck", "Clearing preferences from BaseActivity");
            userPreferences.clear();
            goToLoginAfterLogout();
        }
    }


    @Override
    public void showVersionControl()
    {
        SharedPreferences sp = getSharedPreferences(Constants.READ_PREFS, Activity.MODE_PRIVATE);
        if(sp.getBoolean("SHOWING_CAMERA", false)){
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("SHOWING_CAMERA", false);
            editor.apply();
        }
        else{

            new Environment(getApplicationContext());
            new VersionControlAlert(
                    Environment.getVersionControlUrl(), this, this
            );
        }

    }

    @Override
    public void continueToApp(boolean b) {
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.continueToApp(b);
        //No need to resume RegisterActivity because no background task is performed
        if (b && !(this instanceof RegisterActivity)){
            try{
                this.onRestart();
            }catch (IllegalStateException e){
                Log.e(this.getLocalClassName(), e.toString());
            }
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (currentFragment instanceof ContentDetailFragment) {
            ContentDetailFragment contentDetailFragment = (ContentDetailFragment)currentFragment;
            if (contentDetailFragment.isFullScreen){
                contentDetailFragment.hideAugmentedDetail();
                return;
            }

        }
        super.onBackPressed();
    }
}

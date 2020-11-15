package com.tlv.vincles.tlvincles.UI.Splash;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.tlv.vincles.tlvincles.Client.Business.VersionControlAlert;
import com.tlv.vincles.tlvincles.Client.Db.DatabaseUtils;
import com.tlv.vincles.tlvincles.Client.Environment.Environment;
import com.tlv.vincles.tlvincles.Client.Migration.Fase1SQLiteHelper;
import com.tlv.vincles.tlvincles.Client.Preferences.UserPreferences;
import com.tlv.vincles.tlvincles.R;
import com.tlv.vincles.tlvincles.UI.Alert.AlertMessage;
import com.tlv.vincles.tlvincles.UI.Chats.ChatFragment;
import com.tlv.vincles.tlvincles.UI.FragmentManager.MainFragmentManagerActivity;
import com.tlv.vincles.tlvincles.UI.Login.LoginActivity;
import com.tlv.vincles.tlvincles.UI.TermsAndConditions.TermsAndConditionsActivity;
import com.tlv.vincles.tlvincles.Utils.MyApplication;
import com.tlv.vincles.tlvincles.Utils.OtherUtils;

import java.util.Timer;
import java.util.TimerTask;

import static com.tlv.vincles.tlvincles.Utils.MyApplication.getAppContext;


public class SplashScreenActivity extends AppCompatActivity implements AlertMessage.AlertMessageInterface, MyApplication.AppInterface, VersionControlAlert.ControlVersionInterface, LifecycleObserver {

    private static final long SPLASH_SCREEN_DELAY = 2000;
    public final static String LOGOUT_BROADCAST = "logout_broadcast";
    public static final String FRAGMENT_CHAT = "fragment_chat";

    Context context;

    @Override
    protected void onResume() {
        super.onResume();

        OtherUtils.sendAnalyticsView(this, getResources().getString(R.string.tracking_terms_splash));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }
        context = this;
        setContentView(R.layout.activity_splash_screen);

        ((MyApplication)getAppContext()).initAppInterface(this);

        //showVersionControl();

        startTimer();
    }

    @Override
    public void showVersionControl() {
        new Environment(getApplicationContext());
        new VersionControlAlert(
                Environment.getVersionControlUrl(), this, this
        );
    }

    private void startTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                UserPreferences userPreferences = new UserPreferences(getApplication());
                int userID = userPreferences.getUserID();
                boolean isLogged = userPreferences.getLoginDataDownloaded();

                Intent intent = new Intent().setClass(
                        SplashScreenActivity.this, MainFragmentManagerActivity.class);
                startActivity(intent);

                // ok
                //old preferences for migration
//                SharedPreferences preferences = getSharedPreferences(
//                        "com.tlv.vincles.tlvincles.app-preferences", Context.MODE_PRIVATE);
//                final long fase1UserId = preferences.getLong("com.tlv.vincles.tlvincles.user-id", 0L);
//
//                if (fase1UserId != 0) {
//                    Log.d("migrt","fase 1 user id:"+fase1UserId);
//                    Fase1SQLiteHelper sqLiteHelper = new Fase1SQLiteHelper(context);
//                    try {
//                        String[] userPwd = sqLiteHelper.getUserPassword((int) fase1UserId);
//
//                        Intent intent = new Intent().setClass(
//                                SplashScreenActivity.this, LoginActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("username", userPwd[0]);
//                        bundle.putString("password", userPwd[1]);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                        overridePendingTransition(0, 0);
//
//                    }catch (Exception e){
//                        Log.e("migrt","ERROR");
//                        //Go to Login
//                        startActivityIntent(SplashScreenActivity.this, LoginActivity.class);
//                    }
//
//                } else if (userID != 0 && isLogged) {
//                    Intent intent = new Intent().setClass(
//                            SplashScreenActivity.this, MainFragmentManagerActivity.class);
//                    startActivity(intent);
//                } else if (userID != 0) {
//                    Intent intent = new Intent().setClass(
//                            SplashScreenActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent().setClass(
//                            SplashScreenActivity.this, TermsAndConditionsActivity.class);
//                    startActivity(intent);
//                }
            }};

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    private void startActivityIntent(Context context, Class theClass) {
        Intent intent = new Intent().setClass(
                context, theClass);
        startActivity(intent);
    }

    public void renewTokenFailure(){
        AlertMessage alertMessage = new AlertMessage(this, getResources().getString(R.string.close_session));
        alertMessage.showMessage(this, getResources().getString(R.string.error_default), "renewTokenError");
    }

    @Override
    public void onOkAlertMessage(AlertMessage alertMessage, String type) {
        if (type.equals("renewTokenError")) {
            UserPreferences userPreferences = new UserPreferences();
//            Log.d("continuecheck", "Clearing preferences from SplashScreenActivity");
            userPreferences.clear();
            goToLoginAfterLogout();
        }
    }

    public void goToLoginAfterLogout() {
        finishAffinity();
        ChatFragment chatFragment = (ChatFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_CHAT);
        if (chatFragment != null) {
            chatFragment.onLogout();
        }
        Intent intent = new Intent(LOGOUT_BROADCAST);
        LocalBroadcastManager.getInstance(getAppContext()).sendBroadcast(intent);
        DatabaseUtils.dropAllTables();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void continueToApp(boolean b) {
        if (!b){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            return;
        }
        else{
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }

        startTimer();
    }

    @Override
    protected void onStop() {
        new UserPreferences().continueToApp(true);
        super.onStop();
    }
}

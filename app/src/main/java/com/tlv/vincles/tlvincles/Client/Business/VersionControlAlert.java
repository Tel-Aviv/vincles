package com.tlv.vincles.tlvincles.Client.Business;

import android.content.Context;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tempos21.versioncontrol.service.AlertMessageService;
import com.tempos21.versioncontrol.ui.v4.CustomAlertDialogFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tlv.vincles.tlvincles.Client.Model.UserRegister;
import com.tlv.vincles.tlvincles.Client.Preferences.UserPreferences;
import com.tlv.vincles.tlvincles.UI.Login.LoginActivity;

import java.util.List;

//import cat.bcn.vincles.mobile.Client.Model.UserRegister;
//import cat.bcn.vincles.mobile.Client.Preferences.UserPreferences;
//import cat.bcn.vincles.mobile.UI.Login.LoginActivity;

public class VersionControlAlert {

    private ControlVersionInterface controlVersionInterface;

    public VersionControlAlert(String jsonUrl, final Context context, ControlVersionInterface control) {

        this.controlVersionInterface = control;

        if (jsonUrl==null){
            continueToApp(true);
            return;
        }
        String language = new UserPreferences().getUserLanguage().equals(UserRegister.EN) ? "en" : "iw";

        if (context instanceof AppCompatActivity) {
            dismissPreviousDialog((AppCompatActivity)context);
        }

        AlertMessageService.showMessageDialog(context, jsonUrl, language, new AlertMessageService.AlertDialogListener() {
            @Override
            public void onFailure(Exception e) {
                System.out.print("bp1");
                continueToApp(true);
                LoginActivity.versionControlIsPresent = false;
            }

            @Override
            public void onSuccess(boolean b) {
                System.out.print("bp1");
                continueToApp(!b);
            }

            @Override
            public void onAlertDialogDismissed() {
                System.out.print("bp1");
                continueToApp(true);
                LoginActivity.versionControlIsPresent = false;
            }
        });
    }

    private void dismissPreviousDialog(AppCompatActivity activity) {
        List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof CustomAlertDialogFragment) {
                    CustomAlertDialogFragment dialogFragment = (CustomAlertDialogFragment)fragment;
                    if (dialogFragment != null && dialogFragment.isAdded()){
                        dialogFragment.dismiss();
                    }
                }
            }
        }
    }

    private void continueToApp(boolean b) {
        Log.d("versionControlAlert", "continueToApp VersionControlAlert");
        if (controlVersionInterface==null)
            return;
        controlVersionInterface.continueToApp(b);
    }

    public interface ControlVersionInterface{
        void continueToApp(boolean b);
    }
}

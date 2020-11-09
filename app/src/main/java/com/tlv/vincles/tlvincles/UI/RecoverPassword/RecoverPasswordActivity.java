package com.tlv.vincles.tlvincles.UI.RecoverPassword;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.ActionBar;

import com.tlv.vincles.tlvincles.Client.Business.ValidateFields;
import com.tlv.vincles.tlvincles.Client.Errors.ErrorHandler;
import com.tlv.vincles.tlvincles.Client.Requests.RecoverPasswordRequest;
import com.tlv.vincles.tlvincles.R;
import com.tlv.vincles.tlvincles.UI.Alert.AlertMessage;
import com.tlv.vincles.tlvincles.UI.Common.BaseActivity;
import com.tlv.vincles.tlvincles.Utils.OtherUtils;

import okhttp3.ResponseBody;

public class RecoverPasswordActivity extends BaseActivity implements View.OnClickListener, RecoverPasswordRequest.OnResponse, AlertMessage.AlertMessageInterface {

    EditText emailET;
    AlertMessage alertErrorMessage, alertInfoMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        emailET = findViewById(R.id.email);
        Button recoverPasswordBtn = findViewById(R.id.recover);

        recoverPasswordBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        OtherUtils.sendAnalyticsView(this,
                getResources().getString(R.string.tracking_recover_password));
    }

    @Override
    public void onClick(View view) {
        String email = emailET.getText().toString();
        if (isValidData(email)) {
            RecoverPasswordRequest recoverPasswordRequest = new RecoverPasswordRequest(email);
            recoverPasswordRequest.addOnOnResponse(this);
            recoverPasswordRequest.doRequest();
        }
    }

    public boolean isValidData(String email) {
        boolean isValid = true;
        ValidateFields validateFields = new ValidateFields();
        if (!validateFields.isValididEmail(email)) {
            emailET.setError(getString(R.string.not_valid_email));
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onResponseRecoverPasswordRequest(ResponseBody responseBody) {
        alertInfoMessage = new AlertMessage(this,getResources().getString(R.string.passwordrecovery));
        alertInfoMessage.showMessage(this,getResources().getString(R.string.recover_pass_success), "");
    }

    @Override
    public void onFailureRecoverPasswordRequest(Object error) {
        alertErrorMessage = new AlertMessage(this, AlertMessage.TITTLE_ERROR);
        String errorMsg = ErrorHandler.getErrorByCode(this, error);
        alertErrorMessage.showMessage(this,errorMsg,"");
    }

    @Override
    public void onOkAlertMessage(AlertMessage alertMessage, String type) {
        if (alertMessage.equals(alertErrorMessage)) {
            alertErrorMessage.alert.cancel();
        } else if (alertMessage.equals(alertInfoMessage)) {
            alertInfoMessage.alert.cancel();
            OtherUtils.hideKeyboard(this);
            finish();
        }
    }
}

package com.tlv.vincles.tlvincles.Client.Requests;

import android.util.Log;

import com.tlv.vincles.tlvincles.Client.Errors.ErrorHandler;
import com.tlv.vincles.tlvincles.Client.NetworkUsage.DataUsage;
import com.tlv.vincles.tlvincles.Client.Preferences.UserPreferences;
import com.tlv.vincles.tlvincles.Client.Services.UserService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDataUsageRequest extends BaseRequest implements Callback<ResponseBody> {
    UserService userService;
    List<OnResponse> onResponses = new ArrayList<>();
    DataUsage dataUsage;
    long consumeCreatedTime = 0;


    public PostDataUsageRequest(DataUsage dataUsage, RenewTokenFailed listener) {
        super(listener, BaseRequest.AUTHENTICATED_REQUEST);

        this.dataUsage = dataUsage;
        UserPreferences userPreferences = new UserPreferences();
        this.dataUsage.setUserId(userPreferences.getUserID());
    }

    public void doRequest(String accessToken) {
// ok
//        authenticatedRequest(accessToken);
//        consumeCreatedTime = dataUsage.getDate();
//        userService = retrofit.create(UserService.class);
//        Call<ResponseBody> call = userService.postDataUsage(dataUsage);
//
//        try{
//            ((String[])call.request().tag())[0] = this.getClass().getSimpleName();
//        }catch (Exception e){
//            Log.e("TAG", this.getClass().getSimpleName() + " Put request Tag error");
//        }
//
//        call.enqueue(this);
    }

    public void addOnOnResponse(OnResponse onResponse) {
        onResponses.add(onResponse);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        for (OnResponse r : onResponses) {
            if (response.isSuccessful()) {
                r.onResponsePostDataUsageRequest(response.body(), consumeCreatedTime);
            } else {
                String errorCode = ErrorHandler.parseError(response).getCode();
                r.onFailurePostDataUsageRequest(errorCode);
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        for (OnResponse r : onResponses) {
            r.onFailurePostDataUsageRequest(new Exception(t));
        }
    }





    public interface OnResponse {
        void onResponsePostDataUsageRequest(ResponseBody responseBody, long consumeCreatedTime);
        void onFailurePostDataUsageRequest(Object error);
    }
}

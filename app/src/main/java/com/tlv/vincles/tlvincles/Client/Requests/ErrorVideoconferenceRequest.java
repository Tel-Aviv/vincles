package com.tlv.vincles.tlvincles.Client.Requests;

import android.util.Log;

import com.google.gson.JsonObject;
import com.tlv.vincles.tlvincles.Client.Errors.ErrorHandler;
import com.tlv.vincles.tlvincles.Client.Services.CallService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ErrorVideoconferenceRequest extends BaseRequest implements Callback<ResponseBody> {

    CallService callService;
    List<OnResponse> onResponses = new ArrayList<>();
    int idUser;
    String idRoom;
    JsonObject jBody;

    public ErrorVideoconferenceRequest(RenewTokenFailed listener, int idUser, String idRoom) {
        super(listener, BaseRequest.AUTHENTICATED_REQUEST);
        this.idUser = idUser;
        this.idRoom = idRoom;
        jBody = new JsonObject();
        jBody.addProperty("idUser", idUser);
        jBody.addProperty("idRoom", idRoom);
        Log.d("callvid", "ErrorVideoconferenceRequest idRoom:"+idRoom);
    }

    @Override
    public void doRequest(String accessToken) {
        authenticatedRequest(accessToken);
        callService = retrofit.create(CallService.class);
        Call<ResponseBody> call = callService.errorVideoconference(jBody);

        try{
            ((String[])call.request().tag())[0] = this.getClass().getSimpleName();
        }catch (Exception e){
            Log.e("TAG", this.getClass().getSimpleName() + " Put request Tag error");
        }

        call.enqueue(this);
    }

    public void addOnOnResponse(OnResponse onResponse) {
        onResponses.add(onResponse);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (!shouldRenewToken(this, response)) {
            for (OnResponse r : onResponses) {
                if (response.isSuccessful()) {
                    r.onResponseErrorVideoconference(idUser, idRoom);
                } else {
                    String errorCode = ErrorHandler.parseError(response).getCode();
                    r.onFailureErrorVideoconference(errorCode);
                }
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        for (OnResponse r : onResponses) {
            r.onFailureErrorVideoconference(new Exception(t));
        }
    }

    public interface OnResponse {
        void onResponseErrorVideoconference(int idUser, String idRoom);
        void onFailureErrorVideoconference(Object error);
    }
}

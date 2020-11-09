package com.tlv.vincles.tlvincles.Client.Requests;

import android.util.Log;

import com.google.gson.JsonObject;
import com.tlv.vincles.tlvincles.Client.Errors.ErrorHandler;
import com.tlv.vincles.tlvincles.Client.Services.GalleryService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContentInTheGallery extends BaseRequest implements Callback<JsonObject> {

    GalleryService galleryService;
    List<OnResponse> onResponses = new ArrayList<>();
    int idContent = 0;
    private JsonObject jBody;

    public AddContentInTheGallery(RenewTokenFailed listener, int idContent) {
//        super(BaseRequest.AUTHENTICATED_REQUEST,accessToken);
        super(listener, BaseRequest.AUTHENTICATED_REQUEST);
        this.idContent = idContent;
//        galleryService = retrofit.create(GalleryService.class);
    }

    @Override
    public void doRequest(String accessToken) {
        authenticatedRequest(accessToken);
        galleryService = retrofit.create(GalleryService.class);
        jBody = new JsonObject();
        jBody.addProperty("idContent", idContent);
        //jBody.addProperty("tag", GalleryContent.TAG_IMAGES);
        Call<JsonObject> call = galleryService.addContentInTheGallery(jBody);

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
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        if (!shouldRenewToken(this, response)) {
            for (OnResponse r : onResponses) {
                if (response.isSuccessful()) {
                    int id = response.body().get("id").getAsInt();
                    r.onResponseAddContentInTheGallery(id);
                } else {
                    String errorCode = ErrorHandler.parseError(response).getCode();
                    Log.d("rntk","onResponse error code:"+errorCode);
                    r.onFailureAddContentInTheGallery(errorCode);
                }
            }
        }
    }

    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        Log.d("rntk","on failure throwable:"+t.toString());
        for (OnResponse r : onResponses) {
            r.onFailureAddContentInTheGallery(new Exception(t));
        }
    }



    public interface OnResponse {
        void onResponseAddContentInTheGallery(int id);
        void onFailureAddContentInTheGallery(Object error);
    }
}

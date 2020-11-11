package com.tlv.vincles.tlvincles.Client.Services;

import com.google.gson.JsonObject;
import com.tlv.vincles.tlvincles.Client.Model.ChangePasswordResponseModel;
import com.tlv.vincles.tlvincles.Client.Model.GetUser;
import com.tlv.vincles.tlvincles.Client.Model.NotificationRest;
import com.tlv.vincles.tlvincles.Client.Model.Serializers.AddUser;
import com.tlv.vincles.tlvincles.Client.Model.TokenFromLogin;
import com.tlv.vincles.tlvincles.Client.Model.UserRegister;
import com.tlv.vincles.tlvincles.Client.NetworkUsage.DataUsage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


public interface UserService {
    @Headers("Content-Type: application/json")
    @POST("/t/vincles-bcn.cat/vincles-services/1.0/public/users/vinculat/register/")
    public Call<UserRegister> register(@Body JsonObject userRegister);

    @FormUrlEncoded
    @POST("/token")
    public Call<TokenFromLogin> login(@Field("grant_type") String grantType, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/token")
    public Call<TokenFromLogin> renewToken(@Field("grant_type") String grantType, @Field("refresh_token") String refreshToken);

    @FormUrlEncoded
    @POST("/revoke?token_type_hint=access_token")
    public Call<Void> logout(@Field("token") String token);

    @POST("/t/vincles-bcn.cat/vincles-services/1.0/public/users/password/recover")
    public Call<ResponseBody> recoverPassword(@Body JsonObject recoverUser);

    @POST("/t/vincles-bcn.cat/vincles-services/1.0/users/me/password")
    public Call<ChangePasswordResponseModel> changePassword(@Body JsonObject passwords);

    @POST("/t/vincles-bcn.cat/vincles-services/1.0/public/users/vinculat/validate")
    public Call<JsonObject> validateUser(@Body JsonObject validateBody);

    @GET("/t/vincles-bcn.cat/vincles-services/1.0/users/me")
    public Call<GetUser> getMyUserInfo();

    @GET("/t/vincles-bcn.cat/vincles-services/1.0/users/{idUser}/full")
    public Call<GetUser> getUserInfo(@Path("idUser") String idUser);

    @GET("/t/vincles-bcn.cat/vincles-services/1.0/users/{idUser}/public/info")
    public Call<GetUser> getPublicUserInfo(@Path("idUser") String idUser);

    @PUT("/t/vincles-bcn.cat/vincles-services/1.0/users/me")
    public Call<JSONObject> updateUser(@Body JsonObject userRegister);

    @GET("/t/vincles-bcn.cat/vincles-services/1.0/users/{userID}/photo")
    public Call<ResponseBody> getPhoto(@Path("userID") String userID);

    @POST("/t/vincles-bcn.cat/vincles-services/1.0/circles/users")
    public Call<AddUser> associateRegistered(@Body JsonObject association);

    // ok
    //@POST("/t/vincles-bcn.cat/vincles-services/1.0/circles/mine/codes")
    @POST("/me/circles/codes")
    public Call<JsonObject> generateCode(@Body JsonObject empty);

    // ok
    // @GET("/t/vincles-bcn.cat/vincles-services/1.0/device/notifications")
    @GET("/me/notifications")
    public Call<ArrayList<NotificationRest>> getNotifications(@QueryMap Map<String, String> params);

    @GET("/t/vincles-bcn.cat/vincles-services/1.0/device/notifications/{notificationID}")
    public Call<NotificationRest> getNotificationsByID(@Path("notificationID") int notificationID,
                                                       @QueryMap Map<String, String> params);

    @PUT("/t/vincles-bcn.cat/vincles-services/1.0/migration/status")
    public Call<ResponseBody> migrationPostLogin();

    @POST("/t/vincles-bcn.cat/vincles-services/1.0/consume")
    public Call<ResponseBody> postDataUsage(@Body DataUsage dataUsage);

}

package com.tlv.vincles.tlvincles.UI.Calendar;


import com.tlv.vincles.tlvincles.Client.Db.MeetingsDb;
import com.tlv.vincles.tlvincles.Client.Db.UsersDb;
import com.tlv.vincles.tlvincles.Client.Model.MeetingRealm;
import com.tlv.vincles.tlvincles.Client.Preferences.UserPreferences;
import com.tlv.vincles.tlvincles.Client.Requests.AcceptDeclineMeetingRequest;
import com.tlv.vincles.tlvincles.Client.Requests.BaseRequest;
import com.tlv.vincles.tlvincles.Client.Requests.DeleteMeetingRequest;
import com.tlv.vincles.tlvincles.Utils.MyApplication;

import io.realm.Realm;
import io.realm.RealmResults;

public class CalendarRepository implements DeleteMeetingRequest.OnResponse, AcceptDeclineMeetingRequest.OnResponse {

    BaseRequest.RenewTokenFailed renewTokenFailed;
    UsersDb usersDb;
    MeetingsDb meetingsDb;
    UserPreferences userPreferences;
    Callback callbackListener;
    Realm realm;


    public CalendarRepository(BaseRequest.RenewTokenFailed renewTokenFailed, Realm realm, Callback callback) {
        this.renewTokenFailed = renewTokenFailed;
        meetingsDb = new MeetingsDb(MyApplication.getAppContext());
        usersDb = new UsersDb(MyApplication.getAppContext());
        userPreferences = new UserPreferences(MyApplication.getAppContext());
        this.callbackListener = callback;
        this.realm = realm;

    }

    public RealmResults<MeetingRealm> getAllMeetings() {
        return meetingsDb.findAllShownMeetingsAsync(realm);
    }

    void setMeetingNotShown(int meetingId) {
        meetingsDb.setShouldShowMeeting(meetingId, false,
                userPreferences.getIsSyncCalendars() ? userPreferences.getCalendarId() : -1);
    }

    void onMeetingAccepted(int meetingId) {
        meetingsDb.setMeetingToAccepted(meetingId, userPreferences.getUserID());
    }

    void deleteMeeting(int meetingId) {
        DeleteMeetingRequest deleteMeetingRequest = new DeleteMeetingRequest(renewTokenFailed,
                meetingId);
        deleteMeetingRequest.addOnOnResponse(this);
        deleteMeetingRequest.doRequest(userPreferences.getAccessToken());
    }

    void acceptRejectMeeting(int meetingId, boolean assist) {
        AcceptDeclineMeetingRequest acceptDeclineMeetingRequest = new AcceptDeclineMeetingRequest(renewTokenFailed,
                meetingId, assist);
        acceptDeclineMeetingRequest.addOnOnResponse(this);
        acceptDeclineMeetingRequest.doRequest(userPreferences.getAccessToken());
    }

    @Override
    public void onResponseDeleteMeeting(int idMeeting) {
        if (callbackListener != null) callbackListener.onResponseDeleteMeeting(idMeeting);
    }

    @Override
    public void onFailureDeleteMeeting(Object error) {
        if (callbackListener != null) callbackListener.onFailureRequest(error);
    }

    @Override
    public void onResponseAcceptDeclineMeeting(int idMeeting, boolean attendance) {
        if (callbackListener != null) callbackListener.onResponseAcceptDeclineMeeting(idMeeting,
                attendance);
    }

    @Override
    public void onFailureAcceptDeclineMeeting(Object error) {
        if (callbackListener != null) callbackListener.onFailureRequest(error);
    }

    public interface Callback {
        void onFailureRequest(Object error);
        void onResponseDeleteMeeting(int idMeeting);
        void onResponseAcceptDeclineMeeting(int idMeeting, boolean attendance);
    }

}

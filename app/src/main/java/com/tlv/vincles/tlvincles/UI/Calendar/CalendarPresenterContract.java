package com.tlv.vincles.tlvincles.UI.Calendar;


import android.os.Bundle;
import android.util.SparseArray;

import com.tlv.vincles.tlvincles.Client.Model.MeetingRealm;
import com.tlv.vincles.tlvincles.UI.Compound.CalendarCompoundView;

import java.util.ArrayList;

import io.realm.RealmResults;

public interface CalendarPresenterContract extends CalendarCompoundView.OnCalendarEventListener {

    void loadData();

    void onCreateView();
    void onSaveInstanceState(Bundle outState);

    void seeToday();
    void seeTomorrow();
    void seeMonth();

    long getShownDay();

    void stopedShowingErrorDialog();

    ArrayList<MeetingRealm> getCurrentMeetings();
    RealmResults<MeetingRealm> getAllMeetings();
    //SparseArray<GetUser> getUsersInMeetings();

    void onItemButtonClicked(int whatButton, int meetingId);

    void onConfirmationDialogAccepted();
    void onConfirmationDialogCanceled();

}

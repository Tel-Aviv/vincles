package com.tlv.vincles.tlvincles.UI.Calendar;


import com.tlv.vincles.tlvincles.Client.Model.MeetingRealm;
import com.tlv.vincles.tlvincles.UI.Contracts.Contact;

import java.util.List;


public interface CalendarNewDateFragmentView {

    void showMeeting(MeetingRealm meeting, List<Contact> contacts);
    void showContacts(List<Contact> contacts);

    void goBack();

    void showWaitDialog();
    void hideWaitDialog();
    void showError(Object error);
    void showEmptyTitleError();

    void notifyContactChange();

    void onMeetingCreatedOrUpdated();

}

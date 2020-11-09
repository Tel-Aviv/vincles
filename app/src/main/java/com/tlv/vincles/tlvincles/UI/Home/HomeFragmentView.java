package com.tlv.vincles.tlvincles.UI.Home;

import com.tlv.vincles.tlvincles.UI.Contracts.Contact;

import java.util.List;



public interface HomeFragmentView {

    void onContactsReady(List<Contact> contacts);
    void onUserPictureLoaded(int id, String path);
    void showNoContactsError();
    void hideNoContactsError();
    void setCalendarNumber(int number);
    void setNotificationsNumber(int number);

}

package com.tlv.vincles.tlvincles.UI.Notifications;


import android.content.res.Resources;


public interface NotificationsFragmentView {

    void updateList();

    void openGroupChat(int groupId);
    void openUserChat(int userId);
    void openContacts();
    void openContactsGroups();
    void openMeeting(int meetingId);
    void openCalendarDay(long date);
    void openAddToCircles(String code);
    Resources getResources();

}

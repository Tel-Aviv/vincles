package com.tlv.vincles.tlvincles.UI.Calendar;


import com.tlv.vincles.tlvincles.Client.Model.MeetingRealm;
import com.tlv.vincles.tlvincles.UI.Contracts.Contact;

import java.util.List;


public interface CalendarDateDetailFragmentView {

    void showMeeting(MeetingRealm meeting, List<Contact> contacts, String hostName,
                     String hostPath);

    void updateHostImage(String path);

    void showError(Object error);

    void notifyContactChange();

}

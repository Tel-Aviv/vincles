package com.tlv.vincles.tlvincles.UI.Calendar;


import android.os.Bundle;

import java.util.ArrayList;

public interface CalendarNewDatePresenterContract {


    void onCreateView();
    void onSaveInstanceState(Bundle outState);

    void loadMeeting(int meetingId);

    void onSaveClicked(String description, long startDate, int lengthDate);

    void stopedShowingErrorDialog();

    void addContacts(ArrayList<Integer> contactIds);

    ArrayList<Integer> getContactIds();
    void removeContact(int id);

    void loadContactPicture(int contactId);


}

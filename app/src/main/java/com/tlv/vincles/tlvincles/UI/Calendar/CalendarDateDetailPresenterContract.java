package com.tlv.vincles.tlvincles.UI.Calendar;


import android.os.Bundle;

import java.util.ArrayList;

public interface CalendarDateDetailPresenterContract {


    void onCreateView();
    void onSaveInstanceState(Bundle outState);

    void loadMeeting(int meetingId);

    void stopedShowingErrorDialog();


    void loadContactPicture(int contactId);
    void loadCreatorPicture();

}

package com.tlv.vincles.tlvincles.UI.Home;

import android.os.Bundle;

public interface HomePresenterContract {

    void getContacts(boolean needCallRest);
    void getContactPicture(int id, int type);
    void onCreateView();
    void updateNotificationsNumber();
}

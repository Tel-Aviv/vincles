package com.tlv.vincles.tlvincles.UI.Notifications;


import android.os.Bundle;

import com.tlv.vincles.tlvincles.Client.Model.NotificationAdapterModel;

import java.util.ArrayList;

public interface NotificationsPresenterContract {

    ArrayList<NotificationAdapterModel> getNotificationsList();
    void deleteNotification(int notificationId);
    void notificationActionClicked(NotificationAdapterModel notificationRest);
    void onSaveInstanceState(Bundle outState);

}

package com.tlv.vincles.tlvincles.UI.Notifications;

import com.tlv.vincles.tlvincles.Client.Db.NotificationsDb;
import com.tlv.vincles.tlvincles.Client.Model.NotificationRest;
import com.tlv.vincles.tlvincles.Client.Requests.BaseRequest;
import com.tlv.vincles.tlvincles.Utils.MyApplication;

import io.realm.Realm;
import io.realm.RealmResults;

public class NotificationsRepository {

    protected Callback listener;
    BaseRequest.RenewTokenFailed renewTokenListener;
    NotificationsDb notificationsDb;
    Realm realm;

    public NotificationsRepository(Callback listener, BaseRequest.RenewTokenFailed renewTokenListener, Realm realm) {
        this.listener = listener;
        this.renewTokenListener = renewTokenListener;
        notificationsDb = new NotificationsDb(MyApplication.getAppContext());
        this.realm = realm;
    }

    RealmResults<NotificationRest> getNotificationsList() {
        return notificationsDb.findShownNotificationsAsync(realm);
    }

    void deleteNotification(int notificationId) {
        notificationsDb.setNotificationShouldBeShown(notificationId, false);
    }


    public interface Callback {

    }
}

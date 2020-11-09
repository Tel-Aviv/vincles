package com.tlv.vincles.tlvincles.UI.Chats;


import com.tlv.vincles.tlvincles.UI.Contracts.Contact;

import java.util.ArrayList;
import java.util.List;



public interface GroupDetailFragmentView {

    void showError(Object error);
    void showInvitationSent();
    void showSendingData();
    void hideSendingData();

    void notifyContactChange();

    void updateAvatar(String path);
    void updateGroupName(String name);
    void updateDescription(String description);
    void setContacts(ArrayList<Contact> contacts);


}

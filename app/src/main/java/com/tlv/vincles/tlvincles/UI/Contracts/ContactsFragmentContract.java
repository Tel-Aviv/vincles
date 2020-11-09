package com.tlv.vincles.tlvincles.UI.Contracts;


import java.util.List;

public interface ContactsFragmentContract {

    void loadContacts(List<Contact> contactList);

    void reloadContactAdapter();

    void showNoContactsError();

    void hideNoContactsError();

    void showContactDeleteAlert(boolean ok);

    void showSharedMediaAlert(boolean ok);

    void showWaitDialog();

    void hideWaitDialog();
}

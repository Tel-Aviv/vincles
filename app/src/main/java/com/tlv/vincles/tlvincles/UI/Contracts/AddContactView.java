package com.tlv.vincles.tlvincles.UI.Contracts;

public interface AddContactView {

    void showCode(String code);
    void showWaitDialog();
    void hideWaitDialog();
    void showErrorMessage(Object error);
    void codeShouldNotBeEmpty();
    void showContactAdded(Contact contact);

}

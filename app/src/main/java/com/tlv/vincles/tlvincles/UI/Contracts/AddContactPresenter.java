package com.tlv.vincles.tlvincles.UI.Contracts;

import com.tlv.vincles.tlvincles.Client.Preferences.UserPreferences;
import com.tlv.vincles.tlvincles.Client.Requests.BaseRequest;
import com.tlv.vincles.tlvincles.UI.FragmentManager.ContactsRepository;

import java.util.List;


public class AddContactPresenter implements AddContactPresenterContract, ContactsRepository.AddContactCallback {

    private AddContactView addContactView;
    private ContactsRepository contactsRepository;

    AddContactPresenter(BaseRequest.RenewTokenFailed listener, AddContactView addContactView, UserPreferences userPreferences) {
        this.addContactView = addContactView;
        this.contactsRepository = new ContactsRepository(this, listener);
    }

    //SEE CODE
    @Override
    public void onSeeCodeClicked() {
        contactsRepository.requestCode();
        addContactView.showWaitDialog();
    }

    @Override
    public void onAddContactClicked(String code, String relationship) {
        if (code == null || !isValid(code)) {
            addContactView.codeShouldNotBeEmpty();
        } else {
            contactsRepository.addContact(code, relationship);
            addContactView.showWaitDialog();
        }
    }

    @Override
    public void onResponseGenerateUserCode(String code) {
        addContactView.showCode(code);
        addContactView.hideWaitDialog();
    }

    @Override
    public void onResponseGenerateUserCodeError(Object error) {
        addContactView.hideWaitDialog();
        addContactView.showErrorMessage(error);
    }

    @Override
    public void onResponseAddUser(Contact contact) {
        addContactView.hideWaitDialog();
        addContactView.showContactAdded(contact);
    }

    @Override
    public void onResponseAddUserError(Object error) {
        addContactView.hideWaitDialog();
        addContactView.showErrorMessage(error);
    }

    private boolean isValid(String code) {
        return  code.length() > 0;
    }

    @Override
    public void onCirclesLoaded(List<Contact> contactList) {

    }

    @Override
    public void onRemoveContact(boolean ok) {
        //Not used
    }

    @Override
    public void setEmptyText(int filterKind) {

    }
}

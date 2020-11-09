package com.tlv.vincles.tlvincles.Client.Business;

public class UserBusiness {
    public boolean isUserAuthenticatedUserVincles (int idLibrary, int idCercle) {
        return !(idLibrary == -1 && idCercle == -1);
    }
}

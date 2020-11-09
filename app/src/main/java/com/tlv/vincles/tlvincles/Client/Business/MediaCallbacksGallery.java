package com.tlv.vincles.tlvincles.Client.Business;

import java.util.HashMap;

public interface MediaCallbacksGallery
{
    void onSuccess(HashMap<Integer, Object> response);
    void onFailure(HashMap<Integer, Object> responsee);
}

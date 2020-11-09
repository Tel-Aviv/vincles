package com.tlv.vincles.tlvincles.UI.ContentDetail;


import android.util.Log;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tlv.vincles.tlvincles.Client.Model.Db.Model.GalleryContentRealm;

import io.realm.RealmResults;

public class ContentDetailAugmentedPagerAdapter extends FragmentStatePagerAdapter {

    private RealmResults<GalleryContentRealm> galleryContentsRealm;
    ContentDetailAugmentedPagerFragment contentDetailPagerFragment;

    public ContentDetailAugmentedPagerAdapter(FragmentManager fragmentManager,
                                              RealmResults<GalleryContentRealm> galleryContentsRealm) {
        super(fragmentManager);
        this.galleryContentsRealm = galleryContentsRealm;
    }


    @Override
    public int getCount() {
        return galleryContentsRealm.size();
    }


    @Override
    public Fragment getItem(int position) {
        GalleryContentRealm galleryContent = galleryContentsRealm.get(position);
        contentDetailPagerFragment = ContentDetailAugmentedPagerFragment.newInstance(galleryContent.getPath(),
                galleryContent.getMimeType(), position, galleryContent.getIdContent());
        return contentDetailPagerFragment;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;

    }


}

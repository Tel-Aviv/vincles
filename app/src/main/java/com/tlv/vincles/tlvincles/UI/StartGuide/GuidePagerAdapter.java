package com.tlv.vincles.tlvincles.UI.StartGuide;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.tlv.vincles.tlvincles.R;
import com.tlv.vincles.tlvincles.Utils.ImageUtils;

import java.io.File;
import java.util.List;


public class GuidePagerAdapter extends PagerAdapter {

    int[] imageIds = new int[] {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3,
            R.drawable.guide_4, R.drawable.guide_5};
    Context context;

    public GuidePagerAdapter(Context context) {
        super();
        this.context = context;
    }


    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.guide_element_image_adapter, collection, false);
        collection.addView(layout);

        ImageView imageView = layout.findViewById(R.id.imageview);
        /*
        Glide.with(context)
                .load(imageIds[position])
                .into(imageView);
                */
        ImageUtils.setImageToImageView(imageIds[position], imageView, context, false);

        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

}

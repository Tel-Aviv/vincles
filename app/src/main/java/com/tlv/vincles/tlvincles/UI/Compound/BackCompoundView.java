package com.tlv.vincles.tlvincles.UI.Compound;

import android.content.Context;
//import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.tlv.vincles.tlvincles.R;

//import cat.bcn.vincles.mobile.R;

public class BackCompoundView extends LinearLayout {
    public BackCompoundView(Context context) {
        super(context);
        initializeViews(context);
    }

    public BackCompoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public BackCompoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.back_button_layout, this);
    }

}

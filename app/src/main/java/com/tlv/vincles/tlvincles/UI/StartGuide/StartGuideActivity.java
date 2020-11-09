package com.tlv.vincles.tlvincles.UI.StartGuide;

import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tlv.vincles.tlvincles.R;
import com.tlv.vincles.tlvincles.UI.Common.BaseActivity;
import com.tlv.vincles.tlvincles.Utils.OtherUtils;


public class StartGuideActivity extends BaseActivity implements View.OnClickListener {

    ViewPager pager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_guide);

        pager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);
        pager.setAdapter(new GuidePagerAdapter(this));
        tabLayout.setupWithViewPager(pager, true);

        findViewById(R.id.close_button).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        OtherUtils.sendAnalyticsView(this,
                getResources().getString(R.string.tracking_initial_guide));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.close_button) {
            finish();
        }
    }
}

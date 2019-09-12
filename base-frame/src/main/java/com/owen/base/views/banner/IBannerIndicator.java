package com.owen.base.views.banner;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZhouSuQiang on 2017/12/7.
 * banner指示器 基本定义
 */

public interface IBannerIndicator extends ViewPager.OnPageChangeListener {
    View onCreateIndicatorView(LayoutInflater inflater, ViewGroup parent);

    void onNotifyDataChanged(int count);
}

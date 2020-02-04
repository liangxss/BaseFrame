package com.owen.base.frame;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * @author ZhouSuQiang
 * @date 2018/10/9
 */
public interface MvpBaseView {
    void onPresenterEvent(int code, @Nullable Bundle bundle);

    boolean post(Runnable action);

    boolean postDelayed(Runnable action, long delayMillis);
}

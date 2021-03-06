package com.owen.base.frame;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author ZhouSuQiang
 * @date 2018/10/9
 */
public abstract class MvpBasePresenter<V extends MvpBaseView> implements LifecycleObserver {
    public static final int CODE_LOADING = 1;
    public static final int CODE_LOAD_SUCCESS = 2;
    public static final int CODE_LOAD_FAILURE = 3;
    
    protected V mView;

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public abstract void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        mView = null;
    }
    
    protected void onPresenterView(V v) {
        mView = v;
    }

    protected void sendPresenterEvent(int code) {
        sendPresenterEvent(code, null);
    }

    protected void sendPresenterEvent(int code, @Nullable Bundle bundle) {
        if (null != mView) {
            mView.onPresenterEvent(code, bundle);
        }
    }

    protected boolean post(Runnable action) {
        if (null != mView) {
            return mView.post(action);
        }
        return false;
    }

    protected boolean postDelayed(Runnable action, long delayMillis) {
        if (null != mView) {
            return mView.postDelayed(action, delayMillis);
        }
        return false;
    }
}

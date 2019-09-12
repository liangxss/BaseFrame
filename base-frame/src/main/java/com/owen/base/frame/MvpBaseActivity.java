package com.owen.base.frame;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.lang.reflect.Type;

/**
 *
 * @author ZhouSuQiang
 * @date 2018/10/9
 */
public abstract class MvpBaseActivity<P extends MvpBasePresenter<V>, V extends MvpBaseView> extends AppCompatActivity implements MvpBaseView {

    private static final String TAG = MvpBaseActivity.class.getSimpleName();
    protected P mPresenter;
    protected ViewGroup mRootContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = onCreatePresenter();
        if (null != mPresenter) {
            mPresenter.onPresenterView(getPresenterView());
        }

        onSetContentViewBefore();
        setContentView(getLayoutId());
        onSetContentViewAfter();
        mRootContentView = findViewById(android.R.id.content);

        initBundleExtra(savedInstanceState);
        initViews();
        initListeners();

        if (null != mPresenter) {
            getLifecycle().addObserver(mPresenter);
        }
    }

    protected void onSetContentViewBefore() { }
    protected void onSetContentViewAfter() { }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            getLifecycle().removeObserver(mPresenter);
            mPresenter = null;
        }
    }

    public boolean post(Runnable action) {
        if (null != mRootContentView)
            return mRootContentView.post(action);
        return false;
    }

    public boolean postDelayed(Runnable action, long delayMillis) {
        if (null != mRootContentView)
            return mRootContentView.postDelayed(action, delayMillis);
        return false;
    }

    public boolean removeCallbacks(Runnable action) {
        if (null != mRootContentView)
            return mRootContentView.removeCallbacks(action);
        return false;
    }

    @NonNull
    protected V getPresenterView() {
        Type type = FrameUtils.getRealType(this, 1);
        if (null != type) {
            Class<V> clazz = (Class<V>) type;
            try {
                if (!(TextUtils.equals(clazz.getName(), this.getClass().getName()))) {
                    return clazz.newInstance();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        return (V) this;
    }

    @Override
    public void onPresenterEvent(int code, @Nullable Bundle bundle) {

    }

    protected P onCreatePresenter() {
        Type type = FrameUtils.getRealType(this, 0);
        if (null != type) {
            Class<P> clazz = (Class<P>) type;
            try {
                return clazz.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化UI view
     */
    protected abstract void initViews();

    /**
     * 1.获取get Intent数据 2.状态保存数据读取
     */
    protected void initBundleExtra(Bundle savedInstanceState) {
    }

    /**
     * 初始化view监听
     */
    protected void initListeners() {}
}

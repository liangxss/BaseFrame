package com.owen.base.frame;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Type;

/**
 *
 * @author ZhouSuQiang
 * @date 2018/10/9
 */
public abstract class MvpBaseFragment<P extends MvpBaseFragmentPresenter<V>, V extends MvpBaseView> extends Fragment implements MvpBaseView{

    protected P mPresenter;

    private boolean mInitialized;
    protected View mRootView;
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    
        mPresenter = onCreatePresenter();
        if (null != mPresenter) {
            mPresenter.onPresenterView(getPresenterView());
            mPresenter.onAttach(context);
            getLifecycle().addObserver(mPresenter);
        }
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mRootView) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        if (null != mPresenter) {
            mPresenter.onCreateView();
        }

        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (!mInitialized) {
            initBundleExtra(savedInstanceState);
            initViews(view);
            initListeners();
            if (getUserVisibleHint()) {
                onLazyLoad();
            }
            mInitialized = true;
        }
        if (null != mPresenter) {
            mPresenter.onViewCreated();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null != mPresenter) {
            mPresenter.onActivityCreated();
        }
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mPresenter) {
            mPresenter.onDestroyView();
        }
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        if (null != mPresenter) {
            mPresenter.onDetach();
            getLifecycle().removeObserver(mPresenter);
            mPresenter = null;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mInitialized) {
            if (getUserVisibleHint()) {
                onLazyLoad();
            }
        }
    }

    public boolean post(Runnable action) {
        if (null != getView())
            return getView().post(action);
        return false;
    }

    public boolean postDelayed(Runnable action, long delayMillis) {
        if (null != getView())
            return getView().postDelayed(action, delayMillis);
        return false;
    }

    public boolean removeCallbacks(Runnable action) {
        if (null != getView())
            return getView().removeCallbacks(action);
        return false;
    }

    /**
     * Override in case of fragment not implementing MvpBasePresenter<View> interface
     */
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
            } catch (java.lang.InstantiationException e) {
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
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 懒加载回调
     */
    protected void onLazyLoad() {
        if (null != mPresenter) {
            mPresenter.onLazyLoad();
        }
    }

    /**
     * 1.获取get Intent数据 2.状态保存数据读取
     */
    protected void initBundleExtra(Bundle savedInstanceState) {
    }

    /**
     * 初始化UI view
     */
    protected abstract void initViews(@NonNull View view);

    /**
     * 初始化view监听
     */
    protected void initListeners() {}
}

package com.zqf.zhangqflibrary.base.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * -----------------------------
 * Created by zqf on 2018/1/22.
 * ---------------------------
 */

public abstract class BaseFragment<B extends ViewDataBinding> extends SupportFragment {
    protected String TAG = getClass().getSimpleName();
    public LayoutInflater inflater;
    protected B mViewBinding;
    public View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        if (rootView == null) {
            mViewBinding = DataBindingUtil.inflate(inflater, this.getLayoutId(), container, false);
            rootView = mViewBinding.getRoot();
            initView(rootView);
            initData();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this) && useEventBus()){//加上判断
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        if(!EventBus.getDefault().isRegistered(this) && useEventBus()){
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    /**
     * 获取资源文件布局
     *
     * @return 资源布局文件layout
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     *
     * @param rootView 资源布局view
     */
    public abstract void initView(View rootView);

    /**
     * 初始化对象
     */
    protected abstract void initData();


    /**
     * 初始化按钮监听
     */
    protected abstract void initListener();

    /**
     * 是否使用 {@link EventBus},默认为使用(false)，
     *
     * @return
     */
    public boolean useEventBus() {
        return false;
    }


    public void startActivity(Class clazz) {
        startActivity(new Intent(this.getContext(),clazz));
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }
}
package com.zqf.zhangqflibrary.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zqf.zhangqflibrary.base.mvp.BaseIModel;
import com.zqf.zhangqflibrary.base.mvp.BaseIView;
import com.zqf.zhangqflibrary.base.mvp.BasePresenter;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;

import me.yokeyword.fragmentation.SupportFragment;
import util.TUtil;

/**
 * -----------------------------
 * Created by zqf on 2018/1/22.
 * ---------------------------
 */

public abstract class BaseMvpFragment<P extends BasePresenter, M extends BaseIModel, B extends ViewDataBinding> extends SupportFragment implements BaseIView {
    protected String TAG = getClass().getSimpleName();
    public LayoutInflater inflater;
    protected M mMode;
    protected P mPresenter;
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
            initPresenter();
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
    public void onSupportVisible() {
        super.onSupportVisible();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (useEventBus())//如果要使用 Eventbus 请将此方法返回 true
            EventBus.getDefault().register(this);//注册 Eventbus
    }

    @Override
    public void onDestroy() {
        if(mPresenter!=null){
            mPresenter.onDestroy();
        }
        if (useEventBus()){
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
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    public void initPresenter() {
        if (this instanceof BaseIView &&
                this.getClass().getGenericSuperclass() instanceof ParameterizedType &&
                ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            mMode = TUtil.getT(this, 1);
            mPresenter = TUtil.getT(this, 0);

            if (mPresenter != null) mPresenter.setMV(mMode,this);
        }
    }

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
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }

    @Override
    public Context getCt() {
        return this.getActivity();
    }
}
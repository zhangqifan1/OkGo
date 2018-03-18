package com.zqf.zhangqflibrary.base.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.zqf.zhangqflibrary.base.mvp.BaseIModel;
import com.zqf.zhangqflibrary.base.mvp.BaseIView;
import com.zqf.zhangqflibrary.base.mvp.BasePresenter;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;

import me.yokeyword.fragmentation.SupportActivity;
import util.TUtil;

/**
 * -----------------------------
 * Created by zqf on 2018/1/22.
 * ---------------------------
 */

public abstract class BaseMvpActivity<P extends BasePresenter, M extends BaseIModel, B extends ViewDataBinding> extends SupportActivity implements BaseIView {
    protected String TAG = getClass().getSimpleName();
    protected M mMode;
    protected P mPresenter;

    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 屏幕是否发生过旋转
     */
    private boolean isConfigChange;

    protected B mViewBinding;

    /**
     * 请求队列
     */
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MyActivityManager.addActivity(this);
        // 方法1 Android获得屏幕的宽和高
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        // 创建请求队列, 默认并发3个请求, 传入数字改变并发数量
        mRequestQueue = NoHttp.newRequestQueue(5);

        View rootView = getLayoutInflater().inflate(this.getLayoutId(), null, false);
        this.setContentView(rootView);
        try {
            mViewBinding = DataBindingUtil.bind(rootView);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            initFontScale();
            beforeInitView(savedInstanceState);
            initView();
            initPresenter();
            initData();
            initListener();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (useEventBus())//如果要使用 Eventbus 请将此方法返回 true
        {
            EventBus.getDefault().register(this);//注册 Eventbus
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        MyActivityManager.removeActivity(this);
        mPresenter.onDestroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();

        mRequestQueue.cancelAll(); // 退出页面时时取消所有请求。
        mRequestQueue.stop(); // 退出时销毁队列，回收资源。


    }

    private void initFontScale() {
        Configuration configuration = getResources().getConfiguration();
        configuration.fontScale = (float) 1;
        //0.85 小, 1 标准大小, 1.15 大，1.3 超大 ，1.45 特大
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }

    /**
     * 屏幕发生改变调用
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isConfigChange = true;
    }

    public void setContentView(View rootView) {
        super.setContentView(rootView);
    }

    /**
     * 获取资源文件布局
     *
     * @return 资源布局文件layout
     */
    protected abstract int getLayoutId();

    /**
     * @param savedInstanceState
     */
    protected void beforeInitView(Bundle savedInstanceState) {
        mContext = this;
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
    }

    /**
     * 获取传递的bundle数据
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * 初始化view
     */
    public abstract void initView();


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
            mPresenter = TUtil.getT(this, 0);
            mMode = TUtil.getT(this, 1);
            if (mPresenter != null) mPresenter.setMV(mMode, this);
        }
    }

    /**
     * 是否使用 {@link EventBus},默认为使用(false)，
     *
     * @return
     */
    public boolean useEventBus() {
        return false;
    }

    /**
     * 初始化按钮监听
     */
    protected abstract void initListener();

    public void startActivity(Class clazz,boolean isFinish) {
        startActivity(new Intent(this,clazz));
        if (isFinish) {
            finish();
        }
    }

    public void startActivity(Class clazz) {
        startActivity(new Intent(this,clazz));
    }

    @Override
    public Context getCt() {
        return this;
    }
}

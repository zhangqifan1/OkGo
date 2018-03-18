package com.zqf.zhangqflibrary.base.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zqf.zhangqflibrary.R;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * -----------------------------
 * Created by zqf on 2018/1/22.
 * ---------------------------
 */

public abstract class BaseActivity<B extends ViewDataBinding> extends SupportActivity {
    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 屏幕是否发生过旋转
     */
    private boolean isConfigChange;

    protected B mViewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MyActivityManager.addActivity(this);
        //判断当前系统版本是否>=Andoird4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //设置状态栏背景状态
            //true：表明当前Android系统版本>=4.4
            setTranslucentStatus(true);
        }
        //实例化SystemBarTintManager
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        // 通知标题栏所需颜色
        setColor(tintManager,1);
        // 方法1 Android获得屏幕的宽和高
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        View rootView = getLayoutInflater().inflate(this.getLayoutId(), null, false);
        //去除状态栏之后要写这句
        rootView.setFitsSystemWindows(true);
        this.setContentView(rootView);
        try {
            mViewBinding = DataBindingUtil.bind(rootView);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            initFontScale();
            beforeInitView(savedInstanceState);
            initView();
            initData();
            initListener();
        }
    }

    private void setColor(SystemBarTintManager tintManager, int color) {
        tintManager.setStatusBarTintResource(R.color.BaseOrange);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (useEventBus())//如果要使用 Eventbus 请将此方法返回 true
            EventBus.getDefault().register(this);//注册 Eventbus
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
        if (useEventBus()){EventBus.getDefault().unregister(this);}
        super.onDestroy();
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

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    public void startActivity(Class clazz,boolean isFinish) {
        startActivity(new Intent(this,clazz));
        if (isFinish) {
            finish();
        }
    }

    public void startActivity(Class clazz) {
        startActivity(new Intent(this,clazz));
    }
}
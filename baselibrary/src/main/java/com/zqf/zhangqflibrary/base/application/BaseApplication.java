package com.zqf.zhangqflibrary.base.application;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

/**
 * -----------------------------
 * Created by zqf on 2018/1/22.
 * ---------------------------
 */

public abstract class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {
    protected  static Context CONTEXT = null;
    protected static SharedPreferences sp = null;
    protected static String DEVIECE_ID;
    private String spName = "VSHOP_SP";
    protected static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        if (CONTEXT == null) {
            CONTEXT = getApplicationContext();
        }
        if (sp == null) {
            sp = CONTEXT.getSharedPreferences(spName, MODE_PRIVATE);
        }
        if (DEVIECE_ID == null) {
            String deviceid = getDeviceid();
            if (deviceid != null) {
                DEVIECE_ID = deviceid;
            }
        }
        registerActivityLifecycleCallbacks(this);

        initNoHttp();

        initThirdParty();

    }

    /**
     * 做一些第三方的初始化
     */
    public abstract void initThirdParty();



    private void initNoHttp() {
        OkHttpNetworkExecutor okHttpNetworkExecutor = new OkHttpNetworkExecutor();

        InitializationConfig config = InitializationConfig.newBuilder(CONTEXT)
                // 全局连接服务器超时时间，单位毫秒，默认10s。
                .connectionTimeout(30 * 1000)
                // 全局等待服务器响应超时时间，单位毫秒，默认10s。
                .readTimeout(30 * 1000)

                // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                .cacheStore(
                        // 如果不使用缓存，setEnable(false)禁用。
                        new DBCacheStore(CONTEXT).setEnable(true)
                )
                // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现CookieStore接口。
                .cookieStore(
                        // 如果不维护cookie，setEnable(false)禁用。
                        new DBCookieStore(CONTEXT).setEnable(true)
                )
                // 配置网络层，默认URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
                .networkExecutor(okHttpNetworkExecutor)
                // 全局通用Header，add是添加，多次调用add不会覆盖上次add。
//                .addHeader()
                // 全局通用Param，add是添加，多次调用add不会覆盖上次add。
//                .addParam()
//                .sslSocketFactory() // 全局SSLSocketFactory。
//                .hostnameVerifier() // 全局HostnameVerifier。
                .retry(7) // 全局重试次数，配置后每个请求失败都会重试x次。
                .build();
        NoHttp.initialize(config);
    }


    public static Context getContext() {
        return CONTEXT;
    }

    public static SharedPreferences getSp() {
        return sp;
    }

    private String getDeviceid() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        String DEVICE_ID = tm.getDeviceId();
        return DEVICE_ID;
    }

    public static String getDEVICE_ID() {
        return DEVIECE_ID;
    }

    public static <T extends BaseApplication> T me() {
        return (T) instance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//        MyActivityManager.addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
//        MyActivityManager.removeActivity(activity);
    }
}

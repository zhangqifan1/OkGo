package com.zqf.rxhttputils;

import android.content.Context;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Response;

/**
 * -----------------------------
 * Created by zqf on 2018/3/18.
 * ---------------------------
 */

public class Model implements Const.NewsModel {
    @Override
    public void OnDestroy() {

    }

    @Override
    public void requestNewsData(final callBack callBack, Context context) {
                OkGo.<NewsBean>get(HttpConst.NEWSURL)
                .tag(this)
                        .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                 .execute(new BeanCallback<NewsBean>(context){
                     @Override
                     public NewsBean convertResponse(okhttp3.Response response) throws Throwable {
                         return new Gson().fromJson(response.body().string(),NewsBean.class);
                     }

                     @Override
                     public void onSuccess(Response<NewsBean> response) {
                         callBack.setNewsData(response.body());
                     }
                 });
    }
}

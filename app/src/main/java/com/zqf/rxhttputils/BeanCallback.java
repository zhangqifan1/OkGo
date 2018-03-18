package com.zqf.rxhttputils;

import android.content.Context;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * -----------------------------
 * Created by zqf on 2018/3/18.
 * ---------------------------
 */

public  class BeanCallback<T> extends AbsCallback<T> {
    private Context context;

    public BeanCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        DialogUtil.showDialog(context);
    }

    @Override
    public void onFinish() {
        super.onFinish();
        DialogUtil.disMissDialog();
    }

    @Override
    public void onSuccess(Response<T> response) {

    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        return null;
    }
}

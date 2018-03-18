package com.zqf.zhangqflibrary.base.mvp;

import java.lang.ref.WeakReference;

/**
 * -----------------------------
 * Created by zqf on 2018/1/22.
 * ---------------------------
 */

public class BasePresenter<M extends BaseIModel, V extends BaseIView> {
    protected M mModel;
    protected V mView;
    private WeakReference<V> vWeakReference;

    public void setMV(M m, V v) {
        this.mModel = m;
        this.mView=v;
        vWeakReference = new WeakReference<>(v);
        onStart();
    }

    /**
     * 开始的方法
     */
    public void onStart() {
    }

    /**
     * 关闭的方法
     */
    public void onDestroy() {
        if (mModel != null){
            mModel.OnDestroy();
            this.mModel = null;
        }
        if(mView!=null){
            vWeakReference.clear();
            this.mView = null;
        }



    }
}

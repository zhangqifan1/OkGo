package com.zqf.rxhttputils;

import com.zqf.zhangqflibrary.base.mvp.BasePresenter;

/**
 * -----------------------------
 * Created by zqf on 2018/3/18.
 * ---------------------------
 */

public class presenter extends Const.pNewsPresenter {

    @Override
    public void setMvp() {
        mModel.requestNewsData(new Const.NewsModel.callBack() {
            @Override
            public void setNewsData(NewsBean newsBean2) {
                mView.showData(newsBean2);
            }
        },mView.getCt());
    }
}

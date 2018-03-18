package com.zqf.rxhttputils;

import android.content.Context;

import com.zqf.zhangqflibrary.base.mvp.BaseIModel;
import com.zqf.zhangqflibrary.base.mvp.BaseIView;
import com.zqf.zhangqflibrary.base.mvp.BasePresenter;

/**
 * -----------------------------
 * Created by zqf on 2018/2/5.
 * ---------------------------
 */

public interface Const {

    interface NewsModel extends BaseIModel {
        interface callBack{
            void setNewsData(NewsBean newsBean2);
        }
        void  requestNewsData(callBack callBack, Context context);
    }

    interface NewsView extends BaseIView {
        void showData(NewsBean newsBean);
    }

    abstract class pNewsPresenter extends BasePresenter<NewsModel,NewsView> {

        public abstract  void setMvp();

    }

}

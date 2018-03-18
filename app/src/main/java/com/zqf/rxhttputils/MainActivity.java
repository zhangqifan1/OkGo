package com.zqf.rxhttputils;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zqf.rxhttputils.databinding.ActivityMainBinding;
import com.zqf.zhangqflibrary.base.activity.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity<presenter,Model,ActivityMainBinding> implements Const.NewsView{


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    public void initView() {

    }

    @Override
    protected void initData() {
        mPresenter.setMvp();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void showData(NewsBean newsBean) {
        mViewBinding.tv.setText(newsBean.getData().get(0).getNews_title()+"");
    }
}

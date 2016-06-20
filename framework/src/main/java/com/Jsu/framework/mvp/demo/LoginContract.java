package com.Jsu.framework.mvp.demo;

import com.Jsu.framework.mvp.BasePresenter;
import com.Jsu.framework.mvp.BaseView;

/**
 * Created by JSu on 2016/6/13.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void showProgress();

        void hideProgress();

        void toast(String msg);
    }

    interface Presenter extends BasePresenter {
        void getSmsCode();

        void login(long uid, String pwd);
    }
}

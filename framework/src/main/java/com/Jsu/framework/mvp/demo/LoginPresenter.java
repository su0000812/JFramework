package com.Jsu.framework.mvp.demo;

/**
 * Created by JSu on 2016/6/13.
 */

public class LoginPresenter implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.View view) {
        view.setPresenter(this);
    }

    @Override
    public void login(long uid, String pwd) {

    }

    @Override
    public void getSmsCode() {

    }

    @Override
    public void onStart() {

    }
}

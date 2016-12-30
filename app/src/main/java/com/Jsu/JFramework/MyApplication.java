package com.Jsu.JFramework;

import android.app.Application;

import com.Jsu.framework.utils.okhttp.OkHttpUtils;
import com.Jsu.framework.utils.okhttp.https.HttpsUtils;
import com.Jsu.framework.utils.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by JSu on 2016/7/20.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        CookieJarImpl cookieJar1 = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
//                .cookieJar(cookieJar1)
                .sslSocketFactory(HttpsUtils.getSslSocketFactory(null, null, null))
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}

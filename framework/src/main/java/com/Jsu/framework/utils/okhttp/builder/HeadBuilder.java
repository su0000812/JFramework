package com.Jsu.framework.utils.okhttp.builder;


import com.Jsu.framework.utils.okhttp.OkHttpUtils;
import com.Jsu.framework.utils.okhttp.request.OtherRequest;
import com.Jsu.framework.utils.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers).build();
    }
}

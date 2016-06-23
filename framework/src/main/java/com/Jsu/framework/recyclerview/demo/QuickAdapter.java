package com.Jsu.framework.recyclerview.demo;

import com.Jsu.framework.R;
import com.Jsu.framework.recyclerview.adapter.BaseQuickAdapter;
import com.Jsu.framework.recyclerview.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by su on 16-6-22.
 */
public class QuickAdapter extends BaseQuickAdapter<TestBean> {

    public QuickAdapter(List data) {
        super(R.layout.layout_test_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        helper.setText(R.id.tv_title, item.title);
    }
}

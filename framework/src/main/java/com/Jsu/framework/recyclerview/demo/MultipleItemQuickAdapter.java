package com.Jsu.framework.recyclerview.demo;

import android.content.Context;


import com.Jsu.framework.R;
import com.Jsu.framework.recyclerview.adapter.BaseMultiItemQuickAdapter;
import com.Jsu.framework.recyclerview.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by su on 16-6-22.
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem> {

    public MultipleItemQuickAdapter(Context context, List data) {
        super(data);
        addItemType(MultipleItem.TEXT, R.layout.item_text_view);
        addItemType(MultipleItem.IMG, R.layout.item_image_view);
        addItemType(MultipleItem.IMGS, R.layout.item_image_views);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.TEXT:
                break;
            case MultipleItem.IMG:
                break;
            case MultipleItem.IMGS:
                break;
        }
    }

}

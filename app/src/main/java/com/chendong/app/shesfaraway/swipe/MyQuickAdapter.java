package com.chendong.app.shesfaraway.swipe;


import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chendong.app.shesfaraway.R;
import com.chendong.app.shesfaraway.bean.EventMode;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class MyQuickAdapter extends BaseQuickAdapter<EventMode, BaseViewHolder> {


    public MyQuickAdapter(@LayoutRes int layoutResId, @Nullable List<EventMode> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventMode item) {
        helper.setBackgroundColor(R.id.card, Color.parseColor(item.getColor()));
        helper.setText(R.id.list_title,item.getTitle());
    }
}

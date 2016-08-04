package com.fanwe.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.constant.Constant;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.MediaRewardCtlItemModel;
import com.fanwe.o2o.newo2o.R;

import java.util.List;

/**
 * Created by Edison on 2016/8/1.
 */
public class MediaRewardAdapter extends SDSimpleAdapter<MediaRewardCtlItemModel> {

    private int type;

    public MediaRewardAdapter(List<MediaRewardCtlItemModel> listModel, Activity activity, int type) {
        super(listModel, activity);
        this.type = type;
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        if (type == Constant.Reward.ORDER) {
            return R.layout.item_media_order_reward;
        } else {
            return R.layout.item_media_reward;
        }
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, MediaRewardCtlItemModel model) {
        TextView label1 = ViewHolder.get(R.id.label1, convertView);
        TextView label2 = ViewHolder.get(R.id.label2, convertView);
        TextView label3 = ViewHolder.get(R.id.label3, convertView);

        SDViewBinder.setTextView(label1, "测试名称" + position);
        SDViewBinder.setTextView(label2, "￥100");
        SDViewBinder.setTextView(label3, "￥1");

        if (type == Constant.Reward.ORDER) {
            TextView label4 = ViewHolder.get(R.id.label4, convertView);
            SDViewBinder.setTextView(label4, "2015/9/12");
        }
    }

}
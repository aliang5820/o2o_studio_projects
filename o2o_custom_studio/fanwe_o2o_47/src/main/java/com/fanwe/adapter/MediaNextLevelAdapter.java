package com.fanwe.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.MediaNextLevelCtlItemModel;
import com.fanwe.o2o.newo2o.R;

import java.util.List;

/**
 * Created by Edison on 2016/7/31.
 */
public class MediaNextLevelAdapter extends SDSimpleAdapter<MediaNextLevelCtlItemModel> {

    public MediaNextLevelAdapter(List<MediaNextLevelCtlItemModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_media_next_level;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, MediaNextLevelCtlItemModel model) {
        ImageView user_icon = ViewHolder.get(R.id.user_icon, convertView);
        TextView user_name = ViewHolder.get(R.id.user_name, convertView);
        TextView user_level = ViewHolder.get(R.id.user_level, convertView);
        TextView focus_time = ViewHolder.get(R.id.focus_time, convertView);

        SDViewBinder.setTextView(user_name, "测试昵称" + position);
        SDViewBinder.setTextView(user_level, position % 2 == 0 ? "合伙人" : "会员店");
        SDViewBinder.setTextView(focus_time, "2015/9/12 14:20");
    }

}
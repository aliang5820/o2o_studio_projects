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
import com.fanwe.model.MediaNextLevelItemModel;
import com.fanwe.o2o.newo2o.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Edison on 2016/7/31.
 */
public class MediaNextLevelAdapter extends SDSimpleAdapter<MediaNextLevelItemModel> {

    private SimpleDateFormat simpleDateFormat;

    public MediaNextLevelAdapter(List<MediaNextLevelItemModel> listModel, Activity activity) {
        super(listModel, activity);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_media_next_level;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, MediaNextLevelItemModel model) {
        ImageView user_icon = ViewHolder.get(R.id.user_icon, convertView);
        TextView user_name = ViewHolder.get(R.id.user_name, convertView);
        TextView user_level = ViewHolder.get(R.id.user_level, convertView);
        TextView focus_time = ViewHolder.get(R.id.focus_time, convertView);

        MediaNextLevelItemModel itemModel = mListModel.get(position);
        SDViewBinder.setTextView(user_name, itemModel.getUser_name());
        //0会员  1 会员店 ，2 商户合伙人，3个人合伙人
        String type = "消费股东";
        switch (itemModel.getType()) {
            case 0:
                type = "消费股东";
                break;
            case 1:
                type = "会员店";
                break;
            case 2:
                type = "商户合伙人";
                break;
            case 3:
                type = "个人合伙人";
                break;
        }
        SDViewBinder.setTextView(user_level, type);

        Date date = new Date();
        if(itemModel.getTime() > 0) {
            date = new Date(itemModel.getTime() * 1000);
        }
        String time = simpleDateFormat.format(date);
        SDViewBinder.setTextView(focus_time, time);
    }

}
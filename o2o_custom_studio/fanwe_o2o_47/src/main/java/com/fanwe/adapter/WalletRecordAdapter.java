package com.fanwe.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.MediaNextLevelItemModel;
import com.fanwe.model.WalletRecordModel;
import com.fanwe.o2o.newo2o.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Edison on 2016/11/8.
 */

public class WalletRecordAdapter extends SDSimpleAdapter<WalletRecordModel> {

    private SimpleDateFormat simpleDateFormat;

    public WalletRecordAdapter(List<WalletRecordModel> listModel, Activity activity) {
        super(listModel, activity);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_wallet_record;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, WalletRecordModel model) {
        TextView record_time = ViewHolder.get(R.id.time, convertView);
        TextView record_desc = ViewHolder.get(R.id.desc, convertView);
        TextView record_value = ViewHolder.get(R.id.value, convertView);

        WalletRecordModel itemModel = mListModel.get(position);
        SDViewBinder.setTextView(record_desc, itemModel.getDesc());
        SDViewBinder.setTextView(record_value, itemModel.getValue());

        Date date = new Date();
        if(itemModel.getTime() > 0) {
            date = new Date(itemModel.getTime() * 1000);
        }
        String time = simpleDateFormat.format(date);
        SDViewBinder.setTextView(record_time, time);
    }

}

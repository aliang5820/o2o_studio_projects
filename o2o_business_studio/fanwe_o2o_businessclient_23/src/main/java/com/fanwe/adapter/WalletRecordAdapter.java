package com.fanwe.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.businessclient.R;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.WalletRecordModel;

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
        if (itemModel.getType() == 0) {
            //提现
            String str;
            switch (itemModel.getPay_type()) {
                case 0:
                    //支付宝
                    str = "支付宝";
                    break;
                case 1:
                    //微信
                    str = "微信";
                    break;
                case 2:
                    //借记卡
                    str = "借记卡";
                    break;
                case 3:
                    //贷记卡
                    str = "贷记卡";
                    break;
                default:
                    //其他
                    str = "其他";
                    break;
            }
            SDViewBinder.setTextView(record_desc, "余额提现-" + str);
            SDViewBinder.setTextView(record_value, mActivity.getString(R.string.wallet_record_money1, itemModel.getMoney()));
        } else {
            //分佣
            SDViewBinder.setTextView(record_desc, "消费奖励");
            SDViewBinder.setTextView(record_value, mActivity.getString(R.string.wallet_record_money2, itemModel.getMoney()));
        }


        Date date = new Date();
        if (itemModel.getTime() > 0) {
            date = new Date(itemModel.getTime() * 1000);
        }
        String time = simpleDateFormat.format(date);
        SDViewBinder.setTextView(record_time, time);
    }

}

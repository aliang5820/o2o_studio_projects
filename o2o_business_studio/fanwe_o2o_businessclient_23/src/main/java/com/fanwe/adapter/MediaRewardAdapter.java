package com.fanwe.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.MediaRewardItemModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Edison on 2016/8/1.
 */
public class MediaRewardAdapter extends SDSimpleAdapter<MediaRewardItemModel> {

    private int type;
    private SimpleDateFormat simpleDateFormat;

    public MediaRewardAdapter(List<MediaRewardItemModel> listModel, Activity activity, int type) {
        super(listModel, activity);
        this.type = type;
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
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
    public void bindData(int position, View convertView, ViewGroup parent, MediaRewardItemModel model) {
        if (type == Constant.Reward.ORDER) {
            TextView label1 = ViewHolder.get(R.id.label1, convertView);
            TextView label2 = ViewHolder.get(R.id.label2, convertView);
            TextView label3 = ViewHolder.get(R.id.label3, convertView);
            TextView label4 = ViewHolder.get(R.id.label4, convertView);
            //订单号
            /*switch (model.getBelong_id()) {
                case 1:
                    SDViewBinder.setTextView(label1, "会员店消费");
                    break;
                case 2:
                    SDViewBinder.setTextView(label1, "合伙人消费");
                    break;
                case 3:
                    SDViewBinder.setTextView(label1, "销售");
                    break;
            }*/
            SDViewBinder.setTextView(label1, model.getBelong_value());
            //下单时间
            Date date = new Date();
            if(model.getOrderTime() > 0) {
                date = new Date(model.getOrderTime() * 1000);
            }
            String time = simpleDateFormat.format(date);
            SDViewBinder.setTextView(label2, time);
            //消费
            SDViewBinder.setTextView(label3, mActivity.getString(R.string.money, model.getConsume_money()));
            //奖励
            SDViewBinder.setTextView(label4, mActivity.getString(R.string.money, model.getReward_money()));
        } else {
            TextView label1 = ViewHolder.get(R.id.label1, convertView);
            TextView label2 = ViewHolder.get(R.id.label2, convertView);
            TextView label3 = ViewHolder.get(R.id.label3, convertView);
            //名称
            SDViewBinder.setTextView(label1, mActivity.getString(R.string.number, model.getUser_name()));
            //招募时间
            Date date = new Date();
            if(model.getOrderTime() > 0) {
                date = new Date(model.getOrderTime() * 1000);
            }
            String time = simpleDateFormat.format(date);
            SDViewBinder.setTextView(label2, time);
            //奖励
            SDViewBinder.setTextView(label3, mActivity.getString(R.string.money, model.getReward_money()));
        }

    }

}
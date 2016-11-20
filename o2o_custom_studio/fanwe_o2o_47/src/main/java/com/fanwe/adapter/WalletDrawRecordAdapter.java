package com.fanwe.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.WalletDrawRecordModel;
import com.fanwe.o2o.newo2o.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Edison on 2016/11/10.
 */

public class WalletDrawRecordAdapter extends SDSimpleAdapter<WalletDrawRecordModel> {

    private SimpleDateFormat simpleDateFormat;

    public WalletDrawRecordAdapter(List<WalletDrawRecordModel> listModel, Activity activity) {
        super(listModel, activity);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_wallet_draw_record;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, WalletDrawRecordModel model) {
        TextView time = ViewHolder.get(R.id.time, convertView);
        TextView order_no = ViewHolder.get(R.id.order_no, convertView);
        TextView money = ViewHolder.get(R.id.money, convertView);
        TextView draw_type = ViewHolder.get(R.id.draw_type, convertView);
        TextView draw_status = ViewHolder.get(R.id.draw_status, convertView);
        TextView info = ViewHolder.get(R.id.info, convertView);
        View failed_layout = ViewHolder.get(R.id.failed_layout, convertView);

        WalletDrawRecordModel itemModel = mListModel.get(position);
        //提现方式
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
        SDViewBinder.setTextView(draw_type, str);
        //时间
        Date date = new Date();
        if (itemModel.getCreate_time() > 0) {
            date = new Date(itemModel.getCreate_time() * 1000);
        }
        String formatTime = simpleDateFormat.format(date);
        SDViewBinder.setTextView(time, formatTime);
        //订单号
        SDViewBinder.setTextView(order_no, itemModel.getOrder_no());
        //金额
        SDViewBinder.setTextView(money, mActivity.getString(R.string.wallet_all_money, itemModel.getMoney()));
        //状态 0 未审核  1 审核通过 2 审核失败
        switch (itemModel.getIs_auth()) {
            case 0:
                failed_layout.setVisibility(View.GONE);
                SDViewBinder.setTextView(draw_status, "待审核");
                break;
            case 1:
                switch (itemModel.getIs_pay()) {
                    case 0:
                        failed_layout.setVisibility(View.GONE);
                        SDViewBinder.setTextView(draw_status, "待审核");
                        break;
                    case 1:
                        failed_layout.setVisibility(View.GONE);
                        SDViewBinder.setTextView(draw_status, "已发放");
                        break;
                    case 2:
                        failed_layout.setVisibility(View.VISIBLE);
                        SDViewBinder.setTextView(draw_status, "未成功");
                        SDViewBinder.setTextView(info, itemModel.getPay_remark());
                        break;
                }
                break;
            case 2:
                failed_layout.setVisibility(View.VISIBLE);
                SDViewBinder.setTextView(draw_status, "未成功");
                if(itemModel.getIs_auth() == 2) {
                    SDViewBinder.setTextView(info, itemModel.getAuth_remark());
                } else if(itemModel.getIs_pay() == 2){
                    SDViewBinder.setTextView(info, itemModel.getPay_remark());
                } else {
                    SDViewBinder.setTextView(info, "未知原因");
                }
                break;
        }

    }

}
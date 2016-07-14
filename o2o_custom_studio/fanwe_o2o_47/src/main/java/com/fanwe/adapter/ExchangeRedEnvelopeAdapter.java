package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogCustom.SDDialogCustomListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.RedEnvelopeModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.sunday.eventbus.SDEventManager;

public class ExchangeRedEnvelopeAdapter extends SDSimpleAdapter<RedEnvelopeModel>
{
	public ExchangeRedEnvelopeAdapter(List<RedEnvelopeModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_exchange_red_envelope;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final RedEnvelopeModel model)
	{
		TextView tv_money = ViewHolder.get(R.id.tv_money, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_score = ViewHolder.get(R.id.tv_score, convertView);
		TextView tv_exchange = ViewHolder.get(R.id.tv_exchange, convertView);

		SDViewBinder.setTextView(tv_money, model.getMoney_format());
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_score, String.valueOf(model.getExchange_score()));

		tv_exchange.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				showDoExchangeDialog(model);
			}
		});
	}

	protected void showDoExchangeDialog(final RedEnvelopeModel model)
	{
		SDDialogConfirm dialog = new SDDialogConfirm();
		dialog.setTextContent("确定要兑换吗?");
		dialog.setmListener(new SDDialogCustomListener()
		{

			@Override
			public void onDismiss(SDDialogCustom dialog)
			{

			}

			@Override
			public void onClickConfirm(View v, SDDialogCustom dialog)
			{
				requestDoExchange(model);
			}

			@Override
			public void onClickCancel(View v, SDDialogCustom dialog)
			{
			}
		});
		dialog.show();
	}

	protected void requestDoExchange(RedEnvelopeModel model)
	{
		RequestModel requestModel = new RequestModel();
		requestModel.putCtl("uc_ecv");
		requestModel.putAct("do_exchange");
		requestModel.put("id", model.getId());
		InterfaceServer.getInstance().requestInterface(requestModel, new SDRequestCallBack<BaseActModel>()
		{
			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() > 0)
				{
					SDEventManager.post(EnumEventTag.EXCHANGE_RED_ENVELOPE_SUCCESS.ordinal());
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{

			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}
		});
	}

}

package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_money_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.SDFormatUtil;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 账户余额
 * 
 * @author Administrator
 * 
 */
public class AccountMoneyActivity extends BasePullToRefreshScrollViewActivity
{

	@ViewInject(R.id.tv_money)
	private TextView mTv_money;

	@ViewInject(R.id.tv_withdraw)
	private TextView mTv_withdraw;

	private Uc_money_indexActModel mActModel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_account_money);
		init();
	}

	private void init()
	{
		initTitle();
		registerClick();
	}

	@Override
	protected void onResume()
	{
		requestData();
		super.onResume();
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshScrollView view)
	{
		requestData();
		super.onPullDownToRefresh(view);
	}

	private void requestData()
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_money");
		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_money_indexActModel>()
		{
			@Override
			public void onStart()
			{
				super.onStart();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				mActModel = actModel;
				if (actModel.getStatus() > 0)
				{
					mTv_money.setText(SDFormatUtil.formatMoneyChina(actModel.getMoney()));
				}
				super.onSuccess(responseInfo);
			}

			@Override
			public void onFinish()
			{
				onRefreshComplete();
				super.onFinish();
			}
		});
	}

	private void registerClick()
	{
		mTv_withdraw.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mActModel != null)
				{
					double money = mActModel.getMoney();
					if (money > 0)
					{
						Intent intent = new Intent(getApplicationContext(), WithdrawActivity.class);
						startActivity(intent);
					} else
					{
						SDToast.showToast("没有余额可提现");
					}
				} else
				{
					requestData();
				}
			}
		});
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("账户余额");
		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("提现明细");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		Intent intent = new Intent(getApplicationContext(), WithdrawLogActivity.class);
		startActivity(intent);
		super.onCLickRight_SDTitleSimple(v, index);
	}

}

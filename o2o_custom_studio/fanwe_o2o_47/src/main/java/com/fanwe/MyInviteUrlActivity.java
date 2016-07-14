package com.fanwe;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_invite_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 邀请链接
 * 
 * @author Administrator
 * @date 2016-4-11 上午10:13:38
 */
public class MyInviteUrlActivity extends BasePullToRefreshScrollViewActivity
{

	@ViewInject(R.id.et_invite_url)
	private EditText mEt_invite_url;

	@ViewInject(R.id.et_invite_code)
	private EditText mEt_invite_code;

	@ViewInject(R.id.iv_invite_qrcode)
	private ImageView mIv_invite_qrcode;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_my_invite_url);
		init();
	}

	private void init()
	{
		initTitle();
		mEt_invite_url.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String content = mEt_invite_url.getText().toString();
				if (!isEmpty(content))
				{
					SDOtherUtil.copyText(content);
					SDToast.showToast("邀请链接已经复制");
				}
			}
		});
		mEt_invite_code.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String content = mEt_invite_code.getText().toString();
				if (!isEmpty(content))
				{
					SDOtherUtil.copyText(content);
					SDToast.showToast("邀请码已经复制");
				}
			}
		});
		setRefreshing();
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("邀请链接");
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
		model.putCtl("uc_invite");
		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_invite_indexActModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mEt_invite_url.setText(actModel.getShare_url());
					mEt_invite_code.setText(actModel.getInvite_code());
					SDViewBinder.setImageView(actModel.getQrcode(), mIv_invite_qrcode);
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

}

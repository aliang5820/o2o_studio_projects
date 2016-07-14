package com.fanwe;

import android.content.Intent;
import android.os.Bundle;

import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.common.CommonInterfaceRequestModel;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.model.BizUserCtlDoLoginActModel;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.umeng.UmengPushManager;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;

/**
 * 
 * 
 * 欢迎页
 */
public class InitActivity extends BaseActivity
{
	private final long mTime = 3000;

	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		setContentView(R.layout.act_init);
		init();
	}

	private void init()
	{

		new Thread()
		{
			public void run()
			{
				try
				{
					sleep(mTime);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				if (isFinishLogin())
				{
					requestInterface();
				} else
				{
					startActivity(new Intent(InitActivity.this, LoginActivity.class));
					finish();
				}
			}
		}.start();
	}

	private boolean isFinishLogin()
	{
		LocalUserModel localUserModel = App.getApp().getmLocalUser();
		if (localUserModel != null)
		{
			return true;
		}
		return false;

	}

	private void requestInterface()
	{
		RequestModel model = CommonInterfaceRequestModel.initBizUserCtlDoLoginActRquestModel();
		model.put("device_token", UmengPushManager.getPushAgent().getRegistrationId());
		if (model != null)
		{
			SDRequestCallBack<BizUserCtlDoLoginActModel> handler = new SDRequestCallBack<BizUserCtlDoLoginActModel>()
			{
				@Override
				public void onSuccess(BizUserCtlDoLoginActModel actModel)
				{
					if (!SDInterfaceUtil.dealactModel(actModel, InitActivity.this))
					{
						SDToast.showToast(actModel.getInfo());
						switch (actModel.getStatus())
						{
						case 0:
							dealLoginFailure(actModel);
							break;
						case 1:
							dealLoginSuccess(actModel);
							break;
						default:
							break;
						}
					}
				}
			};
			InterfaceServer.getInstance().requestInterface(model, handler);
		}

	}

	private void dealLoginSuccess(BizUserCtlDoLoginActModel actModel)
	{
		if (actModel.getAccount_info() != null)
		{
			LocalUserModel user = new LocalUserModel();
			user.setAccount_name(actModel.getAccount_info().getAccount_name());
			user.setAccount_password(actModel.getAccount_info().getAccount_password());
			App.getApp().setmLocalUser(user);
			startActivity(new Intent(InitActivity.this, MainActivity.class));
			finish();
		}
	}

	private void dealLoginFailure(BizUserCtlDoLoginActModel actModel)
	{
		App.getApp().clearAppsLocalUserModel();
		startActivity(new Intent(InitActivity.this, LoginActivity.class));
		finish();
	}

}

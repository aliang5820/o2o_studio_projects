package com.fanwe.work;

import android.app.Dialog;
import android.view.View;

import com.fanwe.app.App;
import com.fanwe.common.CommonInterface;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogCustom.SDDialogCustomListener;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDTimer;
import com.fanwe.library.utils.SDTimer.SDTimerListener;
import com.fanwe.model.Init_indexActModel;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.sunday.eventbus.SDEventManager;
import com.ta.util.netstate.TANetChangeObserver;
import com.ta.util.netstate.TANetWorkUtil;
import com.ta.util.netstate.TANetWorkUtil.netType;
import com.ta.util.netstate.TANetworkStateReceiver;

public class RetryInitWorker implements TANetChangeObserver
{

	private static final int RETRY_TIME_SPAN = 1000 * 30;
	private static final int RETRY_MAX_COUNT = 10;

	private static RetryInitWorker mInstance;
	private SDTimer mTimer = new SDTimer();
	private boolean mIsInRetryInit = false;
	private boolean mIsInitSuccess = false;
	private int mRetryCount = 0;
	private Dialog mDialog;

	private RetryInitWorker()
	{
		TANetworkStateReceiver.registerObserver(this);
	}

	public static RetryInitWorker getInstance()
	{
		if (mInstance == null)
		{
			syncInit();
		}
		return mInstance;
	}

	private synchronized static void syncInit()
	{
		if (mInstance == null)
		{
			mInstance = new RetryInitWorker();
		}
	}

	public void start()
	{
		if (mIsInRetryInit)
		{
			return;
		}
		mIsInRetryInit = true;
		mIsInitSuccess = false;
		mRetryCount = 0;
		mTimer.startWork(0, RETRY_TIME_SPAN, new SDTimerListener()
		{

			@Override
			public void onWorkMain()
			{
				// 达到最大重试次数，并且没有初始化成功
				if (mRetryCount >= RETRY_MAX_COUNT && !mIsInitSuccess)
				{
					stop();
					showRetryFailDialog();
					return;
				}
				mRetryCount++;
				LogUtil.i("retry:" + mIsInitSuccess);
				if (TANetWorkUtil.isNetworkAvailable(App.getApplication()))
				{
					if (!mIsInitSuccess)
					{
						CommonInterface.requestInit(mListener);
					} else
					{
						stop();
						SDEventManager.post(EnumEventTag.RETRY_INIT_SUCCESS.ordinal());
					}
				} else
				{
					stop();
				}
			}

			@Override
			public void onWork()
			{
			}
		});
	}

	protected void showRetryFailDialog()
	{
		if (SDPackageUtil.isBackground())
		{
			return;
		}
		if (mDialog != null)
		{
			mDialog.dismiss();
		}

		SDDialogConfirm dialog = new SDDialogConfirm();
		dialog.setCancelable(false);
		dialog.setTextContent("已经尝试初始化" + RETRY_MAX_COUNT + "次失败，是否继续重试？");
		dialog.setTextConfirm("重试").setmListener(new SDDialogCustomListener()
		{

			@Override
			public void onDismiss(SDDialogCustom dialog)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onClickConfirm(View v, SDDialogCustom dialog)
			{
				start();

			}

			@Override
			public void onClickCancel(View v, SDDialogCustom dialog)
			{
				stop();
			}
		});
		dialog.show();
		mDialog = dialog;
	}

	public void stop()
	{
		mTimer.stopWork();
		mIsInRetryInit = false;
		LogUtil.i("stop retry");
	}

	@Override
	public void onConnect(netType type)
	{
		if (!mIsInitSuccess)
		{
			start();
		}
	}

	@Override
	public void onDisConnect()
	{
	}

	private SDRequestCallBack<Init_indexActModel> mListener = new SDRequestCallBack<Init_indexActModel>()
	{

		@Override
		public void onSuccess(ResponseInfo<String> responseInfo)
		{
			mIsInitSuccess = true;
		}

		@Override
		public void onStart()
		{

		}

		@Override
		public void onFinish()
		{

		}

		@Override
		public void onFailure(HttpException error, String msg)
		{

		}
	};

}

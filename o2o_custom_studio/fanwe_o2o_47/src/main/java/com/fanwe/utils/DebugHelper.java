package com.fanwe.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.fanwe.InitAdvsMultiActivity;
import com.fanwe.app.App;
import com.fanwe.constant.ApkConstant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.dialog.SDDialogInput;
import com.fanwe.library.dialog.SDDialogInput.SDDialogInputListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.RequestModel;
import com.fanwe.umeng.UmengSocialManager;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.umeng.socialize.sensor.strategy.UMSensorStrategy;

public class DebugHelper implements UMSensorStrategy
{

	private static final int MAX_URL = 50;

	private static DebugHelper mInstance;
	private Dialog mDialog;
	private boolean mDebug = false;
	private String mStrOriginalUrl;

	/** 用于保存app运行时候请求的url */
	private Queue<String> mQueueUrl = new ArrayBlockingQueue<String>(MAX_URL);

	private DebugHelper()
	{
		mDebug = ApkConstant.DEBUG;
	}

	public static DebugHelper getInstance()
	{
		if (mInstance == null)
		{
			synchronized (DebugHelper.class)
			{
				if (mInstance == null)
				{
					mInstance = new DebugHelper();
				}
			}
		}
		return mInstance;
	}

	public void offerUrl(String ctl, String act, String url)
	{
		if (mDebug)
		{
			if (mQueueUrl.size() >= MAX_URL)
			{
				mQueueUrl.poll();
			}
			url = ctl + "," + act + " " + url;
			mQueueUrl.offer(url);
		}
	}

	public String pollUrl()
	{
		return mQueueUrl.poll();
	}

	public Queue<String> getQueueUrl()
	{
		return mQueueUrl;
	}

	public void setDebug(boolean debug)
	{
		this.mDebug = debug;
		if (!debug)
		{
			mQueueUrl.clear();
		}
	}

	public void registerShake(Activity activity)
	{
		if (mDebug)
		{
			if (activity != null)
			{
				UmengSocialManager.getUMShake().registerShake(activity, this);
			}
		}
	}

	@Override
	public void shakeComplete()
	{
		if (!SDPackageUtil.isBackground())
		{
			if (mDialog != null)
			{
				mDialog.dismiss();
				mDialog = null;
			} else
			{
				SDDialogInput dialog = new SDDialogInput();
				dialog.setTextTitle(ApkConstant.SERVER_API_URL_MID).setTextCancel("切换域名").setTextConfirm("查看链接");
				dialog.setmListener(new SDDialogInputListener()
				{

					@Override
					public void onDismiss(SDDialogInput dialog)
					{
					}

					@Override
					public void onClickConfirm(View v, String content, SDDialogInput dialog)
					{
						showUrlDialog();
					}

					@Override
					public void onClickCancel(View v, SDDialogInput dialog)
					{
						String url = dialog.mEtContent.getText().toString();
						validateSite(url);
					}
				});
				dialog.show();
				mDialog = dialog;
			}
		}
	}

	public void showUrlDialog()
	{
		if (!mQueueUrl.isEmpty())
		{
			final String[] arrString = new String[mQueueUrl.size()];
			mQueueUrl.toArray(arrString);
			List<String> listUrl = Arrays.asList(arrString);
			Collections.reverse(listUrl);
			listUrl.toArray(arrString);

			SDDialogMenu dialog = new SDDialogMenu();
			dialog.setItems(arrString);
			dialog.setmListener(new SDDialogMenuListener()
			{

				@Override
				public void onItemClick(View v, int index, SDDialogMenu dialog)
				{
					SDOtherUtil.copyText(arrString[index]);
					SDToast.showToast("链接已经复制到剪切板");
				}

				@Override
				public void onDismiss(SDDialogMenu dialog)
				{
				}

				@Override
				public void onCancelClick(View v, SDDialogMenu dialog)
				{
				}
			});
			dialog.showBottom();
		}
	}

	private void saveOriginalUrl()
	{
		mStrOriginalUrl = ApkConstant.SERVER_API_URL_MID;
	}

	private void restoreOriginalUrl()
	{
		ApkConstant.SERVER_API_URL_MID = mStrOriginalUrl;
	}

	private void validateSite(final String url)
	{
		if (TextUtils.isEmpty(url))
		{
			return;
		}

		saveOriginalUrl();
		ApkConstant.SERVER_API_URL_MID = url;

		RequestModel model = new RequestModel();
		model.putCtl("init");
		InterfaceServer.getInstance().requestInterface(model, new RequestCallBack<String>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("正在验证地址是否可用");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				Activity activity = SDActivityManager.getInstance().getLastActivity();
				if (activity != null)
				{
					App.getApplication().clearAppsLocalUserModel();
					SDActivityManager.getInstance().finishAllActivityExcept(activity);
					Intent intent = new Intent(activity, InitAdvsMultiActivity.class);
					activity.startActivity(intent);
					activity.finish();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				restoreOriginalUrl();
				SDToast.showToast("地址不可用");
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}
		});
	}

}

package com.fanwe.work;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.fanwe.MyCaptureActivity;
import com.fanwe.common.CommonInterface;
import com.fanwe.constant.Constant.IndexType;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.handler.OnActivityResultHandler;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.AdvsDataModel;
import com.fanwe.model.Mobile_qrcode_indexActModel;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

public class ScanResultHandler extends OnActivityResultHandler
{

	private static final int REQUEST_CODE = 100;
	private static final int RESULT_CODE = MyCaptureActivity.RESULT_CODE_SCAN_SUCCESS;
	private static final String EXTRA_RESULT_SUCCESS_STRING = MyCaptureActivity.EXTRA_RESULT_SUCCESS_STRING;
	private int mRequestCode = REQUEST_CODE;

	private ScanResultDealerListener mListener;

	public void setmListener(ScanResultDealerListener mListener)
	{
		this.mListener = mListener;
	}

	public ScanResultHandler(Fragment mFragment)
	{
		super(mFragment);
	}

	public ScanResultHandler(FragmentActivity mActivity)
	{
		super(mActivity);
	}

	public void startScan(Intent intent)
	{
		startActivityForResult(intent, mRequestCode);
	}

	public void startScan(Intent intent, int requestCode)
	{
		this.mRequestCode = requestCode;
		startActivityForResult(intent, mRequestCode);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == mRequestCode)
		{
			if (resultCode == RESULT_CODE)
			{
				String scanResult = data.getStringExtra(EXTRA_RESULT_SUCCESS_STRING);
				if (mListener != null)
				{
					if (mListener.onResult(scanResult))
					{
						return;
					}
				}
				requestScanResult(scanResult);
			}
		}
	}

	private void requestScanResult(final String scanResult)
	{
		if (TextUtils.isEmpty(scanResult))
		{
			SDToast.showToast("扫描结果为空");
			return;
		}

		CommonInterface.requestScanResult(scanResult, new SDRequestCallBack<Mobile_qrcode_indexActModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					int type = actModel.getType();
					AdvsDataModel data = actModel.getParams();
					if (type == -1) // 直接显示扫描内容
					{
						SDDialogConfirm dialog = new SDDialogConfirm();
						dialog.setTextCancel(null);
						dialog.setTextContent(scanResult);
						dialog.show();
						return;
					}
					if (type == IndexType.URL)
					{
						if (data == null)
						{
							data = new AdvsDataModel();
						}
						data.setUrl(scanResult);
					}

					Intent intent = AppRuntimeWorker.createIntentByType(type, data, false);
					if (intent != null)
					{
						try
						{
							startActivity(intent);
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					} else
					{
						SDToast.showToast("未知的类型：" + type);
					}
				}
			}

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("正在解释验证码...");
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				SDToast.showToast("解释失败");
			}
		});
	}

	public interface ScanResultDealerListener
	{
		public boolean onResult(String result);
	}

}

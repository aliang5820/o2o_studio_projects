package com.fanwe.common;

import android.text.TextUtils;

import com.fanwe.application.App;
import com.fanwe.config.AppConfig;
import com.fanwe.dialog.InputImageCodeDialog;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.http.listener.proxy.SDRequestCallBackProxy;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Sms_send_sms_codeActModel;
import com.lidroid.xutils.http.ResponseInfo;

/**
 * 
 * @author yhz
 * @create time 2014-7-31
 */
public class CommonInterfaceRequestModel
{
	public static RequestModel initBizUserCtlDoLoginActRquestModel()
	{
		LocalUserModel localUserModel = App.getApp().getmLocalUser();
		if (localUserModel != null)
		{
			RequestModel model = new RequestModel();
			model.putCtlAct("biz_user", "dologin");
			model.put("account_name", localUserModel.getAccount_name());
			model.put("account_password", localUserModel.getAccount_password());
			return model;
		}
		return null;
	}

	/**
	 * 请求验证码
	 * 
	 * @param mobile
	 *            手机号
	 * @param unique
	 *            0:不检测 ，1:要检测是否被抢占(绑定手机)， 2:要检测是否存在(修改密码)
	 */
	public static void requestValidateCode(String mobile, int unique, SDRequestCallBack<Sms_send_sms_codeActModel> listener)
	{
		String imageCode = AppConfig.getImageCode();
		requestValidateCode(mobile, unique, imageCode, listener);
	}

	private static void requestValidateCode(String mobile, int unique, final String imageCode, SDRequestCallBack<Sms_send_sms_codeActModel> listener)
	{
		RequestModel requestModel = new RequestModel();
		requestModel.putCtl("sms");
		requestModel.putAct("send_sms_code");
		requestModel.put("mobile", mobile);
		requestModel.put("unique", unique);
		requestModel.put("verify_code", imageCode);
		InterfaceServer.getInstance().requestInterface(requestModel, new SDRequestCallBackProxy<Sms_send_sms_codeActModel>(listener)
		{
			@Override
			public void onStart()
			{
				if (getOriginalCallBack() != null)
				{
					getOriginalCallBack().showToast = false;
				}
				super.onStart();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (getOriginalCallBack() != null)
				{
					getOriginalCallBack().showToast = true;
				}
				switch (actModel.getStatus())
				{
				case -1:
					InputImageCodeDialog dialog = new InputImageCodeDialog();
					dialog.setImage(actModel.getVerify_image());
					dialog.show();
					if (!TextUtils.isEmpty(imageCode))
					{
						if (getOriginalCallBack() != null)
						{
							getOriginalCallBack().showToast();
						}
					}
					break;
				case 1:
					AppConfig.setImageCode("");
					if (getOriginalCallBack() != null)
					{
						getOriginalCallBack().showToast();
					}
					break;

				default:
					if (getOriginalCallBack() != null)
					{
						getOriginalCallBack().showToast();
					}
					break;
				}
				super.onSuccess(responseInfo);
			}
		});
	}
}

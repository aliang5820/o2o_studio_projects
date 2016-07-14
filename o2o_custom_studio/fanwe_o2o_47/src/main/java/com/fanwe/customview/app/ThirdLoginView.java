package com.fanwe.customview.app;

import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.User_infoModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.umeng.UmengSocialManager;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;

public class ThirdLoginView extends SDAppView
{
	private LinearLayout mLlThridLogin;

	private LinearLayout mLlLoginSina;
	private LinearLayout mLlLoginQQ;
	private LinearLayout mLlLoginWx;

	public ThirdLoginView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public ThirdLoginView(Context context)
	{
		super(context);
		init();
	}

	@Override
	protected void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.view_third_login, this, true);

		mLlThridLogin = (LinearLayout) findViewById(R.id.ll_third_login);

		mLlLoginSina = (LinearLayout) findViewById(R.id.ll_login_sina);
		mLlLoginQQ = (LinearLayout) findViewById(R.id.ll_login_qq);
		mLlLoginWx = (LinearLayout) findViewById(R.id.ll_login_wx);

		initViewState();
		register();
	}

	private void initViewState()
	{
		String sinaAppKey = AppRuntimeWorker.getSina_app_key();
		String qqAppKey = AppRuntimeWorker.getQq_app_key();
		if (TextUtils.isEmpty(sinaAppKey) && TextUtils.isEmpty(qqAppKey))
		{
			SDViewUtil.hide(mLlThridLogin);
		} else
		{
			SDViewUtil.show(mLlThridLogin);

			if (TextUtils.isEmpty(sinaAppKey))
			{
				SDViewUtil.hide(mLlLoginSina);
			} else
			{
				SDViewUtil.show(mLlLoginSina);
			}

			if (TextUtils.isEmpty(qqAppKey))
			{
				SDViewUtil.hide(mLlLoginQQ);
			} else
			{
				SDViewUtil.show(mLlLoginQQ);
			}
		}
	}

	private void register()
	{
		mLlLoginSina.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickLoginSina();
			}
		});

		mLlLoginQQ.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickLoginQQ();
			}
		});

		mLlLoginWx.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickLoginWx();
			}
		});
	}

	/**
	 * 点击微信登录，先获取个人资料
	 */
	private void clickLoginWx()
	{
		UmengSocialManager.doOauthVerify(getActivity(), SHARE_MEDIA.WEIXIN, new UMAuthListener()
		{

			@Override
			public void onStart(SHARE_MEDIA arg0)
			{
			}

			@Override
			public void onError(SocializeException arg0, SHARE_MEDIA arg1)
			{
				SDToast.showToast("授权失败");
			}

			@Override
			public void onComplete(Bundle bundle, SHARE_MEDIA arg1)
			{
				final String uid = bundle.getString("uid");
				final String access_token = bundle.getString("access_token");
				UmengSocialManager.getPlatformInfo(getActivity(), SHARE_MEDIA.WEIXIN, new UMDataListener()
				{

					@Override
					public void onStart()
					{
						SDDialogManager.showProgressDialog("请稍候");
					}

					@Override
					public void onComplete(int code, Map<String, Object> data)
					{
						SDDialogManager.dismissProgressDialog();
						if (code == 200 && data != null)
						{
							String nickName = String.valueOf(data.get("nickname"));
							requestLoginWx();
						} else
						{
							SDToast.showToast("获取用户信息失败");
						}
					}

				});
			}

			@Override
			public void onCancel(SHARE_MEDIA arg0)
			{
			}
		});
	}

	private void requestLoginWx()
	{

	}

	/**
	 * 点击qq登录,先获取个人资料
	 */
	private void clickLoginQQ()
	{
		UmengSocialManager.doOauthVerify(getActivity(), SHARE_MEDIA.QQ, new UMAuthListener()
		{
			@Override
			public void onCancel(SHARE_MEDIA arg0)
			{
			}

			@Override
			public void onComplete(Bundle bundle, SHARE_MEDIA arg1)
			{
				final String openId = bundle.getString("openid");
				final String access_token = bundle.getString("access_token");
				UmengSocialManager.getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, new UMDataListener()
				{
					@Override
					public void onStart()
					{
						SDDialogManager.showProgressDialog("请稍候");
					}

					@Override
					public void onComplete(int code, Map<String, Object> data)
					{
						SDDialogManager.dismissProgressDialog();
						if (code == 200 && data != null)
						{
							String nickName = String.valueOf(data.get("screen_name"));
							requestQQLogin(openId, access_token, nickName);
						} else
						{
							SDToast.showToast("获取用户信息失败");
						}
					}
				});
			}

			@Override
			public void onError(SocializeException arg0, SHARE_MEDIA arg1)
			{
				SDToast.showToast("授权失败");
			}

			@Override
			public void onStart(SHARE_MEDIA arg0)
			{
			}
		});

	}

	protected void requestQQLogin(String openId, String access_token, String nickname)
	{
		RequestModel model = new RequestModel();
		model.putCtl("synclogin");
		model.put("login_type", "Qq");
		model.put("qqv2_id", openId);
		model.put("access_token", access_token);
		model.put("nickname", nickname);
		SDRequestCallBack<User_infoModel> handler = new SDRequestCallBack<User_infoModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					LocalUserModel.dealLoginSuccess(actModel, true);
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
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	/**
	 * 点击新浪微博登录,先获取个人资料，然后登录
	 */
	private void clickLoginSina()
	{
		UmengSocialManager.doOauthVerify(getActivity(), SHARE_MEDIA.SINA, new UMAuthListener()
		{
			@Override
			public void onCancel(SHARE_MEDIA arg0)
			{
			}

			@Override
			public void onComplete(Bundle bundle, SHARE_MEDIA arg1)
			{
				final String uid = bundle.getString("uid");
				final String access_token = bundle.getString("access_secret");
				UmengSocialManager.getPlatformInfo(getActivity(), SHARE_MEDIA.SINA, new UMDataListener()
				{
					@Override
					public void onStart()
					{
						SDDialogManager.showProgressDialog("请稍候");
					}

					@Override
					public void onComplete(int code, Map<String, Object> data)
					{
						SDDialogManager.dismissProgressDialog();
						if (code == 200 && data != null)
						{
							String nickName = String.valueOf(data.get("screen_name"));
							requestSinaLogin(uid, access_token, nickName);
						} else
						{
							SDToast.showToast("获取用户信息失败");
						}
					}
				});
			}

			@Override
			public void onError(SocializeException arg0, SHARE_MEDIA arg1)
			{
				SDToast.showToast("授权失败");
			}

			@Override
			public void onStart(SHARE_MEDIA arg0)
			{
			}
		});
	}

	protected void requestSinaLogin(String uid, String access_token, String nickname)
	{
		RequestModel model = new RequestModel();
		model.putCtl("synclogin");
		model.put("login_type", "Sina");
		model.put("sina_id", uid);
		model.put("access_token", access_token);
		model.put("nickname", nickname);
		SDRequestCallBack<User_infoModel> handler = new SDRequestCallBack<User_infoModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					LocalUserModel.dealLoginSuccess(actModel, true);
				}
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

}

package com.fanwe.fragment;

import java.io.File;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.MainActivity;
import com.fanwe.NoticeDetailActivity;
import com.fanwe.NoticeListActivity;
import com.fanwe.app.App;
import com.fanwe.constant.ApkConstant;
import com.fanwe.constant.Constant.LoadImageType;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.dao.SettingModelDao;
import com.fanwe.event.EnumEventTag;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.utils.SDActivityUtil;
import com.fanwe.library.utils.SDFileUtil;
import com.fanwe.library.utils.SDHandlerUtil;
import com.fanwe.library.utils.SDIntentUtil;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.Init_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.service.AppUpgradeService;
import com.fanwe.umeng.UmengPushManager;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sunday.eventbus.SDEventManager;

/**
 * 更多fragment
 * 
 * @author js02
 * 
 */
public class MoreFragment extends BaseFragment
{

	@ViewInject(R.id.ll_qrcode)
	private LinearLayout mLl_qrcode; // 扫一扫

	@ViewInject(R.id.cb_load_image_in_mobile_net)
	private CheckBox mCb_load_image_in_mobile_net; // 在移动网络下加载图片

	@ViewInject(R.id.ll_clear_cache)
	private LinearLayout mLl_clear_cache; // 清除缓存

	@ViewInject(R.id.tv_cache_size)
	private TextView mTv_cache_size;

	@ViewInject(R.id.ll_check_update)
	private LinearLayout mLl_check_update; // 检查更新

	@ViewInject(R.id.tv_version)
	private TextView mTv_version;

	@ViewInject(R.id.ll_notice_list)
	private LinearLayout mLl_notice_list; // 公告列表

	@ViewInject(R.id.ll_kf_phone)
	private LinearLayout mLl_kf_phone; // 客服电话

	@ViewInject(R.id.tv_kf_phone)
	private TextView mTv_kf_phone;

	@ViewInject(R.id.ll_kf_email)
	private LinearLayout mLl_kf_email; // 客服邮箱

	@ViewInject(R.id.tv_kf_email)
	private TextView mTv_kf_email;

	@ViewInject(R.id.ll_about_us)
	private LinearLayout mLl_about_us; // 关于我们

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.frag_more);
	}

	@Override
	protected void init()
	{
		initTitle();
		bindData();
		showCacheSize();
		registeClick();
		initViewState();
	}

	private void initViewState()
	{
		Init_indexActModel initActModel = AppRuntimeWorker.getInitActModel();
		if (initActModel != null)
		{
			int isXiaomi = initActModel.getIs_xiaomi();
			if (isXiaomi == 1)
			{
				SDViewUtil.hide(mLl_qrcode);
			} else
			{
				SDViewUtil.show(mLl_qrcode);
			}
		}
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("更多");
		if (getActivity() instanceof MainActivity)
		{
			mTitle.setLeftImageLeft(0);
		} else
		{
			mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
		}
	}

	private void bindData()
	{
		int loadImageInMobileNet = SettingModelDao.getLoadImageType();
		if (loadImageInMobileNet == LoadImageType.ALL)
		{
			mCb_load_image_in_mobile_net.setChecked(true);
			mCb_load_image_in_mobile_net.setText("是");
		} else
		{
			mCb_load_image_in_mobile_net.setChecked(false);
			mCb_load_image_in_mobile_net.setText("否");
		}
		mCb_load_image_in_mobile_net.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{

				if (isChecked)
				{
					SettingModelDao.updateLoadImageType(LoadImageType.ALL);
					mCb_load_image_in_mobile_net.setText("是");
				} else
				{
					SettingModelDao.updateLoadImageType(LoadImageType.ONLY_WIFI);
					mCb_load_image_in_mobile_net.setText("否");
				}
			}
		});

		PackageInfo pi = SDPackageUtil.getCurrentPackageInfo();
		mTv_version.setText(String.valueOf(pi.versionName));

		String kfPhone = AppRuntimeWorker.getKf_phone();
		SDViewBinder.setTextView(mTv_kf_phone, kfPhone);

		String kfEmail = AppRuntimeWorker.getKf_email();
		SDViewBinder.setTextView(mTv_kf_email, kfEmail);

	}

	private void showCacheSize()
	{
		File cacheDir = ImageLoader.getInstance().getDiskCache().getDirectory();
		if (cacheDir != null)
		{
			long cacheSize = SDFileUtil.getFileSize(cacheDir);
			mTv_cache_size.setText(SDFileUtil.formatFileSize(cacheSize));
		}
	}

	private void registeClick()
	{
		mLl_notice_list.setOnClickListener(this);
		mLl_about_us.setOnClickListener(this);
		mLl_qrcode.setOnClickListener(this);
		mLl_clear_cache.setOnClickListener(this);
		mLl_kf_phone.setOnClickListener(this);
		mLl_kf_email.setOnClickListener(this);
		mLl_check_update.setOnClickListener(this);
		mLl_check_update.setOnLongClickListener(new OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View v)
			{
				PackageInfo pi = SDPackageUtil.getCurrentPackageInfo();
				StringBuilder sb = new StringBuilder();
				sb.append("appName=" + SDResourcesUtil.getString(R.string.app_name) + "\r\n");
				sb.append("package=" + pi.packageName + "\r\n");
				sb.append("versionCode=" + pi.versionCode + "\r\n");
				sb.append("versionName=" + pi.versionName + "\r\n");
				sb.append("serverUrl=" + ApkConstant.SERVER_API_URL_MID + "\r\n");
				sb.append("umengKey=" + SDResourcesUtil.getString(R.string.umeng_key) + "\r\n");
				sb.append("umengMessageSecret=" + SDResourcesUtil.getString(R.string.umeng_message_secret) + "\r\n");
				String regId = UmengPushManager.getPushAgent().getRegistrationId();
				sb.append("umengRegId=" + regId + "\r\n");
				sb.append("baiduKey=" + SDResourcesUtil.getString(R.string.baidu_key) + "\r\n");
				sb.append("WeiXinKey=" + AppRuntimeWorker.getWx_app_key() + "\r\n");
				sb.append("QQKey=" + AppRuntimeWorker.getQq_app_key() + "\r\n");
				sb.append("SinaKey=" + AppRuntimeWorker.getSina_app_key() + "\r\n");
				new SDDialogConfirm().setTextContent(sb.toString()).show();
				return false;
			}
		});
	}

	@Override
	public void onClick(View v)
	{
		if (v == mLl_qrcode)
		{
			clickQrcode();
		} else if (v == mLl_clear_cache)
		{
			clickClearCache();
		} else if (v == mLl_check_update)
		{
			clickCheckUpdate();
		} else if (v == mLl_notice_list)
		{
			clickNoticeList();
		} else if (v == mLl_kf_phone)
		{
			clickKfPhone();
		} else if (v == mLl_kf_email)
		{
			clickKfEmail();
		} else if (v == mLl_about_us)
		{
			clickAboutUs();
		}
	}

	/**
	 * 客服邮箱
	 */
	private void clickKfEmail()
	{

	}

	/**
	 * 客服电话
	 */
	private void clickKfPhone()
	{
		String kfPhone = AppRuntimeWorker.getKf_phone();
		if (!TextUtils.isEmpty(kfPhone))
		{
			Intent intent = SDIntentUtil.getIntentCallPhone(kfPhone);
			SDActivityUtil.startActivity(this, intent);
		} else
		{
			SDToast.showToast("未找到客服电话");
		}
	}

	/**
	 * 扫一扫
	 */
	private void clickQrcode()
	{
		SDEventManager.post(MainActivity.class, EnumEventTag.START_SCAN_QRCODE.ordinal());
	}

	/**
	 * 关于
	 */
	private void clickAboutUs()
	{
		int noticeId = AppRuntimeWorker.getAbout_info();
		if (noticeId > 0)
		{
			Intent intent = new Intent(getActivity(), NoticeDetailActivity.class);
			intent.putExtra(NoticeDetailActivity.EXTRA_NOTICE_ID, noticeId);
			startActivity(intent);
		} else
		{
			SDToast.showToast("未找到关于我们ID");
		}
	}

	/**
	 * 公告列表
	 */
	private void clickNoticeList()
	{
		startActivity(new Intent(App.getApplication(), NoticeListActivity.class));
	}

	private void clickCheckUpdate()
	{
		Intent intent = new Intent(App.getApplication(), AppUpgradeService.class);
		intent.putExtra(AppUpgradeService.EXTRA_SERVICE_START_TYPE, 1);
		getActivity().startService(intent);
	}

	/**
	 * 清除缓存
	 */
	private void clickClearCache()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				ImageLoader.getInstance().clearDiskCache();
				SDHandlerUtil.runOnUiThread(new Runnable()
				{
					public void run()
					{
						mTv_cache_size.setText("0.00B");
						SDToast.showToast("清除完毕");
					}
				});
			}
		}).start();
	}

	@Override
	public void onHiddenChanged(boolean hidden)
	{
		if (!hidden)
		{
			showCacheSize();
		}
		super.onHiddenChanged(hidden);
	}
}
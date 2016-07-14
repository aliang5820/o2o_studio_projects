package com.fanwe;

import android.os.Bundle;
import android.text.TextUtils;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.fragment.AppWebViewFragment;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.NoticeModel;
import com.fanwe.model.Notice_detailActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.http.ResponseInfo;

/**
 * 公告内容
 * 
 * @author js02
 * 
 */
public class NoticeDetailActivity extends BaseActivity
{

	/** 公告id(int) */
	public static final String EXTRA_NOTICE_ID = "extra_notice_id";

	private AppWebViewFragment mFragWebview;
	private int mNoticeId;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_notice_detail);
		init();
	}

	private void init()
	{
		getIntentData();
		initTitle();
		requestData();
	}

	private void getIntentData()
	{
		mNoticeId = getIntent().getIntExtra(EXTRA_NOTICE_ID, 0);
		if (mNoticeId <= 0)
		{
			SDToast.showToast("id 为空");
			finish();
		}
	}

	private void requestData()
	{
		RequestModel model = new RequestModel();
		model.putCtl("notice");
		model.putAct("detail");
		model.put("id", mNoticeId);
		SDRequestCallBack<Notice_detailActModel> handler = new SDRequestCallBack<Notice_detailActModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候...");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					addWebViewFragment(actModel);
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

	private void addWebViewFragment(Notice_detailActModel actModel)
	{
		NoticeModel model = actModel.getResult();
		if (model == null)
		{
			return;
		}

		String content = model.getContent();
		String pageTitle = actModel.getPage_title();
		if (!TextUtils.isEmpty(content))
		{
			mFragWebview = new AppWebViewFragment();
			mFragWebview.setHtmlContent(content);
			getSDFragmentManager().replace(R.id.act_news_detail_fl_content, mFragWebview);
		}
		if (!TextUtils.isEmpty(pageTitle))
		{
			mTitle.setMiddleTextTop(pageTitle);
		}
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("公告内容");
	}

}
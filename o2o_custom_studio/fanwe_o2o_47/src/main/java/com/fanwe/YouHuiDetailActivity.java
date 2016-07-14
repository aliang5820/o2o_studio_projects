package com.fanwe;

import android.content.Intent;
import android.os.Bundle;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.customview.SDStickyScrollView;
import com.fanwe.event.EnumEventTag;
import com.fanwe.fragment.YouhuiDetailCommentFragment;
import com.fanwe.fragment.YouhuiDetailHtmlDetailFragment;
import com.fanwe.fragment.YouhuiDetailNoticeFragment;
import com.fanwe.fragment.YouhuiDetailOtherMerchantFragment;
import com.fanwe.fragment.YouhuiDetailTopFragment;
import com.fanwe.fragment.YouhuiDetailTopFragment.YouhuiDetailTopFragmentListener;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.customview.StickyScrollView;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Youhui_indexActModel;
import com.fanwe.model.Youhui_infoModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.umeng.UmengSocialManager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

public class YouHuiDetailActivity extends BaseActivity
{

	/** 优惠id (int) */
	public static final String EXTRA_YOUHUI_ID = "extra_youhui_id";

	@ViewInject(R.id.ssv_scroll)
	private SDStickyScrollView mScrollView;

	private YouhuiDetailTopFragment mFragTop;
	private YouhuiDetailOtherMerchantFragment mFragOtherMerchant;
	private YouhuiDetailHtmlDetailFragment mFragHtmlDetail;
	private YouhuiDetailNoticeFragment mFragNotice;
	private YouhuiDetailCommentFragment mFragComment;

	private int mId;

	private Youhui_indexActModel mActModel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_youhui_detail);
		init();
	}

	private void init()
	{
		getIntentData();
		if (mId <= 0)
		{
			SDToast.showToast("id为空");
			finish();
			return;
		}
		initTitle();
		initScrollView();
	}

	private void initScrollView()
	{
		mScrollView.setMode(Mode.PULL_FROM_START);
		mScrollView.setOnRefreshListener(new OnRefreshListener2<StickyScrollView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<StickyScrollView> refreshView)
			{
				requestYouhuiDetail();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<StickyScrollView> refreshView)
			{

			}
		});
		mScrollView.setRefreshing();
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("优惠券详情");

		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setImageLeft(R.drawable.ic_tuan_detail_share);
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		clickShare();
	}

	/**
	 * 分享
	 */
	private void clickShare()
	{
		if (mActModel == null)
		{
			SDToast.showToast("未找到可分享内容");
			return;
		}

		Youhui_infoModel infoModel = mActModel.getYouhui_info();
		if (infoModel == null)
		{
			SDToast.showToast("未找到可分享内容");
			return;
		}

		String content = infoModel.getName() + infoModel.getShare_url();
		String imageUrl = infoModel.getIcon();
		String clickUrl = infoModel.getShare_url();

		UmengSocialManager.openShare("分享", content, imageUrl, clickUrl, this, null);
	}

	private void getIntentData()
	{
		Intent intent = getIntent();
		mId = intent.getIntExtra(EXTRA_YOUHUI_ID, -1);
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		setIntent(intent);
		init();
		super.onNewIntent(intent);
	}

	/**
	 * 加载优惠明细
	 */
	private void requestYouhuiDetail()
	{
		RequestModel model = new RequestModel();
		model.putCtl("youhui");
		model.putUser();
		model.put("data_id", mId);
		model.putLocation();
		model.setIsNeedCheckLoginState(false);
		SDRequestCallBack<Youhui_indexActModel> handler = new SDRequestCallBack<Youhui_indexActModel>()
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
					mActModel = actModel;
					addFragments(actModel);
				}
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				mScrollView.onRefreshComplete();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	/**
	 * 添加fragment
	 * 
	 * @param model
	 */
	protected void addFragments(Youhui_indexActModel model)
	{
		if (model == null)
		{
			return;
		}

		mFragTop = new YouhuiDetailTopFragment();
		mFragTop.setmYouhuiModel(model);
		mFragTop.setListener(new YouhuiDetailTopFragmentListener()
		{
			@Override
			public void onRefresh()
			{
				requestYouhuiDetail();
			}
		});
		getSDFragmentManager().replace(R.id.act_youhui_detail_fl_top, mFragTop);

		mFragOtherMerchant = new YouhuiDetailOtherMerchantFragment();
		mFragOtherMerchant.setmYouhuiModel(model);
		getSDFragmentManager().replace(R.id.act_youhui_detail_fl_other_merchant, mFragOtherMerchant);

		mFragHtmlDetail = new YouhuiDetailHtmlDetailFragment();
		mFragHtmlDetail.setmYouhuiModel(model);
		getSDFragmentManager().replace(R.id.act_youhui_detail_fl_youhui_detail, mFragHtmlDetail);

		mFragNotice = new YouhuiDetailNoticeFragment();
		mFragNotice.setmYouhuiModel(model);
		getSDFragmentManager().replace(R.id.act_youhui_detail_fl_youhui_notice, mFragNotice);

		mFragComment = new YouhuiDetailCommentFragment();
		mFragComment.setmYouhuiModel(model);
		getSDFragmentManager().replace(R.id.act_youhui_detail_fl_youhui_comments, mFragComment);
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case COMMENT_SUCCESS:
			setmIsNeedRefreshOnResume(true);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onNeedRefreshOnResume()
	{
		requestYouhuiDetail();
		super.onNeedRefreshOnResume();
	}

}
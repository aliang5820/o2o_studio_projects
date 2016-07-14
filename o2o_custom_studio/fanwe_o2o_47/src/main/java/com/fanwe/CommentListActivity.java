package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fanwe.adapter.TuanDetailCommentAdapter;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.CommentModel;
import com.fanwe.model.Dp_indexActModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

/**
 * 评论列表
 * 
 * @author js02
 * 
 */
public class CommentListActivity extends BaseActivity
{

	/** id (int) */
	public static final String EXTRA_ID = "extra_id";

	/** type 评论类型,传进来的值要引用Constant.CommentType的属性 (int) */
	public static final String EXTRA_TYPE = "extra_type";

	@ViewInject(R.id.act_tuan_comment_list_ptrlv_comments)
	private PullToRefreshListView mPtrlvComment;

	@ViewInject(R.id.act_tuan_comment_tv_buy_dp_avg)
	private TextView mTvByDpAvg;

	@ViewInject(R.id.act_tuan_comment_tv_buy_dp_count)
	private TextView mTvByDpCount;

	@ViewInject(R.id.act_tuan_comment_rb_star)
	private RatingBar mRbStar;

	@ViewInject(R.id.act_tuan_comment_pb_start5)
	private ProgressBar mPbStar5;

	@ViewInject(R.id.act_tuan_comment_tv_start5)
	private TextView mTvStar5;

	@ViewInject(R.id.act_tuan_comment_pb_start4)
	private ProgressBar mPbStar4;

	@ViewInject(R.id.act_tuan_comment_tv_start4)
	private TextView mTvStar4;

	@ViewInject(R.id.act_tuan_comment_pb_start3)
	private ProgressBar mPbStar3;

	@ViewInject(R.id.act_tuan_comment_tv_start3)
	private TextView mTvStar3;

	@ViewInject(R.id.act_tuan_comment_pb_start2)
	private ProgressBar mPbStar2;

	@ViewInject(R.id.act_tuan_comment_tv_start2)
	private TextView mTvStar2;

	@ViewInject(R.id.act_tuan_comment_pb_start1)
	private ProgressBar mPbStar1;

	@ViewInject(R.id.act_tuan_comment_tv_start1)
	private TextView mTvStar1;

	@ViewInject(R.id.act_tuan_comment_btn_publish)
	private Button mBtnPublish;

	private List<CommentModel> mListModel = new ArrayList<CommentModel>();
	private TuanDetailCommentAdapter mAdapter;

	private PageModel mPage = new PageModel();

	private int mId;
	private String mStrType;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_comment_list);
		init();
	}

	private void init()
	{
		getIntentData();
		initTitle();
		bindDefaultData();
		initPullToRefreshListView();
	}

	private void bindDefaultData()
	{
		mAdapter = new TuanDetailCommentAdapter(mListModel, this);
		mPtrlvComment.setAdapter(mAdapter);
	}

	private void getIntentData()
	{
		mId = getIntent().getIntExtra(EXTRA_ID, 0);
		mStrType = getIntent().getStringExtra(EXTRA_TYPE);

		if (mId <= 0)
		{
			SDToast.showToast("id为空");
			finish();
		}
		if (TextUtils.isEmpty(mStrType))
		{
			SDToast.showToast("评论类型为空");
			finish();
		}
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		setIntent(intent);
		init();
		super.onNewIntent(intent);
	}

	private void initPullToRefreshListView()
	{
		mPtrlvComment.setMode(Mode.BOTH);
		mPtrlvComment.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				mPage.resetPage();
				requestComments(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				if (!mPage.increment())
				{
					SDToast.showToast("没有更多内容");
					mPtrlvComment.onRefreshComplete();
				} else
				{
					requestComments(true);
				}
			}
		});
		mPtrlvComment.setRefreshing();
	}

	protected void requestComments(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.setIsNeedCheckLoginState(false);
		model.putCtl("dp");
		model.put("data_id", mId);
		model.put("type", mStrType);
		model.putPage(mPage.getPage());
		model.putUser();
		SDRequestCallBack<Dp_indexActModel> handler = new SDRequestCallBack<Dp_indexActModel>()
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
					mPage.update(actModel.getPage());
					if (actModel != null)
					{
						bindData(actModel);
					}
					SDViewUtil.updateAdapterByList(mListModel, actModel.getItem(), mAdapter, isLoadMore, "未找到评论", "没有更多评论了");
				}
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				mPtrlvComment.onRefreshComplete();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	/**
	 * 评分栏
	 * 
	 * @param actModel
	 */
	private void bindData(Dp_indexActModel actModel)
	{
		String strTitle = actModel.getPage_title();
		final String strName = actModel.getName();
		if (!TextUtils.isEmpty(strTitle))
		{
			mTitle.setMiddleTextTop(strTitle);
		}

		if (AppRuntimeWorker.isLogin())
		{
			if (actModel.getAllow_dp() == 1)
			{
				mBtnPublish.setVisibility(View.VISIBLE);
			} else
			{
				mBtnPublish.setVisibility(View.GONE);
			}
		} else
		{
			mBtnPublish.setVisibility(View.VISIBLE);
		}

		mBtnPublish.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				if (!AppRuntimeWorker.isLogin())
				{
					startActivity(new Intent(CommentListActivity.this, LoginActivity.class));
				} else
				{
					Intent i = new Intent(CommentListActivity.this, AddCommentActivity.class);
					i.putExtra(AddCommentActivity.EXTRA_ID, mId);
					i.putExtra(AddCommentActivity.EXTRA_TYPE, mStrType);
					if (!TextUtils.isEmpty(strName))
					{
						i.putExtra(AddCommentActivity.EXTRA_NAME, strName);
					}
					startActivity(i);
				}
			}
		});

		mTvByDpAvg.setText(String.valueOf(actModel.getBuy_dp_avg()));
		mTvByDpCount.setText(String.valueOf(actModel.getMessage_count()));

		float ratingStar = SDTypeParseUtil.getFloat(actModel.getBuy_dp_avg());
		mRbStar.setRating(ratingStar);

		mTvStar5.setText(String.valueOf(actModel.getStar_5()));
		mTvStar4.setText(String.valueOf(actModel.getStar_4()));
		mTvStar3.setText(String.valueOf(actModel.getStar_3()));
		mTvStar2.setText(String.valueOf(actModel.getStar_2()));
		mTvStar1.setText(String.valueOf(actModel.getStar_1()));

		mPbStar5.setProgress(actModel.getStar_dp_width_5());
		mPbStar4.setProgress(actModel.getStar_dp_width_4());
		mPbStar3.setProgress(actModel.getStar_dp_width_3());
		mPbStar2.setProgress(actModel.getStar_dp_width_2());
		mPbStar1.setProgress(actModel.getStar_dp_width_1());

	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("评论列表");
	}

	@Override
	protected void onNeedRefreshOnResume()
	{
		mPtrlvComment.setRefreshing();
		super.onNeedRefreshOnResume();
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		// TODO Auto-generated method stub
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case LOGIN_SUCCESS:
			setmIsNeedRefreshOnResume(true);
			break;
		case COMMENT_SUCCESS:
			setmIsNeedRefreshOnResume(true);
			break;

		default:
			break;
		}
	}

}
package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.fanwe.adapter.DistributionRecommendAdapter;
import com.fanwe.adapter.DistributionRecommendAdapter.DistributionRecommendAdapterListener;
import com.fanwe.common.CommonInterface;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.DistributionRecommendModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.Uc_fxinvite_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 查看分销用户推荐的人
 * 
 * @author Administrator
 * 
 */
public class CheckDistributionRecommendActivity extends BaseActivity
{
	/** 用户id(int) */
	public static final String EXTRA_ID = "extra_id";
	/** 用户名(String) */
	public static final String EXTRA_USER_NAME = "extra_user_name";

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlv_content;

	private DistributionRecommendAdapter mAdapter;
	private List<DistributionRecommendModel> mListModel = new ArrayList<DistributionRecommendModel>();

	private int mUserId;
	private String mStrUsername;

	private PageModel mPage = new PageModel();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_check_distribution_recommend);
		init();
	}

	private void init()
	{
		getIntentData();
		initTitle();
		initPullToRefreshListView();
	}

	private void initTitle()
	{
		String title = "查看推荐人";
		if (!isEmpty(mStrUsername))
		{
			title = mStrUsername + "推荐的人";
		}
		mTitle.setMiddleTextTop(title);
	}

	private void getIntentData()
	{
		mUserId = getIntent().getIntExtra(EXTRA_ID, -1);
		mStrUsername = getIntent().getStringExtra(EXTRA_USER_NAME);

		if (mUserId <= 0)
		{
			SDToast.showToast("id为空");
		}
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		setIntent(intent);
		reset();
		init();
		super.onNewIntent(intent);
	}

	private void reset()
	{
		mListModel.clear();
		if (mAdapter != null)
		{
			mAdapter.notifyDataSetChanged();
			mAdapter = null;
		}
	}

	private void initPullToRefreshListView()
	{
		mPtrlv_content.setMode(Mode.BOTH);
		mPtrlv_content.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				mPage.resetPage();
				requestData(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				if (mPage.increment())
				{
					requestData(true);
				} else
				{
					SDToast.showToast("未找到更多数据");
					mPtrlv_content.onRefreshComplete();
				}
			}
		});
		mPtrlv_content.setRefreshing();
	}

	protected void requestData(final boolean isLoadMore)
	{
		CommonInterface.requestDistributionRecommend(mPage.getPage(), mUserId, new SDRequestCallBack<Uc_fxinvite_indexActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mPage.update(actModel.getPage());

					if (mAdapter == null)
					{
						boolean showCheckBtn = true;
						if (actModel.getUser_temid() > 0)
						{
							showCheckBtn = false;
						}
						mAdapter = new DistributionRecommendAdapter(mListModel, showCheckBtn, mActivity);
						mAdapter.setListener(new DistributionRecommendAdapterListener()
						{

							@Override
							public void onCheckRecommend(DistributionRecommendModel model)
							{
								Intent intent = new Intent(getApplicationContext(), CheckDistributionRecommendActivity.class);
								intent.putExtra(EXTRA_ID, model.getId());
								intent.putExtra(EXTRA_USER_NAME, model.getUser_name());
								startActivity(intent);
							}
						});
						mPtrlv_content.setAdapter(mAdapter);
					}
					SDViewUtil.updateAdapterByList(mListModel, actModel.getList(), mAdapter, isLoadMore);
				}
			}

			@Override
			public void onStart()
			{

			}

			@Override
			public void onFinish()
			{
				mPtrlv_content.onRefreshComplete();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{

			}
		});
	}

}

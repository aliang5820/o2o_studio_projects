package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fanwe.adapter.DistributionMarketAdapter;
import com.fanwe.adapter.DistributionMarketCatePageAdapter;
import com.fanwe.adapter.DistributionMarketCatePageAdapter.OnClickCateItemListener;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.customview.app.DistributionMarketCateView;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.DistributionGoodsModel;
import com.fanwe.model.DistributionMarketCateModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_fx_deal_fxActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

/**
 * 分销市场
 * 
 * @author Administrator
 * 
 */
public class DistributionMarketFragment extends BaseFragment
{

	/** 要搜索的商品的id (int) */
	public static final String EXTRA_ID = "extra_id";
	/** 商品分类id (int) */
	public static final String EXTRA_CATE_ID = "extra_cate_id";
	/** 要搜索的商品的关键字 (String) */
	public static final String EXTRA_KEY_WORD = "extra_key_word";

	@ViewInject(R.id.et_search)
	private EditText mEt_search;

	@ViewInject(R.id.btn_search)
	private Button mBtn_search;

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlv_content;

	private DistributionMarketCateView mCateView;
	private boolean mNeedBindCate = true;

	private List<DistributionGoodsModel> mListModel = new ArrayList<DistributionGoodsModel>();
	private DistributionMarketAdapter mAdapter;

	private PageModel mPage = new PageModel();

	private String mStrKeyword;
	private int mId;
	private int mCate_id;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.frag_distribution_market);
	}

	@Override
	protected void init()
	{
		getIntentData();
		initTitle();
		registerClick();
		addHeaderView();
		bindDefaultData();
		initPullToRefreshListView();
	}

	private void addHeaderView()
	{
		mCateView = new DistributionMarketCateView(getActivity());
		SDViewUtil.hide(mCateView);
		mPtrlv_content.getRefreshableView().addHeaderView(mCateView);
	}

	private void getIntentData()
	{
		mId = getArguments().getInt(EXTRA_ID);
		mCate_id = getArguments().getInt(EXTRA_CATE_ID);
		mStrKeyword = getArguments().getString(EXTRA_KEY_WORD);

		if (!isEmpty(mStrKeyword))
		{
			mEt_search.setText(mStrKeyword);
		}
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("逛市场");
	}

	private void registerClick()
	{
		mBtn_search.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mStrKeyword = mEt_search.getText().toString();
				requestData(false);
			}
		});
	}

	private void bindDefaultData()
	{
		mAdapter = new DistributionMarketAdapter(mListModel, getActivity());
		mPtrlv_content.setAdapter(mAdapter);
	}

	private void initPullToRefreshListView()
	{
		mPtrlv_content.setMode(Mode.BOTH);
		mPtrlv_content.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				refreshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				if (mPage.increment())
				{
					requestData(true);
				} else
				{
					SDToast.showToast("没有更多数据了");
					mPtrlv_content.onRefreshComplete();
				}
			}
		});
		mPtrlv_content.setRefreshing();
	}

	public void refreshData()
	{
		mPage.resetPage();
		requestData(false);
	}

	protected void requestData(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_fx");
		model.putAct("deal_fx");
		model.put("fx_seach_key", mStrKeyword);
		model.put("id", mId);
		model.put("cate_id", mCate_id);
		model.putPage(mPage.getPage());

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_fx_deal_fxActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mPage.update(actModel.getPage());
					bindData(actModel, isLoadMore);
				}
			}

			@Override
			public void onFinish()
			{
				mPtrlv_content.onRefreshComplete();
			}
		});
	}

	protected void bindData(Uc_fx_deal_fxActModel actModel, boolean isLoadMore)
	{
		if (actModel == null)
		{
			return;
		}

		String title = actModel.getPage_title();
		if (!isEmpty(title))
		{
			mTitle.setMiddleTextTop(title);
		}

		if (mNeedBindCate)
		{
			List<List<DistributionMarketCateModel>> listModel = SDCollectionUtil.splitList(actModel.getCate_list(), 8);
			if (isEmpty(listModel))
			{
				SDViewUtil.hide(mCateView);
			} else
			{
				SDViewUtil.show(mCateView);
				DistributionMarketCatePageAdapter adapter = new DistributionMarketCatePageAdapter(listModel, getActivity());
				adapter.setmListenerOnClickCateItem(new OnClickCateItemListener()
				{

					@Override
					public void onClickItem(int position, View view, DistributionMarketCateModel model)
					{
						mCate_id = model.getId();
						mPtrlv_content.setRefreshing();
					}
				});
				mCateView.mSpv_content.setAdapter(adapter);
				mNeedBindCate = false;
			}
		}

		SDViewUtil.updateAdapterByList(mListModel, actModel.getItem(), mAdapter, isLoadMore);
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case DELETE_DISTRIBUTION_GOODS_SUCCESS:
			refreshData();
			break;
		case ADD_DISTRIBUTION_GOODS_SUCCESS:
			refreshData();
			break;

		default:
			break;
		}
	}

}

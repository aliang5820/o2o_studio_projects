package com.fanwe;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.common.CommonInterface;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.customview.SDStickyScrollView;
import com.fanwe.fragment.DistributionStoreBaseDealFragment;
import com.fanwe.fragment.DistributionStoreGoodsFragment;
import com.fanwe.fragment.DistributionStoreTuanFragment;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.common.SDSelectManager.SDSelectManagerListener;
import com.fanwe.library.customview.StickyScrollView;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.view.SDTabCorner.TabPosition;
import com.fanwe.library.view.SDTabCornerText;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.model.MyDistributionUser_dataModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.Uc_fx_mallActModel;
import com.fanwe.o2o.newo2o.R;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 分销小店
 * 
 * @author Administrator
 * 
 */
public class DistributionStoreActivity extends BaseActivity
{
	@ViewInject(R.id.ssv_all)
	private SDStickyScrollView mSsv_all;

	@ViewInject(R.id.siv_image)
	private ImageView mSiv_image;

	@ViewInject(R.id.iv_user_avatar)
	private CircularImageView miv_user_avatar;

	@ViewInject(R.id.tv_username)
	private TextView mTv_username;

	@ViewInject(R.id.tab_tuan)
	private SDTabCornerText mTab_tuan;

	@ViewInject(R.id.tab_goods)
	private SDTabCornerText mTab_goods;

	private SDSelectViewManager<SDTabCornerText> mViewManager = new SDSelectViewManager<SDTabCornerText>();

	private DistributionStoreBaseDealFragment mFragDeal;
	private DistributionStoreTuanFragment mFragTuan;
	private DistributionStoreGoodsFragment mFragGoods;

	private PageModel mPage;

	/** 0:团购，1:商品 */
	private int mType;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_distribution_store);
		init();
	}

	private void init()
	{
		initTitle();
		initTabs();
		initScrollView();
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("小店");
	}

	private void initTabs()
	{
		mTab_tuan.setTextTitle("团购精品");
		mTab_tuan.setTextSizeTitle(14);
		mTab_tuan.setPosition(TabPosition.FIRST);

		mTab_goods.setTextTitle("商城热销");
		mTab_goods.setTextSizeTitle(14);
		mTab_goods.setPosition(TabPosition.LAST);

		SDTabCornerText[] items = new SDTabCornerText[] { mTab_tuan, mTab_goods };
		mViewManager.setItems(items);

		mViewManager.setListener(new SDSelectManagerListener<SDTabCornerText>()
		{

			@Override
			public void onNormal(int index, SDTabCornerText item)
			{

			}

			@Override
			public void onSelected(int index, SDTabCornerText item)
			{
				boolean needRefresh = false;
				switch (index)
				{
				case 0:
					if (mFragTuan == null)
					{
						needRefresh = true;
					}
					mFragTuan = (DistributionStoreTuanFragment) getSDFragmentManager().toggle(R.id.act_distribution_store_fl_content, null,
							DistributionStoreTuanFragment.class);
					break;
				case 1:
					if (mFragGoods == null)
					{
						needRefresh = true;
					}
					mFragGoods = (DistributionStoreGoodsFragment) getSDFragmentManager().toggle(R.id.act_distribution_store_fl_content, null,
							DistributionStoreGoodsFragment.class);
					break;

				default:
					break;
				}
				if (needRefresh)
				{
					mSsv_all.setRefreshing();
				}
			}
		});
		mViewManager.performClick(0);
	}

	private void initScrollView()
	{
		mSsv_all.setMode(Mode.BOTH);
		mSsv_all.setOnRefreshListener(new OnRefreshListener2<StickyScrollView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<StickyScrollView> refreshView)
			{
				pullRefresh();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<StickyScrollView> refreshView)
			{
				pullLoadMore();
			}
		});
	}

	protected void pullRefresh()
	{
		resetPage();
		requestData(false);
	}

	private DistributionStoreBaseDealFragment getSelectFragment()
	{
		DistributionStoreBaseDealFragment frag = null;
		switch (mViewManager.getSelectedIndex())
		{
		case 0:
			frag = mFragTuan;
			break;
		case 1:
			frag = mFragGoods;
			break;

		default:
			break;
		}
		return frag;
	}

	private void findParams()
	{
		mFragDeal = getSelectFragment();
		if (mFragDeal != null)
		{
			mPage = mFragDeal.getmPage();
			mType = mFragDeal.getmType();
		}
	}

	private void resetPage()
	{
		findParams();
		if (mPage != null)
		{
			mPage.resetPage();
		}
	}

	protected void pullLoadMore()
	{
		findParams();
		if (mPage != null)
		{
			if (mPage.increment())
			{
				requestData(true);
			} else
			{
				SDToast.showToast("没有更多数据了");
				mSsv_all.onRefreshComplete();
			}
		}
	}

	private void requestData(final boolean isLoadMore)
	{
		CommonInterface.requestDistributionStore(mType, mPage.getPage(), new SDRequestCallBack<Uc_fx_mallActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					MyDistributionUser_dataModel userData = actModel.getUser_data();
					if (userData != null)
					{
						SDViewBinder.setImageView(userData.getFx_mall_bg(), mSiv_image);
						SDViewBinder.setImageView(userData.getUser_avatar(), miv_user_avatar);
						SDViewBinder.setTextView(mTv_username, userData.getUser_name());
						String userName = userData.getUser_name();
						mTitle.setMiddleTextTop(userName + "的小店");
					}

					if (mFragDeal != null)
					{
						if (isLoadMore)
						{
							mFragDeal.pullLoadMore(actModel);
						} else
						{
							mFragDeal.pullRefresh(actModel);
						}
					}
				}
			}

			@Override
			public void onStart()
			{
			}

			@Override
			public void onFinish()
			{
				mSsv_all.onRefreshComplete();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
			}
		});
	}

}

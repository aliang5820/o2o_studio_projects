package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fanwe.adapter.CategoryCateLeftAdapter;
import com.fanwe.adapter.CategoryCateRightAdapter;
import com.fanwe.adapter.CategoryOrderAdapter;
import com.fanwe.adapter.CategoryQuanLeftAdapter;
import com.fanwe.adapter.CategoryQuanRightAdapter;
import com.fanwe.adapter.MerchantListAdapter;
import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.customview.SD2LvCategoryView;
import com.fanwe.library.customview.SDLvCategoryView;
import com.fanwe.library.customview.SDLvCategoryView.SDLvCategoryViewListener;
import com.fanwe.library.customview.SDViewBase;
import com.fanwe.library.customview.SDViewNavigatorManager;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.Bcate_listModel;
import com.fanwe.model.CategoryOrderModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.Quan_listModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.StoreModel;
import com.fanwe.model.Stores_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

public class StoreListFragment extends BaseFragment
{
	/** 大分类id(int) */
	public static final String EXTRA_CATE_ID = "extra_cate_id";

	/** 小分类id(int) */
	public static final String EXTRA_TID = "extra_tid";

	/** 商圈id(int) */
	public static final String EXTRA_QID = "extra_qid";

	/** 关键字(String) */
	public static final String EXTRA_KEY_WORD = "extra_key_word";

	/** 门店类型(int) 1:优惠，2:全部 */
	public static final String EXTRA_STORE_TYPE = "extra_store_type";

	@ViewInject(R.id.lcv_left)
	private SD2LvCategoryView mCvLeft = null;

	@ViewInject(R.id.lcv_middle)
	private SD2LvCategoryView mCvMiddle = null;

	@ViewInject(R.id.lcv_right)
	private SDLvCategoryView mCvRight = null;

	@ViewInject(R.id.ll_empty)
	private LinearLayout mLlEmpty = null;

	@ViewInject(R.id.ll_current_location)
	private LinearLayout mLlCurrentLocation = null;

	@ViewInject(R.id.tv_current_address)
	private TextView mTvAddress = null;

	@ViewInject(R.id.iv_location)
	private ImageView mIvLocation = null;

	@ViewInject(R.id.ll_current_search)
	private LinearLayout mLlCurrentSearch = null;

	@ViewInject(R.id.tv_current_keyword)
	private TextView mTvCurrentKeyword = null;

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlvContent = null;

	private MerchantListAdapter mAdapter = null;
	private List<StoreModel> mListModel = new ArrayList<StoreModel>();
	private SDViewNavigatorManager mViewManager = new SDViewNavigatorManager();

	private boolean mIsNeedBindLeft = true;
	private boolean mIsNeedBindMiddle = true;
	private boolean mIsNeedBindRight = true;

	// =======================提交到服务器参数
	/** 大分类id */
	private int cate_id;
	/** 小分类id */
	private int tid;
	/** 关键词 */
	private String keyword;
	/** 商圈id */
	private int qid;
	/** 排序类型 */
	private String order_type;
	/** 米果定制 */
	private int store_type = 2;

	private PageModel mPage = new PageModel();

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_tuan_list);
	}

	private void resetParams()
	{
		cate_id = 0;
		tid = 0;
		qid = 0;
		order_type = null;
		mIsNeedBindLeft = true;
		mIsNeedBindMiddle = true;
		mIsNeedBindRight = true;
	}

	@Override
	protected void init()
	{
		getIntentData();
		bindDefaultLvData();
		bindLocationData();
		initCategoryView();
		initCategoryViewNavigatorManager();
		registeClick();
		initPullRefreshLv();
	}

	private void bindLocationData()
	{
		String addrShort = BaiduMapManager.getInstance().getCurAddressShort();
		if (TextUtils.isEmpty(addrShort))
		{
			locationAddress();
		}
	}

	public void scrollToTop()
	{
		if (isResumed() && mPtrlvContent != null && mAdapter != null && mAdapter.getCount() > 0)
		{
			mPtrlvContent.getRefreshableView().setSelection(0);
		}
	}

	private void getIntentData()
	{

		cate_id = getArguments().getInt(EXTRA_CATE_ID);
		tid = getArguments().getInt(EXTRA_TID);
		qid = getArguments().getInt(EXTRA_QID);
		keyword = getArguments().getString(EXTRA_KEY_WORD);
		store_type = getArguments().getInt(EXTRA_STORE_TYPE);

		if (TextUtils.isEmpty(keyword))
		{
			mLlCurrentLocation.setVisibility(View.VISIBLE);
			mLlCurrentSearch.setVisibility(View.GONE);
			if (BaiduMapManager.getInstance().getCurAddress() != null)
			{
				mTvAddress.setText(BaiduMapManager.getInstance().getCurAddressShort());
			}
		} else
		{
			mLlCurrentLocation.setVisibility(View.GONE);
			mLlCurrentSearch.setVisibility(View.VISIBLE);
			mTvCurrentKeyword.setText(keyword);
		}
	}

	private void initPullRefreshLv()
	{
		mPtrlvContent.setMode(Mode.BOTH);
		mPtrlvContent.setOnRefreshListener(new OnRefreshListener2<ListView>()
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
				if (!mPage.increment())
				{
					SDToast.showToast("没有更多数据了");
					mPtrlvContent.onRefreshComplete();
				} else
				{
					requestData(true);
				}
			}
		});
		mPtrlvContent.setRefreshing();
	}

	private void bindDefaultLvData()
	{
		mAdapter = new MerchantListAdapter(mListModel, getActivity());
		mPtrlvContent.setAdapter(mAdapter);
	}

	private void initCategoryViewNavigatorManager()
	{
		SDViewBase[] items = new SDViewBase[] { mCvLeft, mCvMiddle, mCvRight };
		mViewManager.setItems(items);
		mViewManager.setmMode(SDViewNavigatorManager.Mode.CAN_NONE_SELECT);
	}

	private void initCategoryView()
	{
		mCvLeft.getmAttr().setmBackgroundDrawableNormalResId(R.drawable.bg_choosebar_press_down);
		mCvLeft.getmAttr().setmBackgroundDrawableSelectedResId(R.drawable.bg_choosebar_press_up);

		mCvLeft.getmAttr().setmTextColorNormalResId(R.color.text_item_content);
		mCvLeft.getmAttr().setmTextColorSelectedResId(R.color.main_color);
		mCvLeft.setmListener(new SD2LvCategoryView.SD2LvCategoryViewListener()
		{

			@Override
			public void onRightItemSelect(int leftIndex, int rightIndex, Object leftModel, Object rightModel)
			{
				Bcate_listModel left = (Bcate_listModel) leftModel;
				Bcate_listModel right = (Bcate_listModel) rightModel;
				cate_id = left.getId();
				tid = right.getId();
				mPtrlvContent.setRefreshing();
			}

			@Override
			public void onLeftItemSelect(int leftIndex, Object leftModel, boolean isNotifyDirect)
			{
				if (isNotifyDirect)
				{
					Bcate_listModel left = (Bcate_listModel) leftModel;
					Bcate_listModel right = SDCollectionUtil.get(left.getBcate_type(), 0);
					cate_id = left.getId();
					if (right != null)
					{
						tid = right.getId();
					} else
					{
						tid = 0;
					}
					mPtrlvContent.setRefreshing();
				}
			}
		});

		mCvMiddle.getmAttr().setmBackgroundDrawableNormalResId(R.drawable.bg_choosebar_press_down_2);
		mCvMiddle.getmAttr().setmBackgroundDrawableSelectedResId(R.drawable.bg_choosebar_press_up_2);

		mCvMiddle.getmAttr().setmTextColorNormalResId(R.color.text_item_content);
		mCvMiddle.getmAttr().setmTextColorSelectedResId(R.color.main_color);
		mCvMiddle.setmListener(new SD2LvCategoryView.SD2LvCategoryViewListener()
		{

			@Override
			public void onRightItemSelect(int leftIndex, int rightIndex, Object leftModel, Object rightModel)
			{
				Quan_listModel right = (Quan_listModel) rightModel;
				qid = right.getId();
				mPtrlvContent.setRefreshing();
			}

			@Override
			public void onLeftItemSelect(int leftIndex, Object leftModel, boolean isNotifyDirect)
			{
				if (isNotifyDirect)
				{
					Quan_listModel left = (Quan_listModel) leftModel;
					Quan_listModel right = SDCollectionUtil.get(left.getQuan_sub(), 0);
					if (right != null)
					{
						qid = right.getId();
					}
					if (qid <= 0)
					{
						qid = left.getId();
					}

					mPtrlvContent.setRefreshing();
				}
			}
		});

		mCvRight.getmAttr().setmBackgroundDrawableNormalResId(R.drawable.bg_choosebar_press_down_3);
		mCvRight.getmAttr().setmBackgroundDrawableSelectedResId(R.drawable.bg_choosebar_press_up_3);

		mCvRight.getmAttr().setmTextColorNormalResId(R.color.text_item_content);
		mCvRight.getmAttr().setmTextColorSelectedResId(R.color.main_color);
		mCvRight.setmListener(new SDLvCategoryViewListener()
		{
			@Override
			public void onItemSelect(int index, Object model)
			{
				if (model instanceof CategoryOrderModel)
				{
					CategoryOrderModel orderModel = (CategoryOrderModel) model;
					order_type = orderModel.getCode();
					mPtrlvContent.setRefreshing();
				}
			}
		});
	}

	protected void fillRequestParams(RequestModel model)
	{
		model.putCtl("stores");
		model.put("keyword", keyword);
		model.put("order_type", order_type); // 排序类型
		model.put("tid", tid);// 小分类ID
		model.put("cate_id", cate_id);// 大分类ID
		model.put("qid", qid);
		model.put("store_type", store_type);
		model.putPage(mPage.getPage());
	}

	private void requestData(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		fillRequestParams(model);
		SDRequestCallBack<Stores_indexActModel> handler = new SDRequestCallBack<Stores_indexActModel>()
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
					if (mIsNeedBindLeft)
					{
						bindLeftCategoryViewData(actModel.getBcate_list());
						mIsNeedBindLeft = false;
					}

					if (mIsNeedBindMiddle)
					{
						bindMiddleCategoryViewData(actModel.getQuan_list());
						mIsNeedBindMiddle = false;
					}

					if (mIsNeedBindRight)
					{
						bindRightCategoryViewData(actModel.getNavs());
						mIsNeedBindRight = false;
					}

					mPage.update(actModel.getPage());
					SDViewUtil.updateAdapterByList(mListModel, actModel.getItem(), mAdapter, isLoadMore);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{

			}

			@Override
			public void onFinish()
			{
				dealFinishRequest();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	protected void dealFinishRequest()
	{
		SDDialogManager.dismissProgressDialog();
		mPtrlvContent.onRefreshComplete();
		SDViewUtil.toggleEmptyMsgByList(mListModel, mLlEmpty);
	}

	private void bindLeftCategoryViewData(List<Bcate_listModel> listModel)
	{
		if (!SDCollectionUtil.isEmpty(listModel))
		{
			int[] arrIndex = Bcate_listModel.findIndex(cate_id, tid, listModel);
			int leftIndex = arrIndex[0];
			int rightIndex = arrIndex[1];

			Bcate_listModel leftModel = listModel.get(leftIndex);
			List<Bcate_listModel> listRight = leftModel.getBcate_type();

			CategoryCateLeftAdapter adapterLeft = new CategoryCateLeftAdapter(listModel, getActivity());
			adapterLeft.setmDefaultIndex(leftIndex);

			CategoryCateRightAdapter adapterRight = new CategoryCateRightAdapter(listRight, getActivity());
			adapterRight.setmDefaultIndex(rightIndex);

			mCvLeft.setLeftAdapter(adapterLeft);
			mCvLeft.setRightAdapter(adapterRight);
			mCvLeft.setAdapterFinish();
		}
	}

	private void bindMiddleCategoryViewData(List<Quan_listModel> listModel)
	{
		if (!SDCollectionUtil.isEmpty(listModel))
		{
			int[] arrIndex = Quan_listModel.findIndex(qid, listModel);
			int leftIndex = arrIndex[0];
			int rightIndex = arrIndex[1];

			Quan_listModel leftModel = listModel.get(leftIndex);
			List<Quan_listModel> listRight = leftModel.getQuan_sub();

			CategoryQuanLeftAdapter adapterLeft = new CategoryQuanLeftAdapter(listModel, getActivity());
			adapterLeft.setmDefaultIndex(leftIndex);

			CategoryQuanRightAdapter adapterRight = new CategoryQuanRightAdapter(listRight, getActivity());
			adapterRight.setmDefaultIndex(rightIndex);

			mCvMiddle.setLeftAdapter(adapterLeft);
			mCvMiddle.setRightAdapter(adapterRight);
			mCvMiddle.setAdapterFinish();
		}
	}

	private void bindRightCategoryViewData(List<CategoryOrderModel> listOrderModel)
	{
		if (!SDCollectionUtil.isEmpty(listOrderModel))
		{
			CategoryOrderAdapter adapter = new CategoryOrderAdapter(listOrderModel, getActivity());
			mCvRight.setAdapter(adapter);
		}
	}

	private void registeClick()
	{
		mIvLocation.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{

		case R.id.iv_location:
			clickTv_locaiton();
			break;

		default:
			break;
		}
	}

	private void clickTv_locaiton()
	{
		locationAddress();
	}

	/**
	 * 定位地址
	 */
	private void locationAddress()
	{
		// 开始定位
		setCurrentLocation("定位中", false);
		BaiduMapManager.getInstance().startLocation(new BDLocationListener()
		{

			@Override
			public void onReceiveLocation(BDLocation location)
			{
				if (BaiduMapManager.getInstance().hasLocationSuccess(location))
				{
					setCurrentLocation(BaiduMapManager.getInstance().getCurAddressShort(), true);
				}
			}
		});
	}

	private void setCurrentLocation(String string, boolean isLocationSuccess)
	{
		if (!TextUtils.isEmpty(string))
		{
			if (mTvAddress != null)
			{
				mTvAddress.setText(string);
				if (isLocationSuccess)
				{
					mPtrlvContent.setRefreshing();
				}
			}
		}
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case CITY_CHANGE:
			resetParams();
			mPtrlvContent.setRefreshing();
			break;

		default:
			break;
		}
	}

}
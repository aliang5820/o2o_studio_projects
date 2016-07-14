package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.fanwe.adapter.SearchTypeAdapter;
import com.fanwe.app.App;
import com.fanwe.constant.Constant.SearchTypeNormal;
import com.fanwe.constant.Constant.SearchTypeNormalString;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.fragment.EventListFragment;
import com.fanwe.fragment.GoodsListFragment;
import com.fanwe.fragment.StoreListFragment;
import com.fanwe.fragment.TuanListFragment;
import com.fanwe.fragment.YouHuiListFragment;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.SearchTypeModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HomeSearchActivity extends BaseActivity
{
	/** 0:优惠，2:团购，3:商家，4:活动，5:商城 */
	public static final String EXTRA_SEARCH_TYPE = "extra_search_type";

	@ViewInject(R.id.act_home_search_rl_search_bar)
	private RelativeLayout mRlSearchBar = null;

	@ViewInject(R.id.act_home_search_et_search_text)
	private ClearEditText mEtSearchText = null;

	@ViewInject(R.id.act_home_search_btn_search)
	private Button mBtnSearch = null;

	@ViewInject(R.id.act_home_search_gv_type)
	private GridView mGvType = null;

	private SearchTypeAdapter mAdapter = null;
	private List<SearchTypeModel> mListModel = new ArrayList<SearchTypeModel>();

	private int mSearchType = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_home_search);
		init();
	}

	private void init()
	{
		getIntentData();
		initTitle();
		bindDefaultData();
		registeClick();
	}

	private void getIntentData()
	{
		mSearchType = getIntent().getIntExtra(EXTRA_SEARCH_TYPE, -1);

		// 未找到搜索类型
		if (mSearchType < 0)
		{
			mSearchType = 2;
		}
	}

	private void bindDefaultData()
	{

		SearchTypeModel model0 = new SearchTypeModel();
		model0.setName(SearchTypeNormalString.YOU_HUI);
		model0.setSelect(false);
		model0.setType(SearchTypeNormal.YOU_HUI);
		mListModel.add(model0);

		SearchTypeModel model2 = new SearchTypeModel();
		model2.setName(SearchTypeNormalString.TUAN);
		model2.setSelect(false);
		model2.setType(SearchTypeNormal.TUAN);
		mListModel.add(model2);

		SearchTypeModel model3 = new SearchTypeModel();
		model3.setName(SearchTypeNormalString.MERCHANT);
		model3.setSelect(false);
		model3.setType(SearchTypeNormal.MERCHANT);
		mListModel.add(model3);

		SearchTypeModel model4 = new SearchTypeModel();
		model4.setName(SearchTypeNormalString.EVENT);
		model4.setSelect(false);
		model4.setType(SearchTypeNormal.EVENT);
		mListModel.add(model4);

		SearchTypeModel model5 = new SearchTypeModel();
		model5.setName(SearchTypeNormalString.SHOP);
		model5.setSelect(false);
		model5.setType(SearchTypeNormal.SHOP);
		mListModel.add(model5);

		mAdapter = new SearchTypeAdapter(mListModel, this);
		mGvType.setAdapter(mAdapter);
		mGvType.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				SearchTypeModel model = mAdapter.getItem((int) id);
				mSearchType = model.getType();
				updateSelectState(mSearchType);
			}
		});
		updateSelectState(mSearchType);
	}

	private void updateSelectState(int type)
	{
		if (mListModel != null && mListModel.size() > 0)
		{
			for (int i = 0; i < mListModel.size(); i++)
			{
				SearchTypeModel model = mListModel.get(i);
				model.setSelect(model.getType() == type);
			}
		}
		if (mAdapter != null)
		{
			mAdapter.notifyDataSetChanged();
		}
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("搜索");
	}

	private void registeClick()
	{
		mBtnSearch.setOnClickListener(this);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.act_home_search_btn_search:
			clickSearchBtn();
			break;

		default:
			break;
		}
	}

	/**
	 * 搜索
	 */
	private void clickSearchBtn()
	{
		String search_text = mEtSearchText.getText().toString();
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		switch (mSearchType)
		{
		case SearchTypeNormal.YOU_HUI: // 优惠
			intent.setClass(App.getApplication(), YouHuiListActivity.class);
			intent.putExtra(YouHuiListFragment.EXTRA_KEY_WORD, search_text);
			startActivity(intent);
			break;
		case SearchTypeNormal.TUAN: // 团购
			intent.setClass(App.getApplication(), TuanListActivity.class);
			intent.putExtra(TuanListFragment.EXTRA_KEY_WORD, search_text);
			startActivity(intent);
			break;
		case SearchTypeNormal.MERCHANT: // 商家
			intent.setClass(App.getApplication(), StoreListActivity.class);
			intent.putExtra(StoreListFragment.EXTRA_KEY_WORD, search_text);
			startActivity(intent);
			break;
		case SearchTypeNormal.EVENT: // 活动
			intent.setClass(App.getApplication(), EventListActivity.class);
			intent.putExtra(EventListFragment.EXTRA_KEY_WORD, search_text);
			startActivity(intent);
			break;
		case SearchTypeNormal.SHOP: // 商城
			intent.setClass(App.getApplication(), GoodsListActivity.class);
			intent.putExtra(GoodsListFragment.EXTRA_KEY_WORD, search_text);
			startActivity(intent);
			break;
		default:
			SDToast.showToast("未知搜索类型");
			break;
		}
	}

}
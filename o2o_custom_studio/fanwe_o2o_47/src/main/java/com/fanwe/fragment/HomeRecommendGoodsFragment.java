package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fanwe.GoodsListActivity;
import com.fanwe.adapter.GoodsListAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.model.GoodsModel;
import com.fanwe.model.Index_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 首页推荐商品
 * 
 * @author js02
 * 
 */
public class HomeRecommendGoodsFragment extends BaseFragment
{

	@ViewInject(R.id.frag_home_recommend_goods_ll_deals)
	protected SDGridLinearLayout mLlDeals;

	@ViewInject(R.id.tv_see_all_goods)
	private TextView mTv_see_all_goods;

	private Index_indexActModel mIndexModel;

	private List<GoodsModel> mListModel = new ArrayList<GoodsModel>();

	public void setmIndexModel(Index_indexActModel indexModel)
	{
		this.mIndexModel = indexModel;
		this.mListModel = mIndexModel.getSupplier_deal_list();
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_home_recommend_goods);
	}

	@Override
	protected void init()
	{
		bindData();
		registeClick();
	}

	private void bindData()
	{
		if (!toggleFragmentView(mListModel))
		{
			return;
		}

		BaseAdapter adapter = getAdapter();
		mLlDeals.setAdapter(adapter);
	}

	protected BaseAdapter getAdapter()
	{
		return new GoodsListAdapter(mListModel, getActivity());
	}

	private void registeClick()
	{
		mTv_see_all_goods.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickSeeAllGoods();
			}
		});
	}

	private void clickSeeAllGoods()
	{
		startActivity(new Intent(getActivity(), GoodsListActivity.class));
	}

}
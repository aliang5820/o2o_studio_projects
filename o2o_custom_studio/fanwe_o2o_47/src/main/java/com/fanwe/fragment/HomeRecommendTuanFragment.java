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

import com.fanwe.TuanListActivity;
import com.fanwe.adapter.TuanListAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.model.GoodsModel;
import com.fanwe.model.Index_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 首页推荐团购
 * 
 * @author js02
 * 
 */
public class HomeRecommendTuanFragment extends BaseFragment
{

	@ViewInject(R.id.frag_home_recommend_deals_ll_deals)
	protected SDGridLinearLayout mLlDeals;

	@ViewInject(R.id.tv_see_all_tuan)
	private TextView mTv_see_all_tuan;

	private Index_indexActModel mIndexModel;

	private List<GoodsModel> mListModel = new ArrayList<GoodsModel>();

	public void setmIndexModel(Index_indexActModel indexModel)
	{
		this.mIndexModel = indexModel;
		this.mListModel = mIndexModel.getDeal_list();
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_home_recommend_tuan);
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
		return new TuanListAdapter(mListModel, getActivity());
	}

	private void registeClick()
	{
		mTv_see_all_tuan.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickSeeAllTuan();
			}
		});
	}

	private void clickSeeAllTuan()
	{
		startActivity(new Intent(getActivity(), TuanListActivity.class));
	}
}
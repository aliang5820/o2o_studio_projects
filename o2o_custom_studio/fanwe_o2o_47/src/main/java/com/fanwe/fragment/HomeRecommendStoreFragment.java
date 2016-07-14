package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fanwe.StoreListActivity;
import com.fanwe.adapter.HomeRecommendSupplierAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.model.Index_indexActModel;
import com.fanwe.model.StoreModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 首页推荐商家
 * 
 * @author js02
 * 
 */
public class HomeRecommendStoreFragment extends BaseFragment
{

	@ViewInject(R.id.frag_home_recommend_supplier_ll_suppliers)
	private SDGridLinearLayout mLlSuppliers;

	@ViewInject(R.id.frag_home_recommend_supplier_ll_all_suppliers)
	private TextView mTvAllSuppliers;

	private List<StoreModel> mListModel = new ArrayList<StoreModel>();

	private Index_indexActModel mIndexModel;

	public void setmIndexModel(Index_indexActModel indexModel)
	{
		this.mIndexModel = indexModel;
		this.mListModel = mIndexModel.getSupplier_list();
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_home_recommend_store);
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

		mLlSuppliers.setmColNumber(3);
		BaseAdapter adapter = getAdapter();
		mLlSuppliers.setAdapter(adapter);
	}

	protected BaseAdapter getAdapter()
	{
		return new HomeRecommendSupplierAdapter(mListModel, getActivity());
	}

	private void registeClick()
	{
		mTvAllSuppliers.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v == mTvAllSuppliers)
		{
			clickAllSuppliers();
		}
	}

	private void clickAllSuppliers()
	{
		Intent intent = new Intent(getActivity(), StoreListActivity.class);
		startActivity(intent);
	}
}
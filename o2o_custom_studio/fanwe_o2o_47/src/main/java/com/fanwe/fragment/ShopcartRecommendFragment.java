package com.fanwe.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.adapter.ShopcartRecommendAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.model.Cart_indexActModel;
import com.fanwe.model.GoodsModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 购物车(猜你喜欢fragment)
 * 
 * @author Administrator
 * 
 */
public class ShopcartRecommendFragment extends BaseFragment
{

	@ViewInject(R.id.frag_shopcart_recommend_ll_content)
	private SDGridLinearLayout mLl_content;

	private Cart_indexActModel mActModel;

	public void setmActModel(Cart_indexActModel actModel)
	{
		this.mActModel = actModel;
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_shopcart_recommend);
	}

	@Override
	protected void init()
	{
		mLl_content.setmColNumber(3);
		bindData();
	}

	private void bindData()
	{
		if (!toggleFragmentView(mActModel))
		{
			return;
		}
		List<GoodsModel> listLike = mActModel.getLike();
		if (!toggleFragmentView(listLike))
		{
			return;
		}

		ShopcartRecommendAdapter adapter = new ShopcartRecommendAdapter(listLike, getActivity());
		mLl_content.setAdapter(adapter);
	}
}

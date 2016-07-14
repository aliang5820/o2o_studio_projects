package com.fanwe.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.fanwe.adapter.DistributionHotGoodsAdapter;
import com.fanwe.model.DistributionGoodsModel;
import com.fanwe.model.Uc_fx_mallActModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 分销小店热门推荐fragment（米果定制）
 * 
 * @author Administrator
 * 
 */
public class DistributionHotGoodsFragment extends BaseFragment
{

	@ViewInject(R.id.ll_deal_hot)
	private LinearLayout mLl_content;

	private Uc_fx_mallActModel mActModel;

	public void setmActModel(Uc_fx_mallActModel mActModel)
	{
		this.mActModel = mActModel;
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_distribution_hot_goods);
	}

	@Override
	protected void init()
	{
		bindData();
	}

	private void bindData()
	{
		if (!toggleFragmentView(mActModel))
		{
			return;
		}

		List<DistributionGoodsModel> listModel = mActModel.getHot_list();
		if (!toggleFragmentView(listModel))
		{
			return;
		}

		mLl_content.removeAllViews();
		DistributionHotGoodsAdapter adapter = new DistributionHotGoodsAdapter(listModel, getActivity());
		int listSize = listModel.size();
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
		for (int i = 0; i < listSize; i++)
		{
			View view = adapter.getView(i, null, null);
			mLl_content.addView(view, param);
		}
	}

}

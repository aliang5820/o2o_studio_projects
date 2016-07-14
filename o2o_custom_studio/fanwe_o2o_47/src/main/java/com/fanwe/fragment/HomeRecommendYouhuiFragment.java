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

import com.fanwe.YouHuiListActivity;
import com.fanwe.adapter.YouHuiListAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.model.Index_indexActModel;
import com.fanwe.model.YouhuiModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 首页推荐优惠券
 * 
 * @author js02
 * 
 */
public class HomeRecommendYouhuiFragment extends BaseFragment
{

	@ViewInject(R.id.frag_home_recommend_coupon_ll_coupon)
	private SDGridLinearLayout mLlCoupon;

	@ViewInject(R.id.tv_see_all_youhui)
	private TextView mTv_see_all_youhui;

	private List<YouhuiModel> mListModel = new ArrayList<YouhuiModel>();

	private Index_indexActModel mIndexModel;

	public void setmIndexModel(Index_indexActModel indexModel)
	{
		this.mIndexModel = indexModel;
		if (mIndexModel != null)
		{
			mListModel = mIndexModel.getYouhui_list();
		}
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_home_recommend_youhui);
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
		mLlCoupon.setAdapter(adapter);
	}

	protected BaseAdapter getAdapter()
	{
		return new YouHuiListAdapter(mListModel, getActivity());
	}

	private void registeClick()
	{
		mTv_see_all_youhui.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickSeeAllYouhui();
			}
		});
	}

	private void clickSeeAllYouhui()
	{
		startActivity(new Intent(getActivity(), YouHuiListActivity.class));
	}

}
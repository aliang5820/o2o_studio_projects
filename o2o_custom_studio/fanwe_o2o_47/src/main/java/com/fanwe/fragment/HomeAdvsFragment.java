package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDPagerAdapter.SDBasePagerAdapterOnItemClickListener;
import com.fanwe.library.adapter.SDSimpleAdvsAdapter;
import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.library.utils.SDActivityUtil;
import com.fanwe.model.IndexActAdvsModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 首页广告fragment
 * 
 * @author js02
 * 
 */
public class HomeAdvsFragment extends BaseFragment
{
	/** 首页接口model */
	public static final String EXTRA_LIST_INDEX_ACT_ADVS_MODEL = "extra_list_index_act_advs_model";

	@ViewInject(R.id.spv_content)
	private SDSlidingPlayView mSpvAd;

	private List<IndexActAdvsModel> mListModel = new ArrayList<IndexActAdvsModel>();
	private SDSimpleAdvsAdapter<IndexActAdvsModel> mAdapter;

	public void setListIndexActAdvsModel(List<IndexActAdvsModel> listModel)
	{
		if (listModel != null)
		{
			mListModel = listModel;
		}
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_home_advs);
	}

	@Override
	protected void init()
	{
		initSlidingPlayView();
		bindDefaultData();
		bindData();

	}

	private void bindDefaultData()
	{
		mAdapter = new SDSimpleAdvsAdapter<IndexActAdvsModel>(mListModel, getActivity());
		mAdapter.setmView(mSpvAd);
		mAdapter.setmListenerOnItemClick(new SDBasePagerAdapterOnItemClickListener()
		{

			@Override
			public void onItemClick(View v, int position)
			{
				IndexActAdvsModel model = mAdapter.getItemModel(position);
				clickAd(model);
			}
		});
		mSpvAd.setAdapter(mAdapter);
	}

	private void bindData()
	{
		toggleFragmentView(mListModel);
	}

	private void initSlidingPlayView()
	{
		mSpvAd.setmImageNormalResId(R.drawable.ic_main_dot2_normal);
		mSpvAd.setmImageSelectedResId(R.drawable.ic_main_dot2_foused);
	}

	@Override
	public void onResume()
	{
		if (mSpvAd != null)
		{
			mSpvAd.startPlay();
		}
		super.onResume();
	}

	@Override
	public void onPause()
	{
		if (mSpvAd != null)
		{
			mSpvAd.stopPlay();
		}
		super.onPause();
	}

	/**
	 * 广告被点击
	 * 
	 * @param model
	 */
	private void clickAd(IndexActAdvsModel model)
	{
		if (model != null)
		{
			int type = model.getType();
			Intent intent = AppRuntimeWorker.createIntentByType(type, model.getData(), true);
			SDActivityUtil.startActivity(this, intent);
		}
	}

	@Override
	public void onDestroy()
	{
		if (mSpvAd != null)
		{
			mSpvAd.stopPlay();
		}
		super.onDestroy();
	}

}
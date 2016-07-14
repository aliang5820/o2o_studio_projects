package com.fanwe.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.adapter.HomeIndexPageAdapter;
import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.IndexActIndexsModel;
import com.fanwe.model.Index_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 首页分类fragment
 * 
 * @author js02
 * 
 */
public class HomeIndexFragment extends BaseFragment
{

	@ViewInject(R.id.spv_content)
	private SDSlidingPlayView mSpvAd;

	private Index_indexActModel mIndexModel;
	private List<IndexActIndexsModel> mListIndexsModel;

	private HomeIndexPageAdapter mAdapter;

	public void setmIndexModel(Index_indexActModel indexModel)
	{
		this.mIndexModel = indexModel;
		if (mIndexModel != null)
		{
			this.mListIndexsModel = mIndexModel.getIndexs();
		}
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_home_index);
	}

	@Override
	protected void init()
	{
		initSlidingPlayView();
		bindData();
	}

	private void initSlidingPlayView()
	{
		mSpvAd.setmImageNormalResId(R.drawable.ic_main_dot2_normal);
		mSpvAd.setmImageSelectedResId(R.drawable.ic_main_dot2_foused);
	}

	private void bindData()
	{
		if (!toggleFragmentView(mListIndexsModel))
		{
			return;
		}

		if (mListIndexsModel.size() > 8)
		{
			SDViewUtil.setViewMarginBottom(mSpvAd.mVpgContent, SDViewUtil.dp2px(10));
		}

		mAdapter = new HomeIndexPageAdapter(SDCollectionUtil.splitList(mListIndexsModel, 8), getActivity());
		mSpvAd.setAdapter(mAdapter);
	}

}
package com.fanwe.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.adapter.ExpressionAdapter.ExpressionAdapterListener;
import com.fanwe.adapter.ExpressionPagerAdapter;
import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.ExpressionModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 表情项fragment
 * 
 * @author Administrator
 * 
 */
public class ExpressionFragment extends BaseFragment
{

	@ViewInject(R.id.spv_content)
	private SDSlidingPlayView mSpvContent;

	private ExpressionPagerAdapter mAdapter;
	private List<ExpressionModel> mListModel;
	private ExpressionAdapterListener mListenerOnItemClick;

	public void setListExpressionGroupModel(List<ExpressionModel> listModel)
	{
		this.mListModel = listModel;
	}

	public void setmListenerOnItemClick(ExpressionAdapterListener mListenerOnItemClick)
	{
		this.mListenerOnItemClick = mListenerOnItemClick;
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_expression);
	}

	@Override
	protected void init()
	{
		initSlidingPlayView();
		bindData();
	}

	private void initSlidingPlayView()
	{
		mSpvContent.setmImageNormalResId(R.drawable.ic_main_dot2_normal);
		mSpvContent.setmImageSelectedResId(R.drawable.ic_main_dot2_foused);
		SDViewUtil.setViewMarginBottom(mSpvContent.mVpgContent, SDViewUtil.dp2px(20));
	}

	private void bindData()
	{
		if (!toggleFragmentView(mListModel))
		{
			return;
		}

		List<List<ExpressionModel>> listModel = SDCollectionUtil.splitList(mListModel, 28);
		mAdapter = new ExpressionPagerAdapter(listModel, getActivity());
		mAdapter.setmListener(mListenerOnItemClick);
		mSpvContent.setAdapter(mAdapter);
	}

}

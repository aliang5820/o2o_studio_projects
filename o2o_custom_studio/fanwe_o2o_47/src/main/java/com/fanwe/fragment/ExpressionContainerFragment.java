package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.fanwe.adapter.ExpressionAdapter.ExpressionAdapterListener;
import com.fanwe.library.customview.SDTabItem;
import com.fanwe.library.customview.SDViewNavigatorManager;
import com.fanwe.library.customview.SDViewNavigatorManager.SDViewNavigatorManagerListener;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.ExpressionGroupModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ExpressionContainerFragment extends BaseFragment
{

	@ViewInject(R.id.ll_expression_group)
	private LinearLayout mLl_expression_group;

	@ViewInject(R.id.ll_delete)
	private LinearLayout mLl_delete;

	private List<ExpressionFragment> mListFragment = new ArrayList<ExpressionFragment>();
	private SDViewNavigatorManager mNavigatorManager = new SDViewNavigatorManager();
	private List<ExpressionGroupModel> mListModel = new ArrayList<ExpressionGroupModel>();

	private ExpressionAdapterListener mListenerExpressionAdapter;
	private ExpressionContainerOnClickDelete mListenerOnClickDelete;

	public void setmListenerExpressionAdapter(ExpressionAdapterListener mListener)
	{
		this.mListenerExpressionAdapter = mListener;
	}

	public void setmListenerOnClickDelete(ExpressionContainerOnClickDelete mListenerOnClickDelete)
	{
		this.mListenerOnClickDelete = mListenerOnClickDelete;
	}

	public void setListExpressionGroupModel(List<ExpressionGroupModel> mListModel)
	{
		this.mListModel = mListModel;
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_expression_container);
	}

	@Override
	protected void init()
	{
		bindData();
		retisterClick();
	}

	private void retisterClick()
	{
		mLl_delete.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (mListenerOnClickDelete != null)
				{
					mListenerOnClickDelete.onClick(v);
				}
			}
		});
	}

	private void bindData()
	{
		if (!toggleFragmentView(mListModel))
		{
			return;
		}

		List<SDTabItem> listItems = new ArrayList<SDTabItem>();
		mLl_expression_group.removeAllViews();
		for (ExpressionGroupModel model : mListModel)
		{
			if (model != null)
			{
				// tab
				SDTabItem tab = createTab(model);
				listItems.add(tab);
				mLl_expression_group.addView(tab);

				// frament
				ExpressionFragment fragment = new ExpressionFragment();
				fragment.setmListenerOnItemClick(mListenerExpressionAdapter);
				fragment.setListExpressionGroupModel(model.getListExpression());
				mListFragment.add(fragment);
			}
		}

		mNavigatorManager.setItems(SDCollectionUtil.toArray(listItems));
		mNavigatorManager.setmListener(new SDViewNavigatorManagerListener()
		{
			@Override
			public void onItemClick(View v, int index)
			{
				getSDFragmentManager().toggle(R.id.frag_expression_container_fl_expression, null, mListFragment.get(index));
			}
		});
		mNavigatorManager.setSelectIndex(0, null, true);

		if (mListModel.size() <= 1)
		{
			SDViewUtil.hide(mLl_expression_group);
		}
	}

	private SDTabItem createTab(ExpressionGroupModel model)
	{
		SDTabItem tab = new SDTabItem(getActivity());
		tab.getmAttr().setmTextColorNormalResId(R.color.black);
		tab.getmAttr().setmTextColorSelectedResId(R.color.black);
		tab.getmAttr().setmBackgroundDrawableNormalResId(R.drawable.layer_white_stroke_all);
		tab.getmAttr().setmBackgroundDrawableSelectedResId(R.drawable.layer_gray_stroke_all);
		tab.setTabName(model.getName());
		tab.setMinimumWidth(SDViewUtil.dp2px(60));
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		tab.setLayoutParams(params);
		return tab;
	}

	public interface ExpressionContainerOnClickDelete
	{
		public void onClick(View v);
	}

}

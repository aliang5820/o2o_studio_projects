package com.fanwe.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.adapter.DealAttrAdapter;
import com.fanwe.adapter.DealAttrAdapter.DealAttrAdapterListener;
import com.fanwe.adapter.DealAttrGroupAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.model.Deal_attrModel;
import com.fanwe.model.Deal_attrValueModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 团购详情，商品套餐属性fragment
 * 
 * @author js02
 * 
 */
public class TuanDetailAttrsFragment extends TuanDetailBaseFragment
{
	@ViewInject(R.id.ll_attrs)
	private SDGridLinearLayout mLl_attrs;

	private TuanDetailAttrsFragment_onClickAttrItemListener mListenerOnClickAttrItem;

	public void setmListenerOnClickAttrItem(TuanDetailAttrsFragment_onClickAttrItemListener mListenerOnClickAttrItem)
	{
		this.mListenerOnClickAttrItem = mListenerOnClickAttrItem;
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_tuan_detail_attrs);
	}

	@Override
	protected void init()
	{
		bindData();
	}

	private void bindData()
	{
		if (!toggleFragmentView(mDealModel))
		{
			return;
		}

		List<Deal_attrModel> listAttr = mDealModel.getDeal_attr();
		if (!toggleFragmentView(listAttr))
		{
			return;
		}

		DealAttrGroupAdapter adapter = new DealAttrGroupAdapter(listAttr, getActivity());
		adapter.setmListener(new DealAttrAdapterListener()
		{

			@Override
			public void onClickItem(View v, int position, Deal_attrValueModel model, DealAttrAdapter adapter)
			{
				updateGoodsPrice();
				if (mListenerOnClickAttrItem != null)
				{
					mListenerOnClickAttrItem.onClickItem(v, position, model, adapter);
				}
			}
		});
		mLl_attrs.setAdapter(adapter);
	}

	public interface TuanDetailAttrsFragment_onClickAttrItemListener
	{
		public void onClickItem(View v, int position, Deal_attrValueModel model, DealAttrAdapter adapter);
	}

}
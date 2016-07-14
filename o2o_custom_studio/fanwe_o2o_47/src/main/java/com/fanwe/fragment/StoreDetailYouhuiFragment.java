package com.fanwe.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.adapter.YouHuiListAdapter;
import com.fanwe.library.customview.SDMoreLinearLayout;
import com.fanwe.library.customview.SDMoreLinearLayout.OnOpenCloseListener;
import com.fanwe.model.YouhuiModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 门店详情，推荐优惠券
 * 
 * @author js02
 * 
 */
public class StoreDetailYouhuiFragment extends StoreDetailBaseFragment
{

	@ViewInject(R.id.frag_store_detail_youhui_ll_more)
	private SDMoreLinearLayout mLl_more;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_store_detail_youhui);
	}

	@Override
	protected void init()
	{

		bindData();
	}

	private void bindData()
	{
		if (!toggleFragmentView(mStoreModel))
		{
			return;
		}

		final List<YouhuiModel> listModel = mStoreModel.getYouhui_list();
		if (!toggleFragmentView(listModel))
		{
			return;
		}

		mLl_more.setmMaxShowCount(3);
		mLl_more.setmViewMoreLayoutId(R.layout.view_more_switch);
		mLl_more.setmListenerOnOpenClose(new OnOpenCloseListener()
		{

			@Override
			public void onOpen(List<View> listView, View viewMore)
			{
				TextView tvMore = (TextView) viewMore.findViewById(R.id.tv_more);
				tvMore.setText(R.string.click_close);
			}

			@Override
			public void onClose(List<View> listView, View viewMore)
			{
				TextView tvMore = (TextView) viewMore.findViewById(R.id.tv_more);
				tvMore.setText(R.string.click_expand);
			}
		});

		YouHuiListAdapter adapter = new YouHuiListAdapter(listModel, getActivity());
		mLl_more.setAdapter(adapter);
	}
}
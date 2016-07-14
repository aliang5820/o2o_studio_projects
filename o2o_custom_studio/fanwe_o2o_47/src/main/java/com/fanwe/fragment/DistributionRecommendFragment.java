package com.fanwe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.library.common.SDSelectManager.SDSelectManagerListener;
import com.fanwe.library.view.SDTabCorner.TabPosition;
import com.fanwe.library.view.SDTabCornerText;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 分销推荐
 * 
 * @author Administrator
 * 
 */
public class DistributionRecommendFragment extends BaseFragment
{

	@ViewInject(R.id.tab_my_distribution_recommend)
	private SDTabCornerText mTab_my_distribution_recommend;

	@ViewInject(R.id.tab_distribution_money_log)
	private SDTabCornerText mTab_distribution_money_log;

	private SDSelectViewManager<SDTabCornerText> mViewManager = new SDSelectViewManager<SDTabCornerText>();

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.frag_distribution_recommend);
	}

	@Override
	protected void init()
	{
		initTitle();
		initTabs();
	}

	private void initTabs()
	{
		mTab_my_distribution_recommend.setTextTitle("分销推荐");
		mTab_my_distribution_recommend.setTextSizeTitle(14);
		mTab_my_distribution_recommend.setPosition(TabPosition.FIRST);

		mTab_distribution_money_log.setTextTitle("分销资金日志");
		mTab_distribution_money_log.setTextSizeTitle(14);
		mTab_distribution_money_log.setPosition(TabPosition.LAST);

		SDTabCornerText[] items = new SDTabCornerText[] { mTab_my_distribution_recommend, mTab_distribution_money_log };
		mViewManager.setItems(items);
		mViewManager.setListener(new SDSelectManagerListener<SDTabCornerText>()
		{
			@Override
			public void onNormal(int index, SDTabCornerText item)
			{

			}

			@Override
			public void onSelected(int index, SDTabCornerText item)
			{
				switch (index)
				{
				case 0:
					getSDFragmentManager().toggle(R.id.frag_distribution_recommend_fl_content, null, MyDistributionRecommendFragment.class);
					break;
				case 1:
					getSDFragmentManager().toggle(R.id.frag_distribution_recommend_fl_content, null, MyDistributionMoneyLogFragment.class);
					break;

				default:
					break;
				}
			}
		});
		mViewManager.performClick(0);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("分销推荐");
	}

}

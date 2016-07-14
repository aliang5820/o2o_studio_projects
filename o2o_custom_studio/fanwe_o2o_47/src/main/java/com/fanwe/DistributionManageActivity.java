package com.fanwe;

import android.os.Bundle;
import android.view.View;

import com.fanwe.fragment.DistributionMarketFragment;
import com.fanwe.fragment.DistributionRecommendFragment;
import com.fanwe.fragment.DistributionWithdrawFragment;
import com.fanwe.fragment.MyDistributionFragment;
import com.fanwe.library.customview.SDTabItemBottom;
import com.fanwe.library.customview.SDViewBase;
import com.fanwe.library.customview.SDViewNavigatorManager;
import com.fanwe.library.customview.SDViewNavigatorManager.SDViewNavigatorManagerListener;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 会员中心-分销管理
 * 
 * @author Administrator
 * 
 */
public class DistributionManageActivity extends BaseActivity
{

	/** 默认选中第几项,值为0-3(int) */
	public static final String EXTRA_SELECT_INDEX = "extra_select_index";

	@ViewInject(R.id.tab_0)
	private SDTabItemBottom mTab0;

	@ViewInject(R.id.tab_1)
	private SDTabItemBottom mTab1;

	@ViewInject(R.id.tab_2)
	private SDTabItemBottom mTab2;

	@ViewInject(R.id.tab_3)
	private SDTabItemBottom mTab3;

	private MyDistributionFragment mFragMyDistribution = new MyDistributionFragment();
	private DistributionMarketFragment mFragDistributionMarket = new DistributionMarketFragment();
	private DistributionWithdrawFragment mFragDistributionWithdraw = new DistributionWithdrawFragment();
	private DistributionRecommendFragment mFragDistributionRecommend = new DistributionRecommendFragment();

	private SDViewNavigatorManager mViewManager = new SDViewNavigatorManager();

	private int mSelectIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_distribution_manage);
		init();
	}

	private void init()
	{
		getIntentData();
		initBottom();
	}

	private void getIntentData()
	{
		int index = getIntent().getIntExtra(EXTRA_SELECT_INDEX, 0);
		if (index < 0 || index > 3)
		{
			index = 0;
		}
		mSelectIndex = index;
		mFragDistributionMarket.setArguments(getIntent().getExtras());
	}

	private void initBottom()
	{
		mTab0.setBackgroundTextTitleNumber(R.drawable.bg_number);
		mTab1.setBackgroundTextTitleNumber(R.drawable.bg_number);
		mTab2.setBackgroundTextTitleNumber(R.drawable.bg_number);
		mTab3.setBackgroundTextTitleNumber(R.drawable.bg_number);

		mTab0.setTextTitle("我的");
		mTab1.setTextTitle("市场");
		mTab2.setTextTitle("提现");
		mTab3.setTextTitle("推荐");

		mTab0.getmAttr().setmImageNormalResId(R.drawable.ic_distribution_tab_my_normal);
		mTab1.getmAttr().setmImageNormalResId(R.drawable.ic_distribution_tab_market_normal);
		mTab2.getmAttr().setmImageNormalResId(R.drawable.ic_distribution_tab_withdraw_normal);
		mTab3.getmAttr().setmImageNormalResId(R.drawable.ic_distribution_tab_recommend_normal);

		mTab0.getmAttr().setmImageSelectedResId(R.drawable.ic_distribution_tab_my_selected);
		mTab1.getmAttr().setmImageSelectedResId(R.drawable.ic_distribution_tab_market_selected);
		mTab2.getmAttr().setmImageSelectedResId(R.drawable.ic_distribution_tab_withdraw_selected);
		mTab3.getmAttr().setmImageSelectedResId(R.drawable.ic_distribution_tab_recommend_selected);

		mTab0.getmAttr().setmTextColorNormalResId(R.color.text_home_menu_normal);
		mTab1.getmAttr().setmTextColorNormalResId(R.color.text_home_menu_normal);
		mTab2.getmAttr().setmTextColorNormalResId(R.color.text_home_menu_normal);
		mTab3.getmAttr().setmTextColorNormalResId(R.color.text_home_menu_normal);

		mTab0.getmAttr().setmTextColorSelectedResId(R.color.text_home_menu_selected);
		mTab1.getmAttr().setmTextColorSelectedResId(R.color.text_home_menu_selected);
		mTab2.getmAttr().setmTextColorSelectedResId(R.color.text_home_menu_selected);
		mTab3.getmAttr().setmTextColorSelectedResId(R.color.text_home_menu_selected);

		SDViewBase[] items = new SDViewBase[] { mTab0, mTab1, mTab2, mTab3 };

		mViewManager.setItems(items);
		mViewManager.setmListener(new SDViewNavigatorManagerListener()
		{
			@Override
			public void onItemClick(View v, int index)
			{
				switch (index)
				{
				case 0:
					click0();
					break;
				case 1:
					click1();
					break;
				case 2:
					click2();
					break;
				case 3:
					click3();
					break;

				default:
					break;
				}
			}
		});

		mViewManager.setSelectIndex(mSelectIndex, items[mSelectIndex], true);
	}

	/**
	 * 我的
	 */
	protected void click0()
	{
		getSDFragmentManager().toggle(R.id.act_distribution_manage_fl_content, null, mFragMyDistribution);
	}

	/**
	 * 市场
	 */
	protected void click1()
	{
		getSDFragmentManager().toggle(R.id.act_distribution_manage_fl_content, null, mFragDistributionMarket);
	}

	/**
	 * 提现
	 */
	protected void click2()
	{
		getSDFragmentManager().toggle(R.id.act_distribution_manage_fl_content, null, mFragDistributionWithdraw);
	}

	/**
	 * 推荐
	 */
	protected void click3()
	{
		getSDFragmentManager().toggle(R.id.act_distribution_manage_fl_content, null, mFragDistributionRecommend);
	}

}

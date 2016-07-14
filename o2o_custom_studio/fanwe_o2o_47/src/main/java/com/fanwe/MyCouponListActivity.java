package com.fanwe;

import android.os.Bundle;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.fragment.MyCouponListFragment;
import com.fanwe.fragment.MyCouponListFragment.CouponTag;
import com.fanwe.library.common.SDSelectManager.SDSelectManagerListener;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.view.SDTabCorner.TabPosition;
import com.fanwe.library.view.SDTabCornerText;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的团购券
 * 
 * @author js02
 * 
 */
public class MyCouponListActivity extends BaseActivity
{

	@ViewInject(R.id.tab_unused)
	private SDTabCornerText mTab_unused;

	@ViewInject(R.id.tab_will_overdue)
	private SDTabCornerText mTab_will_overdue;

	@ViewInject(R.id.tab_overdue)
	private SDTabCornerText mTab_overdue;

	@ViewInject(R.id.tab_all)
	private SDTabCornerText mTab_all;

	private SDSelectViewManager<SDTabCornerText> mViewManager = new SDSelectViewManager<SDTabCornerText>();

	private MyCouponListFragment mFragUnused;
	private MyCouponListFragment mFragWillOverdue;
	private MyCouponListFragment mFragOverdue;
	private MyCouponListFragment mFragAll;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_my_coupon_list);
		init();
	}

	private void init()
	{
		initTitle();
		createFragments();
		initTabs();
	}

	private void createFragments()
	{
		mFragUnused = MyCouponListFragment.newInstance(CouponTag.UN_USED);
		mFragWillOverdue = MyCouponListFragment.newInstance(CouponTag.WILL_OVERDUE);
		mFragOverdue = MyCouponListFragment.newInstance(CouponTag.OVERDUE);
		mFragAll = MyCouponListFragment.newInstance(CouponTag.ALL);
	}

	private void initTabs()
	{
		mTab_unused.setTextTitle("未使用");
		mTab_unused.setTextSizeTitle(14);
		mTab_unused.setPosition(TabPosition.FIRST);

		mTab_will_overdue.setTextTitle("即将过期");
		mTab_will_overdue.setTextSizeTitle(14);
		mTab_will_overdue.setPosition(TabPosition.MIDDLE);

		mTab_overdue.setTextTitle("已失效");
		mTab_overdue.setTextSizeTitle(14);
		mTab_overdue.setPosition(TabPosition.MIDDLE);

		mTab_all.setTextTitle("全部");
		mTab_all.setTextSizeTitle(14);
		mTab_all.setPosition(TabPosition.LAST);

		mViewManager.setItems(new SDTabCornerText[] { mTab_unused, mTab_will_overdue, mTab_overdue, mTab_all });
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
				case 0: // 未使用
					clickUnused();
					break;
				case 1: // 即将过期
					clickWillOverdue();
					break;
				case 2: // 已失效
					clickOverdue();
					break;
				case 3: // 全部
					clickAll();
					break;
				default:
					break;
				}
			}
		});
		mViewManager.performClick(0);
	}

	protected void clickUnused()
	{
		getSDFragmentManager().toggle(R.id.act_my_coupon_list_fl_content, null, mFragUnused);
	}

	protected void clickWillOverdue()
	{
		getSDFragmentManager().toggle(R.id.act_my_coupon_list_fl_content, null, mFragWillOverdue);
	}

	protected void clickOverdue()
	{
		getSDFragmentManager().toggle(R.id.act_my_coupon_list_fl_content, null, mFragOverdue);
	}

	protected void clickAll()
	{
		getSDFragmentManager().toggle(R.id.act_my_coupon_list_fl_content, null, mFragAll);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop(SDResourcesUtil.getString(R.string.my_tuan_gou_coupon));
	}

}
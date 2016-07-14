package com.fanwe;

import android.os.Bundle;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.fragment.MyCollectionEventFragment;
import com.fanwe.fragment.MyCollectionTuanGoodsFragment;
import com.fanwe.fragment.MyCollectionYouhuiFragment;
import com.fanwe.library.common.SDSelectManager.SDSelectManagerListener;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabCorner.TabPosition;
import com.fanwe.library.view.SDTabCornerText;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的收藏
 * 
 * @author js02
 * 
 */
public class MyCollectionActivity extends BaseActivity
{
	@ViewInject(R.id.tab_goods)
	private SDTabCornerText mTab_goods;

	@ViewInject(R.id.tab_youhui)
	private SDTabCornerText mTab_youhui;

	@ViewInject(R.id.tab_event)
	private SDTabCornerText mTab_event;

	@ViewInject(R.id.tab_dc_collection)
	private SDTabCornerText mTab_dc_collection;

	private SDSelectViewManager<SDTabCornerText> mViewManager = new SDSelectViewManager<SDTabCornerText>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_my_collection);
		init();
	}

	private void init()
	{
		initTitle();
		initTabs();
	}

	private void initTabs()
	{
		mTab_goods.setTextTitle(SDResourcesUtil.getString(R.string.tuan_gou_and_goods));
		mTab_goods.setTextSizeTitle(14);
		mTab_goods.setPosition(TabPosition.FIRST);

		mTab_youhui.setTextTitle(SDResourcesUtil.getString(R.string.youhui_coupon));
		mTab_youhui.setTextSizeTitle(14);
		mTab_youhui.setPosition(TabPosition.MIDDLE);

		mTab_event.setTextTitle(SDResourcesUtil.getString(R.string.event));
		mTab_event.setTextSizeTitle(14);
		mTab_event.setPosition(TabPosition.MIDDLE);

		mTab_dc_collection.setTextTitle(SDResourcesUtil.getString(R.string.dc_collection));
		mTab_dc_collection.setTextSizeTitle(14);
		mTab_dc_collection.setPosition(TabPosition.LAST);

		if (AppRuntimeWorker.getIs_plugin_dc() == 1)
		{
			SDViewUtil.show(mTab_dc_collection);
			mTab_event.setPosition(TabPosition.MIDDLE);
			mTab_dc_collection.setPosition(TabPosition.LAST);
		} else
		{
			SDViewUtil.hide(mTab_dc_collection);
			mTab_event.setPosition(TabPosition.LAST);
		}

		mViewManager.setItems(new SDTabCornerText[] { mTab_goods, mTab_youhui, mTab_event, mTab_dc_collection });

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
					clickGoods();
					break;
				case 1:
					clickYouhui();
					break;
				case 2:
					clickEvent();
					break;
				case 3:
					clickDcCollection();
					break;
				default:
					break;
				}
			}
		});
		mViewManager.performClick(0);

	}

	protected void clickDcCollection()
	{
		// TODO 切换到订餐收藏界面

		// getSDFragmentManager().toggle(R.id.act_my_collection_fl_content,
		// null, MyCollectionFragment_dc.class);
	}

	protected void clickGoods()
	{
		getSDFragmentManager().toggle(R.id.act_my_collection_fl_content, null, MyCollectionTuanGoodsFragment.class);
	}

	protected void clickYouhui()
	{
		getSDFragmentManager().toggle(R.id.act_my_collection_fl_content, null, MyCollectionYouhuiFragment.class);
	}

	protected void clickEvent()
	{
		getSDFragmentManager().toggle(R.id.act_my_collection_fl_content, null, MyCollectionEventFragment.class);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("我的收藏");
	}
}
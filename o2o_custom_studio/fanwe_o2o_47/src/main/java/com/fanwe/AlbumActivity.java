package com.fanwe;

import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.fanwe.adapter.AlbumAdapter;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.library.customview.HackyViewPager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 分享详情中图片被点击后的图片浏览activity
 * 
 * @author Administrator
 * 
 */
public class AlbumActivity extends BaseActivity
{
	/** 图片url集合 （图片链接ArrayList） */
	public static final String EXTRA_LIST_IMAGES = "extra_list_images";
	/** 第一次进入页面的时候选中的图片位置 （int） */
	public static final String EXTRA_IMAGES_INDEX = "extra_images_index";

	@ViewInject(R.id.act_album_vp_content)
	private HackyViewPager mVpContent;

	private AlbumAdapter mAdapter;

	private List<String> mListUrl;

	/**
	 * 第一次进入页面的时候选中的图片位置
	 */
	private int mFirstSelectIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_album);
		init();
	}

	private void init()
	{
		initTitle();
		getIntentData();
		initViewPager();
		bindData();
	}

	private void initTitle()
	{
		SDViewUtil.setBackgroundColorResId(mTitle, R.color.black);
	}

	private void getIntentData()
	{
		mListUrl = getIntent().getStringArrayListExtra(EXTRA_LIST_IMAGES);
		mFirstSelectIndex = getIntent().getIntExtra(EXTRA_IMAGES_INDEX, 0);
	}

	private void initViewPager()
	{
		mVpContent.setOnPageChangeListener(new OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int position)
			{
				setPositionData(position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{

			}

			@Override
			public void onPageScrollStateChanged(int state)
			{

			}
		});

	}

	private void setPositionData(int position)
	{
		if (SDCollectionUtil.isIndexLegalInList(mListUrl, position))
		{
			String textIndex = position + 1 + " of " + mListUrl.size();
			mTitle.setMiddleTextTop(textIndex);
		}
	}

	private void bindData()
	{
		mAdapter = new AlbumAdapter(mListUrl, mActivity);
		mVpContent.setAdapter(mAdapter);
		setSelectState();
	}

	private void setSelectState()
	{
		if (mVpContent != null && mAdapter != null)
		{
			if (mFirstSelectIndex >= 0 && mAdapter.getCount() > mFirstSelectIndex)
			{
				mVpContent.setCurrentItem(mFirstSelectIndex);
				setPositionData(mFirstSelectIndex);
			}
		}
	}

}
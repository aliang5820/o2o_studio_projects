package com.fanwe.library.customview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.R;
import com.fanwe.library.customview.SDViewPager.EnumMeasureMode;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDTimer;
import com.fanwe.library.utils.SDTimer.SDTimerListener;
import com.fanwe.library.utils.SDViewUtil;

public class SDSlidingPlayView extends LinearLayout
{

	private static final long DEFAULT_PLAY_SPAN = 1000 * 7;

	public SDViewPager mVpgContent;
	public TextView mTvCount;
	public LinearLayout mLlBot;
	public LinearLayout mLlBlur;
	private PagerAdapter mAdapter;
	private SDSlidingPlayViewOnPageChangeListener mListenerOnPageChange;
	private SDSlidingPlayViewOnTouchListener mListenerOnTouch;
	private SDTimer mTimer = new SDTimer();
	private int mCurrentPosition;
	private boolean mIsPlaying = false;
	private EnumMode mMode = EnumMode.IMAGE;
	private int mImageSelectedResId;
	private int mImageNormalResId;
	private int mLastSelectedPosition;
	private boolean mIsNeedPlay = false;

	public void setmMode(EnumMode mMode)
	{
		if (mMode != null)
		{
			this.mMode = mMode;
			changeBottomViewByData();
		}
	}

	public void setmImageSelectedResId(int mImageSelectedResId)
	{
		this.mImageSelectedResId = mImageSelectedResId;
	}

	public void setmImageNormalResId(int mImageNormalResId)
	{
		this.mImageNormalResId = mImageNormalResId;
	}

	public boolean isPlaying()
	{
		return mIsPlaying;
	}

	public void setmListenerOnPageChange(SDSlidingPlayViewOnPageChangeListener mListenerOnPageChange)
	{
		this.mListenerOnPageChange = mListenerOnPageChange;
	}

	public void setmListenerOnTouch(SDSlidingPlayViewOnTouchListener mListenerOnTouch)
	{
		this.mListenerOnTouch = mListenerOnTouch;
	}

	public SDSlidingPlayView(Context context)
	{
		this(context, null);
	}

	public SDSlidingPlayView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		View.inflate(getContext(), R.layout.view_sliding_play_view, this);
		mVpgContent = (SDViewPager) findViewById(R.id.vpg_content);
		mLlBlur = (LinearLayout) findViewById(R.id.ll_blur);
		mLlBot = (LinearLayout) findViewById(R.id.ll_bot);
		SDViewUtil.setViewHeight(mLlBlur, SDViewUtil.dp2px(20));

		mVpgContent.setmMeasureMode(EnumMeasureMode.MAX_CHILD);
		mVpgContent.setOnPageChangeListener(new PlayViewOnPageChangeListener());
		mVpgContent.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if (mListenerOnTouch != null)
				{
					mListenerOnTouch.onTouch(v, event);
				}
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					stopPlay();
					if (mListenerOnTouch != null)
					{
						mListenerOnTouch.onDown(v, event);
					}
					break;
				case MotionEvent.ACTION_MOVE:
					stopPlay();
					if (mListenerOnTouch != null)
					{
						mListenerOnTouch.onMove(v, event);
					}
					break;
				case MotionEvent.ACTION_UP:
					startPlayAuto();
					if (mListenerOnTouch != null)
					{
						mListenerOnTouch.onUp(v, event);
					}
					break;

				default:
					break;
				}
				return false;
			}
		});
	}

	private TextView createTextView()
	{
		TextView tvCount = new TextView(getContext());
		tvCount.setTextColor(SDResourcesUtil.getColor(R.color.white));
		return tvCount;
	}

	public void setAdapter(PagerAdapter adapter)
	{
		this.mAdapter = adapter;
		if (mAdapter != null)
		{
			mVpgContent.setAdapter(mAdapter);
			changeBottomViewByData();
		}
	}

	private void changeBottomViewByData()
	{
		if (mAdapter == null)
		{
			return;
		}

		mLlBlur.removeAllViews();
		int count = mAdapter.getCount();
		switch (mMode)
		{
		case IMAGE:
			if (count > 1)
			{
				mLlBot.setGravity(Gravity.CENTER);
				mLlBlur.setBackgroundResource(0);

				for (int i = 0; i < count; i++)
				{
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
					if (i != count - 1)
					{
						params.rightMargin = getRightMargin();
					}
					ImageView iv = new ImageView(getContext());
					iv.setScaleType(ScaleType.CENTER_INSIDE);
					setImageViewState(iv, false);
					mLlBlur.addView(iv, params);
				}
			}
			break;
		case NUMBER:
			mTvCount = createTextView();
			mLlBot.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
			mLlBlur.setBackgroundColor(SDResourcesUtil.getColor(R.color.blur));
			mLlBlur.addView(mTvCount);
			break;

		default:
			break;
		}
		updateBottomIndex();
	}

	private int getRightMargin()
	{
		return 20;
	}

	private void setImageViewState(View view, boolean selected)
	{
		if (view != null && (view instanceof ImageView))
		{
			ImageView iv = (ImageView) view;
			if (selected)
			{
				iv.setImageResource(mImageSelectedResId);
			} else
			{
				iv.setImageResource(mImageNormalResId);
			}
		}
	}

	private void setImageViewState(int position, boolean selected)
	{
		View view = mLlBlur.getChildAt(position);
		setImageViewState(view, selected);
	}

	class PlayViewOnPageChangeListener implements OnPageChangeListener
	{
		@Override
		public void onPageScrollStateChanged(int state)
		{
			if (mListenerOnPageChange != null)
			{
				mListenerOnPageChange.onPageScrollStateChanged(state);
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
		{
			if (mListenerOnPageChange != null)
			{
				mListenerOnPageChange.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}
		}

		@Override
		public void onPageSelected(int position)
		{
			mLastSelectedPosition = mCurrentPosition;
			mCurrentPosition = position;

			updateBottomIndex();
			if (mListenerOnPageChange != null)
			{
				mListenerOnPageChange.onPageSelected(position);
			}

		}
	}

	private void updateBottomIndex()
	{
		if (mAdapter == null)
		{
			return;
		}

		int total = mAdapter.getCount();

		switch (mMode)
		{
		case IMAGE:
			setImageViewState(mLastSelectedPosition, false);
			setImageViewState(mCurrentPosition, true);
			break;
		case NUMBER:
			mTvCount.setText((mCurrentPosition + 1) + "/" + total);
			break;

		default:
			break;
		}
	}

	private void setCurrentItem(int position)
	{
		if (mAdapter == null)
		{
			return;
		}

		int count = mAdapter.getCount();

		if (position < 0)
		{
			position = 0;
		}

		if (position >= count)
		{
			position = 0;
		}

		if (position >= 0 && position < count)
		{
			mVpgContent.setCurrentItem(position, true);
		}
	}

	public void startPlay()
	{
		mIsNeedPlay = true;
		startPlayAuto();
	}

	private void startPlayAuto()
	{
		if (mAdapter == null)
		{
			return;
		}

		if (!mIsNeedPlay)
		{
			return;
		}

		if (mAdapter.getCount() <= 1)
		{
			return;
		}

		mIsPlaying = true;
		mTimer.startWork(DEFAULT_PLAY_SPAN, DEFAULT_PLAY_SPAN, new SDTimerListener()
		{

			@Override
			public void onWorkMain()
			{
				if (mAdapter.getCount() <= 1)
				{
					stopPlay();
					return;
				}
				setCurrentItem(mVpgContent.getCurrentItem() + 1);
			}

			@Override
			public void onWork()
			{

			}
		});
	}

	public void stopPlay()
	{
		mIsPlaying = false;
		mTimer.stopWork();
	}

	public interface SDSlidingPlayViewOnItemClickListener
	{
		public void onItemClick(View v, int position);
	}

	public interface SDSlidingPlayViewOnPageChangeListener
	{
		public void onPageScrollStateChanged(int state);

		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

		public void onPageSelected(int position);
	}

	public interface SDSlidingPlayViewOnTouchListener
	{
		public void onTouch(View v, MotionEvent event);

		public void onDown(View v, MotionEvent event);

		public void onMove(View v, MotionEvent event);

		public void onUp(View v, MotionEvent event);
	}

	public enum EnumMode
	{
		NUMBER, IMAGE;
	}

}

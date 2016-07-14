package com.fanwe.library.span.utils;

import android.os.Handler;
import android.os.Looper;

public class SDHandlerLooper
{
	private static final long DELAY_DEFAULT = 200;

	private final Handler mHandler = new Handler(Looper.getMainLooper());
	private SDHandlerLooperListener mListener;
	private long mDelay = DELAY_DEFAULT;

	public void start(SDHandlerLooperListener listener)
	{
		start(DELAY_DEFAULT, listener);
	}

	public void start(long delay, SDHandlerLooperListener listener)
	{
		removeCallback();
		this.mListener = listener;
		this.mDelay = delay;
		mHandler.post(mRunnable);
	}

	public void stop()
	{
		removeCallback();
	}

	private void removeCallback()
	{
		mHandler.removeCallbacks(mRunnable);
	}

	private Runnable mRunnable = new Runnable()
	{

		@Override
		public void run()
		{
			if (mListener != null)
			{
				mListener.run();
			}
			mHandler.postDelayed(this, mDelay);
		}
	};

	public interface SDHandlerLooperListener
	{
		public void run();
	}

}

package com.fanwe.library.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class SDHandlerLooper
{
	private static final int MSG_WHAT = 19900413;

	private static final long INTERVAL_DEFAULT = 200;

	private SDHandlerLooperListener listener;
	private long interval = INTERVAL_DEFAULT;
	private boolean isRunning = false;
	private boolean isNeedStop = false;
	private int loopCount = -1;

	private final Handler handler = new Handler(Looper.getMainLooper())
	{
		public void handleMessage(Message msg)
		{
			if (isNeedStop)
			{

			} else
			{
				if (listener != null)
				{
					if (msg.what == MSG_WHAT)
					{
						loopCount++;
						listener.run();
						handler.sendEmptyMessageDelayed(MSG_WHAT, interval);
					}
				}
			}
		};
	};

	public boolean isRunning()
	{
		return isRunning;
	}

	public int getLoopCount()
	{
		return loopCount;
	}

	public void start(SDHandlerLooperListener listener)
	{
		start(INTERVAL_DEFAULT, listener);
	}

	public void start(long interval, SDHandlerLooperListener listener)
	{
		start(interval, 0, listener);
	}

	/**
	 * 开始循环触发回调
	 * 
	 * @param interval
	 *            触发间隔
	 * @param delay
	 *            第一次触发延迟
	 * @param listener
	 */
	public void start(long interval, long delay, SDHandlerLooperListener listener)
	{
		if (isRunning)
		{
			return;
		}
		this.isRunning = true;
		this.isNeedStop = false;
		this.listener = listener;
		this.interval = interval;

		if (interval <= 0)
		{
			interval = INTERVAL_DEFAULT;
		}
		if (delay < 0)
		{
			delay = 0;
		}

		handler.sendEmptyMessageDelayed(MSG_WHAT, delay);
	}

	public void stop()
	{
		handler.removeMessages(MSG_WHAT);
		isRunning = false;
		isNeedStop = true;
		loopCount = -1;
	}

	public interface SDHandlerLooperListener
	{
		public void run();
	}

}

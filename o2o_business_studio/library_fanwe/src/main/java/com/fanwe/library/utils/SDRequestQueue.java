package com.fanwe.library.utils;

import java.util.concurrent.LinkedBlockingQueue;

import com.fanwe.library.utils.SDHandlerLooper;
import com.fanwe.library.utils.SDHandlerLooper.SDHandlerLooperListener;

public abstract class SDRequestQueue<T>
{
	private LinkedBlockingQueue<T> queueRequest = new LinkedBlockingQueue<T>();
	private SDHandlerLooper handlerLooper = new SDHandlerLooper();
	private boolean isBusy = false;

	public final void offer(T t)
	{
		if (t != null)
		{
			queueRequest.offer(t);
		}
	}

	public final T poll()
	{
		return queueRequest.poll();
	}

	public final void clear()
	{
		queueRequest.clear();
	}

	public final void start(long interval)
	{
		if (handlerLooper.isRunning())
		{
			return;
		}
		handlerLooper.start(interval, new SDHandlerLooperListener()
		{
			@Override
			public void run()
			{
				if (!isBusy)
				{
					T t = poll();
					if (t == null)
					{
						stop();
					} else
					{
						isBusy = true;
						startRequest(t);
					}
				}
			}
		});
	}

	public abstract void startRequest(T t);

	public final void stop()
	{
		handlerLooper.stop();
	}

	public final boolean isBusy()
	{
		return isBusy;
	}

	public final void setBusy(boolean isBusy)
	{
		this.isBusy = isBusy;
	}
}

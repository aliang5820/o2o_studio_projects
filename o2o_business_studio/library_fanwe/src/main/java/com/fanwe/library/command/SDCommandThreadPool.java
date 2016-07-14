package com.fanwe.library.command;

public class SDCommandThreadPool
{
	private static SDCommandThreadPool mInstance;
	private int mMaxThreadCount = 1;
	private SDCommandThread mArrThread[] = null;
	private boolean started = false;

	private SDCommandThreadPool()
	{
	}

	public static SDCommandThreadPool getInstance()
	{
		if (mInstance == null)
		{
			mInstance = new SDCommandThreadPool();
		}
		return mInstance;
	}

	public void start()
	{
		if (!started)
		{
			mArrThread = new SDCommandThread[mMaxThreadCount];
			for (int i = 0; i < mMaxThreadCount; i++)
			{
				mArrThread[i] = new SDCommandThread(i);
				mArrThread[i].start();
			}
			started = true;
		}
	}

	public void shutdown()
	{
		if (started)
		{
			for (SDCommandThread thread : mArrThread)
			{
				thread.stop();
			}
			mArrThread = null;
			started = false;
		}
	}
}

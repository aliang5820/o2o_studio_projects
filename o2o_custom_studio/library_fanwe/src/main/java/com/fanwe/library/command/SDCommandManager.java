package com.fanwe.library.command;

public final class SDCommandManager
{
	private static SDCommandManager mInstance;
	private boolean mInitialized = false;
	private SDCommandThreadPool mPool;
	private SDCommandQueue mQueue;

	private SDCommandManager()
	{
	}

	public static SDCommandManager getInstance()
	{
		if (mInstance == null)
		{
			mInstance = new SDCommandManager();
		}
		return mInstance;
	}

	public void initialize()
	{
		if (!mInitialized)
		{
			mQueue = new SDCommandQueue();
			mPool = SDCommandThreadPool.getInstance();

			mPool.start();
			mInitialized = true;
		}
	}

	public SDCommand take()
	{
		SDCommand cmd = mQueue.take();
		return cmd;
	}

	public void add(SDCommand cmd)
	{
		mQueue.add(cmd);
	}

	public void clear()
	{
		mQueue.clear();
	}

	public void shutdown()
	{
		if (mInitialized)
		{
			mQueue.clear();
			mPool.shutdown();
			mInitialized = false;
		}
	}
}

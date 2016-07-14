package com.fanwe.library.command;

import java.util.concurrent.LinkedBlockingQueue;

public class SDCommandQueue
{
	private LinkedBlockingQueue<SDCommand> mQueue = new LinkedBlockingQueue<SDCommand>();

	public void add(SDCommand cmd)
	{
		mQueue.add(cmd);
	}

	public synchronized SDCommand take()
	{
		SDCommand cmd = null;
		try
		{
			cmd = mQueue.take();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return cmd;
	}

	public synchronized void clear()
	{
		mQueue.clear();
	}
}

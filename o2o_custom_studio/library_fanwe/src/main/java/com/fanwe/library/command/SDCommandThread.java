package com.fanwe.library.command;

public class SDCommandThread implements Runnable
{
	private int threadId;
	private Thread thread = null;
	private boolean running = false;
	private boolean stop = false;

	public SDCommandThread(int threadId)
	{
		this.threadId = threadId;
		thread = new Thread(this);
	}

	public void run()
	{
		while (!stop)
		{
			SDCommand cmd = SDCommandManager.getInstance().take();
			cmd.run();
		}
	}

	public void start()
	{
		thread.start();
		running = true;
	}

	public void stop()
	{
		stop = true;
		running = false;
	}

	public boolean isRunning()
	{
		return running;
	}

	public int getThreadId()
	{
		return threadId;
	}
}

package com.fanwe.library.command;

import com.fanwe.library.utils.SDHandlerUtil;

public abstract class SDCommand implements Runnable
{

	private SDRequest mRequest;
	private SDResponse mResponse;
	private SDResponseListener mListener;

	public SDRequest getmRequest()
	{
		return mRequest;
	}

	public void setmRequest(SDRequest mRequest)
	{
		this.mRequest = mRequest;
	}

	public SDResponse getmResponse()
	{
		return mResponse;
	}

	public void setmResponse(SDResponse mResponse)
	{
		this.mResponse = mResponse;
	}

	public SDResponseListener getmListener()
	{
		return mListener;
	}

	public void setmListener(SDResponseListener mListener)
	{
		this.mListener = mListener;
	}

	@Override
	public void run()
	{
		try
		{
			beforeRun();
			onRun();
			afterRun();
		} catch (Exception e)
		{
			onException(e);
		}
	}

	protected void onException(Exception e)
	{

	}

	public abstract void onRun();

	protected void notifySuccess()
	{
		if (mListener != null)
		{
			SDHandlerUtil.runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					mListener.onSuccess(getmResponse());
				}
			});
		}
	}

	protected void notifyFailure()
	{
		if (mListener != null)
		{
			SDHandlerUtil.runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					mListener.onFailure(getmResponse());
				}
			});
		}
	}

	private void beforeRun()
	{
		notifyStart();
	}

	private void notifyStart()
	{
		if (mListener != null)
		{
			SDHandlerUtil.runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					mListener.onStart();
				}
			});
		}
	}

	private void afterRun()
	{
		notifyFinish();
	}

	private void notifyFinish()
	{
		if (mListener != null)
		{
			SDHandlerUtil.runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					mListener.onFinish();
				}
			});
		}
	}
}

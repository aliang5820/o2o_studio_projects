package com.fanwe.library.command;

public class SDResponse
{

	private Object mTag;
	private Object mData;

	public SDResponse()
	{
	}

	public SDResponse(Object mTag, Object mData)
	{
		this.mTag = mTag;
		this.mData = mData;
	}

	public Object getmTag()
	{
		return mTag;
	}

	public void setmTag(Object mTag)
	{
		this.mTag = mTag;
	}

	public Object getmData()
	{
		return mData;
	}

	public void setmData(Object mData)
	{
		this.mData = mData;
	}

}

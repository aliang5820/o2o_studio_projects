package com.fanwe.model;

public class RuntimeSettingModel
{
	private int canLoadImage = 1;
	private int canPushMessage = 1;

	public int getCanLoadImage()
	{
		return canLoadImage;
	}

	public void setCanLoadImage(int canLoadImage)
	{
		this.canLoadImage = canLoadImage;
	}

	public int getCanPushMessage()
	{
		return canPushMessage;
	}

	public void setCanPushMessage(int canPushMessage)
	{
		this.canPushMessage = canPushMessage;
	}

}

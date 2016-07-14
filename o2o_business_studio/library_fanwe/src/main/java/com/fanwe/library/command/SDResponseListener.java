package com.fanwe.library.command;

public interface SDResponseListener
{

	public void onStart();

	public void onSuccess(SDResponse response);

	public void onFailure(SDResponse response);

	public void onFinish();

}

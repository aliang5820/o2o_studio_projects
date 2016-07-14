package com.fanwe.library.listener;

import android.view.KeyEvent;

public class SDOnKeyDownHandler
{

	private SDOnKeyDownHandlerListener mListener;

	public void setmListener(SDOnKeyDownHandlerListener mListener)
	{
		this.mListener = mListener;
	}

	public boolean handle(int keyCode, KeyEvent event)
	{
		if (mListener != null)
		{
			if (mListener.onKeyDown(keyCode, event))
			{
				return true;
			} else
			{
				switch (keyCode)
				{
				case KeyEvent.KEYCODE_BACK:
					return mListener.onKeyDown_back(keyCode, event);
				case KeyEvent.KEYCODE_MENU:
					return mListener.onKeyDown_menu(keyCode, event);
				case KeyEvent.KEYCODE_HOME:
					return mListener.onKeyDown_home(keyCode, event);
				default:
					return false;
				}
			}
		} else
		{
			return false;
		}
	}

	public interface SDOnKeyDownHandlerListener
	{
		public boolean onKeyDown_back(int keyCode, KeyEvent event);

		public boolean onKeyDown_menu(int keyCode, KeyEvent event);

		public boolean onKeyDown_home(int keyCode, KeyEvent event);

		public boolean onKeyDown(int keyCode, KeyEvent event);
	}

}

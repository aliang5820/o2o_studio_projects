package com.fanwe.library.utils;

import java.util.ArrayList;
import java.util.List;

public class ObserverManager<T>
{
	private List<T> mListObserver = new ArrayList<T>();

	public void register(T observer)
	{
		if (observer != null)
		{
			mListObserver.add(observer);
		}
	}

	public void unregister(T observer)
	{
		if (observer != null)
		{
			mListObserver.remove(observer);
		}
	}

	public List<T> getObserver()
	{
		return mListObserver;
	}

}

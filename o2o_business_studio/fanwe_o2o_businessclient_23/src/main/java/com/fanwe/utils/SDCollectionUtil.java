package com.fanwe.utils;

import java.util.List;

public class SDCollectionUtil
{
	/**
	 * 如果list有数据返回true
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isListHasData(List list)
	{
		if (list != null && !list.isEmpty())
		{
			return true;
		} else
		{
			return false;
		}
	}

}

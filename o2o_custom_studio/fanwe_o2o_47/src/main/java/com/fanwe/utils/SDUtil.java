package com.fanwe.utils;

public class SDUtil
{

	public static void swap(Object first, Object second)
	{
		if (first != null && second != null)
		{
			Object temp = first;
			first = second;
			second = temp;
		}
	}

}

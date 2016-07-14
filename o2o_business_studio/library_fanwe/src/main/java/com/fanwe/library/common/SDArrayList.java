package com.fanwe.library.common;

import java.util.ArrayList;

public class SDArrayList<E> extends ArrayList<E>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public E get(int index)
	{
		E obj = null;
		try
		{
			obj = super.get(index);
		} catch (Exception e)
		{
			// TODO: 不处理
		}
		return obj;
	}

}

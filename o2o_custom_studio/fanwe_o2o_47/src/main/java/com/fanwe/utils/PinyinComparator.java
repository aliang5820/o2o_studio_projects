package com.fanwe.utils;

import java.util.Comparator;

import com.fanwe.model.CitylistModel;

public class PinyinComparator implements Comparator<CitylistModel>
{

	public int compare(CitylistModel o1, CitylistModel o2)
	{
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#"))
		{
			return -1;
		} else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@"))
		{
			return 1;
		} else
		{
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}

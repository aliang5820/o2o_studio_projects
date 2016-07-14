package com.fanwe.model;

import java.io.Serializable;

import android.text.TextUtils;

import com.fanwe.library.utils.CharacterParser;

public class CitylistModel implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String py;
	// ===========add
	private String sortLetters;

	public String getPy()
	{
		return py;
	}

	public void setPy(String py)
	{
		this.py = py;
	}

	public String getSortLetters()
	{
		if (TextUtils.isEmpty(sortLetters))
		{
			findSortLetters();
		}
		return sortLetters;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	private void findSortLetters()
	{
		String firstLetters = null;
		if (!TextUtils.isEmpty(py) && py.length() > 1)
		{
			firstLetters = py.substring(0, 1).toUpperCase();
		} else
		{
			firstLetters = CharacterParser.getFirstLetters_A2Z(name);
		}

		if (TextUtils.isEmpty(firstLetters))
		{
			firstLetters = "#";
		}
		sortLetters = firstLetters;
	}

}

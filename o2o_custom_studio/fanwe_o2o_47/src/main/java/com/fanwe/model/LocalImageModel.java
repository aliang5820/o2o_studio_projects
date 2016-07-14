package com.fanwe.model;

import java.io.Serializable;

import android.text.TextUtils;

import com.fanwe.library.adapter.SDAdapter.SDSelectable;

public class LocalImageModel implements Serializable, SDSelectable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String path;

	private String pathLoad;

	private boolean selected;

	public String getPathLoad()
	{
		return pathLoad;
	}

	public void setPathLoad(String pathLoad)
	{
		this.pathLoad = pathLoad;
	}

	public boolean isAddImage()
	{
		return path == null;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
		if (!TextUtils.isEmpty(path))
		{
			this.pathLoad = "file://" + path;
		}
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean isSelected)
	{
		this.selected = isSelected;
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == null)
		{
			return false;
		}

		if (!(other instanceof LocalImageModel))
		{
			return false;
		}

		LocalImageModel otherModel = (LocalImageModel) other;
		String otherPath = otherModel.getPath();
		if (TextUtils.isEmpty(otherPath))
		{
			return false;
		}

		if (!otherPath.equals(path))
		{
			return false;
		}

		return true;
	}

}

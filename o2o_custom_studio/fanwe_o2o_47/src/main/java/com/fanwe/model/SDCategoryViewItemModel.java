package com.fanwe.model;

public class SDCategoryViewItemModel<T>
{

	private boolean isSelect = false;
	private T data;

	public SDCategoryViewItemModel()
	{
		super();
	}

	public SDCategoryViewItemModel(T data)
	{
		super();
		this.data = data;
	}

	public SDCategoryViewItemModel(boolean isSelect, T data)
	{
		super();
		this.isSelect = isSelect;
		this.data = data;
	}

	public boolean isSelect()
	{
		return isSelect;
	}

	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}

	public T getData()
	{
		return data;
	}

	public void setData(T data)
	{
		this.data = data;
	}

}

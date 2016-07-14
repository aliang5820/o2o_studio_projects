package com.fanwe.model;

import java.util.List;

import android.view.View;

public class Event_fieldsModel
{
	private int id;
	private int event_id;
	private String field_show_name; // 字段名
	private int field_type; // 0 输入框 ，1 下拉框

	private List<String> value_scope;
	private int sort;
	private String result; // 用户输入/选中的值

	// add
	private View view;
	private String fieldValue;

	public String getFieldValue()
	{
		return fieldValue;
	}

	public void setFieldValue(String fieldValue)
	{
		this.fieldValue = fieldValue;
	}

	public View getView()
	{
		return view;
	}

	public void setView(View view)
	{
		this.view = view;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getEvent_id()
	{
		return event_id;
	}

	public void setEvent_id(int event_id)
	{
		this.event_id = event_id;
	}

	public String getField_show_name()
	{
		return field_show_name;
	}

	public void setField_show_name(String field_show_name)
	{
		this.field_show_name = field_show_name;
	}

	public int getField_type()
	{
		return field_type;
	}

	public void setField_type(int field_type)
	{
		this.field_type = field_type;
	}

	public List<String> getValue_scope()
	{
		return value_scope;
	}

	public void setValue_scope(List<String> value_scope)
	{
		this.value_scope = value_scope;
	}

	public int getSort()
	{
		return sort;
	}

	public void setSort(int sort)
	{
		this.sort = sort;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

}

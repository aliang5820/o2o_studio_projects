package com.fanwe.model;

import com.fanwe.library.adapter.SDAdapter.SDSelectable;

public class OrderCoupon_listModel implements SDSelectable
{

	private int id;
	private String password; // 团购券序列号
	private int deal_type; // 0按件发券 1按单发券，为1时显示，共可消费item[number]位
	private String time_str; // 时间状态
	private String status_str; // 时间状态
	private int is_refund; // 是否允许退款（出现退款勾选项） 0否 1是

	// add
	private boolean selected = true;
	private String number;

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getConsumeString()
	{
		String content = null;
		if (deal_type == 1)
		{
			content = "共可消费" + number + "位";
		}
		return content;
	}

	public void toggleSelected()
	{
		selected = !selected;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public int getDeal_type()
	{
		return deal_type;
	}

	public void setDeal_type(int deal_type)
	{
		this.deal_type = deal_type;
	}

	public String getTime_str()
	{
		return time_str;
	}

	public void setTime_str(String time_str)
	{
		this.time_str = time_str;
	}

	public String getStatus_str()
	{
		return status_str;
	}

	public void setStatus_str(String status_str)
	{
		this.status_str = status_str;
	}

	public int getIs_refund()
	{
		return is_refund;
	}

	public void setIs_refund(int is_refund)
	{
		this.is_refund = is_refund;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean isSelected)
	{
		this.selected = isSelected;
	}

}

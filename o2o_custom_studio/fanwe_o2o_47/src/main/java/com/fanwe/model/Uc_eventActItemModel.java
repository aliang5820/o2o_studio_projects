package com.fanwe.model;

public class Uc_eventActItemModel
{
	private int id;
	private String name;
	private String event_sn;
	private String event_end_time; // 活动结束时间
	private String confirm_time; // 活动验证时间
	private String icon;
	private String qrcode; // 二维码

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

	public String getEvent_sn()
	{
		return event_sn;
	}

	public void setEvent_sn(String event_sn)
	{
		this.event_sn = event_sn;
	}

	public String getEvent_end_time()
	{
		return event_end_time;
	}

	public void setEvent_end_time(String event_end_time)
	{
		this.event_end_time = event_end_time;
	}

	public String getConfirm_time()
	{
		return confirm_time;
	}

	public void setConfirm_time(String confirm_time)
	{
		this.confirm_time = confirm_time;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getQrcode()
	{
		return qrcode;
	}

	public void setQrcode(String qrcode)
	{
		this.qrcode = qrcode;
	}

}

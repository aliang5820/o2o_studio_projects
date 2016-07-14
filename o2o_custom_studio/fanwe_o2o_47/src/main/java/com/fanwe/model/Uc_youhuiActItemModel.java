package com.fanwe.model;

public class Uc_youhuiActItemModel
{
	private int id;
	private String name; // 优惠券名称
	private String youhui_sn; // 优惠券SN
	private String expire_time; // 有效日期
	private String confirm_time; // 使用时间
	private String icon; // 优惠券图标
	private String qrcode; // 优惠券二维码

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getYouhui_sn()
	{
		return youhui_sn;
	}

	public void setYouhui_sn(String youhui_sn)
	{
		this.youhui_sn = youhui_sn;
	}

	public String getExpire_time()
	{
		return expire_time;
	}

	public void setExpire_time(String expire_time)
	{
		this.expire_time = expire_time;
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

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

}
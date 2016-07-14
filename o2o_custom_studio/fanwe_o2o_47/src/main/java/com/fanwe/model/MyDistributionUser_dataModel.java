package com.fanwe.model;

import com.fanwe.utils.SDFormatUtil;

public class MyDistributionUser_dataModel
{
	private String user_name; // 用户名
	private String fx_money; // 总佣金
	private String user_avatar;
	private String share_mall_qrcode;
	private String share_mall_url;
	private String fx_mall_bg;

	// add
	private String fx_moneyFormat;
	private String userStoreName;

	public String getUserStoreName()
	{
		return userStoreName;
	}

	public void setUserStoreName(String userStoreName)
	{
		this.userStoreName = userStoreName;
	}

	public String getShare_mall_qrcode()
	{
		return share_mall_qrcode;
	}

	public void setShare_mall_qrcode(String share_mall_qrcode)
	{
		this.share_mall_qrcode = share_mall_qrcode;
	}

	public String getShare_mall_url()
	{
		return share_mall_url;
	}

	public void setShare_mall_url(String share_mall_url)
	{
		this.share_mall_url = share_mall_url;
	}

	public String getFx_moneyFormat()
	{
		return fx_moneyFormat;
	}

	public void setFx_moneyFormat(String fx_moneyFormat)
	{
		this.fx_moneyFormat = fx_moneyFormat;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
		this.userStoreName = user_name + "的小店";
	}

	public String getFx_money()
	{
		return fx_money;
	}

	public void setFx_money(String fx_money)
	{
		this.fx_money = fx_money;
		this.fx_moneyFormat = SDFormatUtil.formatMoneyChina(fx_money);
	}

	public String getUser_avatar()
	{
		return user_avatar;
	}

	public void setUser_avatar(String user_avatar)
	{
		this.user_avatar = user_avatar;
	}

	public String getFx_mall_bg()
	{
		return fx_mall_bg;
	}

	public void setFx_mall_bg(String fx_mall_bg)
	{
		this.fx_mall_bg = fx_mall_bg;
	}

}

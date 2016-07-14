package com.fanwe.model;

public class WalipayModel
{

	private String subject = null;
	private String body = null;
	private String total_fee = null;
	private String total_fee_format = null;
	private String out_trade_no = null;
	private String notify_url = null;
	private String partner = null;
	private String seller = null;
	private String key = null;
	private String pay_code = null;

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public String getTotal_fee()
	{
		return total_fee;
	}

	public void setTotal_fee(String total_fee)
	{
		this.total_fee = total_fee;
	}

	public String getTotal_fee_format()
	{
		return total_fee_format;
	}

	public void setTotal_fee_format(String total_fee_format)
	{
		this.total_fee_format = total_fee_format;
	}

	public String getOut_trade_no()
	{
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no)
	{
		this.out_trade_no = out_trade_no;
	}

	public String getNotify_url()
	{
		return notify_url;
	}

	public void setNotify_url(String notify_url)
	{
		this.notify_url = notify_url;
	}

	public String getPartner()
	{
		return partner;
	}

	public void setPartner(String partner)
	{
		this.partner = partner;
	}

	public String getSeller()
	{
		return seller;
	}

	public void setSeller(String seller)
	{
		this.seller = seller;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getPay_code()
	{
		return pay_code;
	}

	public void setPay_code(String pay_code)
	{
		this.pay_code = pay_code;
	}

}

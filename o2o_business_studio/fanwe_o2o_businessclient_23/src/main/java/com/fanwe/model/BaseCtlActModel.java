package com.fanwe.model;

public class BaseCtlActModel
{
	protected String act;
	protected int status = -1;
	protected String info;
	protected String sess_id;
	protected String ctl;
	protected int biz_login_status = -1;
	protected String page_title;
	protected int is_auth;
	
	public int getIs_auth()
	{
		return is_auth;
	}

	public void setIs_auth(int is_auth)
	{
		this.is_auth = is_auth;
	}

	public String getPage_title()
	{
		return page_title;
	}

	public void setPage_title(String page_title)
	{
		this.page_title = page_title;
	}

	public int getBiz_login_status()
	{
		return biz_login_status;
	}

	public void setBiz_login_status(int biz_login_status)
	{
		this.biz_login_status = biz_login_status;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public String getAct()
	{
		return act;
	}

	public void setAct(String act)
	{
		this.act = act;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getSess_id()
	{
		return sess_id;
	}

	public void setSess_id(String sess_id)
	{
		this.sess_id = sess_id;
	}

	public String getCtl()
	{
		return ctl;
	}

	public void setCtl(String ctl)
	{
		this.ctl = ctl;
	}

}

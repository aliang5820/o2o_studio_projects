package com.fanwe.model;

public class BaseActModel
{
	protected String ctl;
	protected String act;
	protected String info;
	protected int status;
	protected String sess_id;
	protected int biz_login_status = 999;
	protected String page_title;
	protected String ref_uid;
	protected int is_auth;

	public int getIs_auth()
	{
		return is_auth;
	}

	public void setIs_auth(int is_auth)
	{
		this.is_auth = is_auth;
	}

	public String getRef_uid()
	{
		return ref_uid;
	}

	public void setRef_uid(String ref_uid)
	{
		this.ref_uid = ref_uid;
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

	public String getSess_id()
	{
		return sess_id;
	}

	public void setSess_id(String sess_id)
	{
		this.sess_id = sess_id;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String show_err)
	{
		this.info = show_err;
	}

	public String getCtl()
	{
		return ctl;
	}

	public void setCtl(String ctl)
	{
		this.ctl = ctl;
	}

	public String getAct()
	{
		return act;
	}

	public void setAct(String act)
	{
		this.act = act;
	}

}

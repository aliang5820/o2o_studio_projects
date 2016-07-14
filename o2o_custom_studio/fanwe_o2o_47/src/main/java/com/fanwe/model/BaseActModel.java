package com.fanwe.model;

import android.text.TextUtils;

import com.fanwe.library.utils.SDToast;

public class BaseActModel
{
	protected String ctl;
	protected String act;
	protected String info;
	protected int status;
	protected String sess_id;
	protected int user_login_status = 999;
	protected String page_title;
	protected String ref_uid;

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

	public int getUser_login_status()
	{
		return user_login_status;
	}

	public void setUser_login_status(int user_login_status)
	{
		this.user_login_status = user_login_status;
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

	public void showToast()
	{
		if (!TextUtils.isEmpty(info))
		{
			SDToast.showToast(info);
		}
	}

}

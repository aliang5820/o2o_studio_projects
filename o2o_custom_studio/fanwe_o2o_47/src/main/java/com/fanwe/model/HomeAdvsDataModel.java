package com.fanwe.model;

import java.util.List;

public class HomeAdvsDataModel
{
	private String url = null;
	private String cid = null;
	private List<String> tags = null;
	private String share_id = null;
	private String count = null;
	private String cate_id = null;
	private String cate_name = null;
	private String data_id = null;

	public String getData_id()
	{
		return data_id;
	}

	public void setData_id(String data_id)
	{
		this.data_id = data_id;
	}

	public String getCate_id()
	{
		return cate_id;
	}

	public void setCate_id(String cate_id)
	{
		this.cate_id = cate_id;
	}

	public String getCate_name()
	{
		return cate_name;
	}

	public void setCate_name(String cate_name)
	{
		this.cate_name = cate_name;
	}

	public String getCount()
	{
		return count;
	}

	public void setCount(String count)
	{
		this.count = count;
	}

	public String getShare_id()
	{
		return share_id;
	}

	public void setShare_id(String share_id)
	{
		this.share_id = share_id;
	}

	public String getCid()
	{
		return cid;
	}

	public void setCid(String cid)
	{
		this.cid = cid;
	}

	public List<String> getTags()
	{
		return tags;
	}

	public void setTags(List<String> tags)
	{
		this.tags = tags;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

}
package com.fanwe.model;

import java.io.Serializable;

public class AdvsDataModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String url; // url
	private int cate_id; // 大分类id
	private int tid; // 小分类id
	private int qid; // 圈id
	private int bid; // 品牌id
	private int data_id; // 数据id

	public int getData_id()
	{
		return data_id;
	}

	public void setData_id(int data_id)
	{
		this.data_id = data_id;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public int getCate_id()
	{
		return cate_id;
	}

	public void setCate_id(int cate_id)
	{
		this.cate_id = cate_id;
	}

	public int getTid()
	{
		return tid;
	}

	public void setTid(int tid)
	{
		this.tid = tid;
	}

	public int getQid()
	{
		return qid;
	}

	public void setQid(int qid)
	{
		this.qid = qid;
	}

	public int getBid()
	{
		return bid;
	}

	public void setBid(int bid)
	{
		this.bid = bid;
	}

}

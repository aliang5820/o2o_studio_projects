package com.fanwe.model;

import java.io.Serializable;

import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.utils.SDDistanceUtil;

public class EventModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private int cid; // 收藏记录id
	private String name;
	private String icon;
	private String submit_begin_time_format;
	private String submit_end_time_format;
	private String sheng_time_format;
	private double xpoint;
	private double ypoint;
	private int submit_count; // 报名人数
	private int total_count; // 活动名额
	private String brief; // 简介
	private double distance;
	private String event_begin_time;
	private String event_end_time;
	private String submit_begin_time;
	private String submit_end_time;
	private String score_limit;// 所需积分
	private String point_limit;// 所需经验
	private String address;

	// ===============add
	private String submit_countFormat;
	private String distanceFormat;

	public double getXpoint()
	{
		return xpoint;
	}

	public void setXpoint(double xpoint)
	{
		this.xpoint = xpoint;
		calculateDistance();
	}

	public double getYpoint()
	{
		return ypoint;
	}

	public void setYpoint(double ypoint)
	{
		this.ypoint = ypoint;
		calculateDistance();
	}

	public void calculateDistance()
	{
		double dis = 0;
		if (xpoint != 0 && ypoint != 0)
		{
			dis = BaiduMapManager.getInstance().getDistanceFromMyLocation(ypoint, xpoint);
		}
		this.distanceFormat = SDDistanceUtil.getFormatDistance(dis);
	}

	public void setDistance(double distance)
	{
		this.distance = distance;
	}

	public double getDistance()
	{
		return distance;
	}

	public String getDistanceFormat()
	{
		return distanceFormat;
	}

	public String getBrief()
	{
		return brief;
	}

	public void setBrief(String brief)
	{
		this.brief = brief;
	}

	public int getCid()
	{
		return cid;
	}

	public void setCid(int cid)
	{
		this.cid = cid;
	}

	public String getSubmit_countFormat()
	{
		return submit_countFormat;
	}

	public void setSubmit_countFormat(String submit_countFormat)
	{
		this.submit_countFormat = submit_countFormat;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getSubmit_begin_time_format()
	{
		return submit_begin_time_format;
	}

	public void setSubmit_begin_time_format(String submit_begin_time_format)
	{
		this.submit_begin_time_format = submit_begin_time_format;
	}

	public String getSubmit_end_time_format()
	{
		return submit_end_time_format;
	}

	public void setSubmit_end_time_format(String submit_end_time_format)
	{
		this.submit_end_time_format = submit_end_time_format;
	}

	public String getSheng_time_format()
	{
		return sheng_time_format;
	}

	public void setSheng_time_format(String sheng_time_format)
	{
		this.sheng_time_format = sheng_time_format;
	}

	public String getSubmitTimeFormat()
	{
		return "报名时间:" + submit_begin_time + "至" + submit_end_time;
	}

	public String getEventTimeFormat()
	{
		return "活动时间:" + event_begin_time + "至" + event_end_time;
	}

	public String getEventCountFormat()
	{
		String strTotalCount = "";
		if (this.total_count == 0)
		{
			strTotalCount = "无限制";
		} else
		{
			strTotalCount = String.valueOf(this.total_count);
		}
		return "活动名额" + strTotalCount + "，已有" + submit_count + "人报名";
	}

	public int getTotal_count()
	{
		return total_count;
	}

	public void setTotal_count(int total_count)
	{
		this.total_count = total_count;
	}

	public String getScore_limit()
	{
		return score_limit;
	}

	public void setScore_limit(String score_limit)
	{
		this.score_limit = score_limit;
	}

	public String getPoint_limit()
	{
		return point_limit;
	}

	public void setPoint_limit(String point_limit)
	{
		this.point_limit = point_limit;
	}

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

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getEvent_begin_time()
	{
		return event_begin_time;
	}

	public void setEvent_begin_time(String event_begin_time)
	{
		this.event_begin_time = event_begin_time;
	}

	public String getEvent_end_time()
	{
		return event_end_time;
	}

	public void setEvent_end_time(String event_end_time)
	{
		this.event_end_time = event_end_time;
	}

	public String getSubmit_begin_time()
	{
		return submit_begin_time;
	}

	public void setSubmit_begin_time(String submit_begin_time)
	{
		this.submit_begin_time = submit_begin_time;
	}

	public String getSubmit_end_time()
	{
		return submit_end_time;
	}

	public void setSubmit_end_time(String submit_end_time)
	{
		this.submit_end_time = submit_end_time;
	}

	public int getSubmit_count()
	{
		return submit_count;
	}

	public void setSubmit_count(int submit_count)
	{
		this.submit_count = submit_count;
		this.submit_countFormat = submit_count + "人报名";
	}

	public MapSearchBaseModel createMapSearchModel()
	{
		MapSearchBaseModel model = new MapSearchBaseModel();
		model.setId(id);
		model.setName(name);
		model.setXpoint(xpoint);
		model.setYpoint(ypoint);
		model.setAddress(address);
		return model;
	}

}
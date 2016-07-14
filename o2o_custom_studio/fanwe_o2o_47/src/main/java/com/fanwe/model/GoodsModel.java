package com.fanwe.model;

import java.io.Serializable;

import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.utils.SDDistanceUtil;
import com.fanwe.utils.SDFormatUtil;

public class GoodsModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private int cid; // 收藏记录id
	private String name; // 团购名称
	private String sub_name; // 团购短名称
	private String brief; // 团购简介
	private int buy_count; // 销量
	private double current_price; // 现价
	private double origin_price; // 原价
	private int deal_score; // 所需积分
	private String icon;
	private String end_time_format;
	private String begin_time_format;
	private int begin_time;
	private int end_time;
	private int auto_order;// 免预约 0:否 1:是
	private int is_lottery;// 是否抽奖 0:否 1:是
	private double xpoint; // 经度
	private double ypoint; // 纬度
	private int is_taday; // 是否为今日团购 0否 1是
	private int is_refund; // 是否随时退 0:否 1:是
	private String distance;
	private String address;

	// -----------add-----------
	private String distanceFormat;
	private String buy_countFormat;
	private String origin_price_format;
	private String current_price_format;
	private String deal_scoreFormat;

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getDistance()
	{
		return distance;
	}

	public void setDistance(String distance)
	{
		this.distance = distance;
	}

	public String getDistanceFormat()
	{
		return distanceFormat;
	}

	public int getCid()
	{
		return cid;
	}

	public void setCid(int cid)
	{
		this.cid = cid;
	}

	public String getDeal_scoreFormat()
	{
		return deal_scoreFormat;
	}

	public void setDeal_scoreFormat(String deal_scoreFormat)
	{
		this.deal_scoreFormat = deal_scoreFormat;
	}

	public int getDeal_score()
	{
		return deal_score;
	}

	public void setDeal_score(int deal_score)
	{
		this.deal_score = deal_score;
		this.deal_scoreFormat = deal_score + "积分";
	}

	public int getIs_refund()
	{
		return is_refund;
	}

	public void setIs_refund(int is_refund)
	{
		this.is_refund = is_refund;
	}

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

	public int getIs_lottery()
	{
		return is_lottery;
	}

	public void setIs_lottery(int is_lottery)
	{
		this.is_lottery = is_lottery;
	}

	public String getEnd_time_format()
	{
		return end_time_format;
	}

	public void setEnd_time_format(String end_time_format)
	{
		this.end_time_format = end_time_format;
	}

	public String getBegin_time_format()
	{
		return begin_time_format;
	}

	public void setBegin_time_format(String begin_time_format)
	{
		this.begin_time_format = begin_time_format;
	}

	public int getBegin_time()
	{
		return begin_time;
	}

	public void setBegin_time(int begin_time)
	{
		this.begin_time = begin_time;
	}

	public int getEnd_time()
	{
		return end_time;
	}

	public void setEnd_time(int end_time)
	{
		this.end_time = end_time;
	}

	public String getBrief()
	{
		return brief;
	}

	public void setBrief(String brief)
	{
		this.brief = brief;
	}

	public String getCurrent_price_format()
	{
		return current_price_format;
	}

	public void setCurrent_price_format(String current_price_format)
	{
		this.current_price_format = current_price_format;
	}

	public String getOrigin_price_format()
	{
		return origin_price_format;
	}

	public void setOrigin_price_format(String origin_price_format)
	{
		this.origin_price_format = origin_price_format;
	}

	public String getBuy_countFormat()
	{
		return buy_countFormat;
	}

	public void setBuy_countFormat(String buy_countFormat)
	{
		this.buy_countFormat = buy_countFormat;
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

	public String getSub_name()
	{
		return sub_name;
	}

	public void setSub_name(String sub_name)
	{
		this.sub_name = sub_name;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public double getOrigin_price()
	{
		return origin_price;
	}

	public void setOrigin_price(double origin_price)
	{
		this.origin_price = origin_price;
		this.origin_price_format = SDFormatUtil.formatMoneyChina(origin_price);
	}

	public double getCurrent_price()
	{
		return current_price;
	}

	public void setCurrent_price(double current_price)
	{
		this.current_price = current_price;
		this.current_price_format = SDFormatUtil.formatMoneyChina(current_price);
	}

	public int getBuy_count()
	{
		return buy_count;
	}

	public void setBuy_count(int buy_count)
	{
		this.buy_count = buy_count;
		this.buy_countFormat = "已售" + buy_count;
	}

	public int getAuto_order()
	{
		return auto_order;
	}

	public void setAuto_order(int auto_order)
	{
		this.auto_order = auto_order;
	}

	public int getIs_taday()
	{
		return is_taday;
	}

	public void setIs_taday(int is_taday)
	{
		this.is_taday = is_taday;
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
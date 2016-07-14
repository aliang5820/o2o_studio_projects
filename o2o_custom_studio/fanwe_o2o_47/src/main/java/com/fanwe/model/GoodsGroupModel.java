package com.fanwe.model;

import java.util.List;

import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.utils.SDDistanceUtil;

public class GoodsGroupModel
{

	private String preview;
	private int id;
	private int is_verify;
	private int is_youhui;
	private float avg_point;
	private String address;
	private String name;
	private double distance;
	private double xpoint;
	private double ypoint;
	private String tel;
	private List<GoodsModel> deal_data;

	// add
	private String avg_pointFormat;
	private String distanceFormat;
	private boolean isOpen;

	public boolean isOpen()
	{
		return isOpen;
	}

	public void setOpen(boolean isOpen)
	{
		this.isOpen = isOpen;
	}

	public int getIs_youhui()
	{
		return is_youhui;
	}

	public void setIs_youhui(int is_youhui)
	{
		this.is_youhui = is_youhui;
	}

	public String getDistanceFormat()
	{
		return distanceFormat;
	}

	public String getAvg_pointFormat()
	{
		return avg_pointFormat;
	}

	public String getPreview()
	{
		return preview;
	}

	public void setPreview(String preview)
	{
		this.preview = preview;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getIs_verify()
	{
		return is_verify;
	}

	public void setIs_verify(int is_verify)
	{
		this.is_verify = is_verify;
	}

	public float getAvg_point()
	{
		return avg_point;
	}

	public void setAvg_point(float avg_point)
	{
		this.avg_point = avg_point;
		this.avg_pointFormat = this.avg_point + "分";
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getDistance()
	{
		return distance;
	}

	public void setDistance(double distance)
	{
		this.distance = distance;
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

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public List<GoodsModel> getDeal_data()
	{
		return deal_data;
	}

	public void setDeal_data(List<GoodsModel> deal_data)
	{
		this.deal_data = deal_data;
	}

}

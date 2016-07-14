package com.fanwe.model;

import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.utils.SDDistanceUtil;

public class StoreModel
{
	private int id;
	private String preview;
	private int is_verify; // 是否为认证商户 0:否 1:是
	private String name;
	private float avg_point;
	private String address;
	private String tel;
	private String distance;
	private int deal_count;
	private int youhui_count;
	private double xpoint;
	private double ypoint;
	private String promote_description;

	// ===============add
	private String distanceFormat;

	public String getPromote_description()
	{
		return promote_description;
	}

	public void setPromote_description(String promote_description)
	{
		this.promote_description = promote_description;
	}

	public int getDeal_count()
	{
		return deal_count;
	}

	public void setDeal_count(int deal_count)
	{
		this.deal_count = deal_count;
	}

	public int getYouhui_count()
	{
		return youhui_count;
	}

	public void setYouhui_count(int youhui_count)
	{
		this.youhui_count = youhui_count;
	}

	public void setDistance(String distance)
	{
		this.distance = distance;
	}

	public String getDistance()
	{
		return distance;
	}

	public String getDistanceFormat()
	{
		return distanceFormat;
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

	public String getPreview()
	{
		return preview;
	}

	public void setPreview(String preview)
	{
		this.preview = preview;
	}

	public int getIs_verify()
	{
		return is_verify;
	}

	public void setIs_verify(int is_verify)
	{
		this.is_verify = is_verify;
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

	public float getAvg_point()
	{
		return avg_point;
	}

	public void setAvg_point(float avg_point)
	{
		this.avg_point = avg_point;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
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
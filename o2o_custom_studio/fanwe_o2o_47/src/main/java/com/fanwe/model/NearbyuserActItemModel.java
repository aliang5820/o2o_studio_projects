package com.fanwe.model;

import com.fanwe.dao.LocalUserModelDao;

public class NearbyuserActItemModel
{

	private String id;
	private String xpoint;
	private String ypoint;
	private String locate_time;
	private String user_name;
	private String daren_title;
	private String sex;
	private String distance;
	private int uid;
	private String fans;
	private String user_avatar;
	private int is_follow;
	private String locate_time_format;

	// add
	private String distanceFormat;
	private String isFollowFormat;

	public String getIs_follow_format()
	{
		return isFollowFormat;
	}

	public void setIs_follow_format(String is_follow_format)
	{
		this.isFollowFormat = is_follow_format;
	}

	public String getDistance_format()
	{
		return distanceFormat;
	}

	public void setDistance_format(String distance_format)
	{
		this.distanceFormat = distance_format;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getXpoint()
	{
		return xpoint;
	}

	public void setXpoint(String xpoint)
	{
		this.xpoint = xpoint;
	}

	public String getYpoint()
	{
		return ypoint;
	}

	public void setYpoint(String ypoint)
	{
		this.ypoint = ypoint;
	}

	public String getLocate_time()
	{
		return locate_time;
	}

	public void setLocate_time(String locate_time)
	{
		this.locate_time = locate_time;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public String getDaren_title()
	{
		return daren_title;
	}

	public void setDaren_title(String daren_title)
	{
		this.daren_title = daren_title;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getDistance()
	{
		return distance;
	}

	public void setDistance(String distance)
	{
		this.distance = distance;
		if (this.distance != null)
		{
			try
			{
				long dis = Math.round(Double.parseDouble(distance));
				if (dis > 5000)
				{
					this.distanceFormat = "大于5公里";
				} else
				{
					this.distanceFormat = distance + "米";
				}
			} catch (Exception e)
			{
				// TODO: handle exception
			}
		}
	}

	public int getUid()
	{
		return uid;
	}

	public void setUid(int uid)
	{
		this.uid = uid;
		setIs_follow_formatByData(this.is_follow, uid);
	}

	public String getFans()
	{
		return fans;
	}

	public void setFans(String fans)
	{
		this.fans = fans;
	}

	public String getUser_avatar()
	{
		return user_avatar;
	}

	public void setUser_avatar(String user_avatar)
	{
		this.user_avatar = user_avatar;
	}

	public int getIs_follow()
	{
		return is_follow;
	}

	public void setIs_follow(int is_follow)
	{
		this.is_follow = is_follow;
		setIs_follow_formatByData(is_follow, this.uid);
	}

	private void setIs_follow_formatByData(int is_follow, int uid)
	{
		LocalUserModel user = LocalUserModelDao.queryModel();
		if (user != null)
		{
			if (user.getUser_id() == uid)
			{
				this.isFollowFormat = "自己";
			} else
			{
				switch (is_follow)
				{
				case 0:
					this.isFollowFormat = "加关注";
					break;
				case 1:
					this.isFollowFormat = "取消关注";
					break;

				default:
					this.isFollowFormat = "未知";
					break;
				}
			}
		} else
		{
			this.isFollowFormat = "加关注";
		}
	}

	public String getLocate_time_format()
	{
		return locate_time_format;
	}

	public void setLocate_time_format(String locate_time_format)
	{
		this.locate_time_format = locate_time_format;
	}

}

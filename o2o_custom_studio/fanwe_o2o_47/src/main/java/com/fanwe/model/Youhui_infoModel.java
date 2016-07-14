package com.fanwe.model;

public class Youhui_infoModel
{
	private int id; // 优惠券ID
	private String name; // 优惠券名称
	private String icon; // 图片
	private long now_time; // 当前时间
	private long begin_time; // 开始时间
	private long end_time; // 结束时间
	private long last_time; // 最后剩下时间
	private int expire_day; // 领取后有效天数
	private int total_num; // 优惠券总数
	private int less; // 剩余数量
	private int is_effect; // 是否有效
	private int user_count; // 已经领取数量
	private int user_limit; // 用户每天最多领取数量
	private int score_limit; // 消耗积分
	private int point_limit; // 经验限制
	private String avg_point; // 点评平均分
	private String description; // 优惠券详情
	private String use_notice; // 使用须知
	private String last_time_format; // 格式化最后剩下时间
	private String supplier_info_name; // 商户主门店名称
	private String share_url;

	public String getShare_url()
	{
		return share_url;
	}

	public void setShare_url(String share_url)
	{
		this.share_url = share_url;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getUse_notice()
	{
		return use_notice;
	}

	public void setUse_notice(String use_notice)
	{
		this.use_notice = use_notice;
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

	public long getNow_time()
	{
		return now_time;
	}

	public void setNow_time(long now_time)
	{
		this.now_time = now_time;
	}

	public long getBegin_time()
	{
		return begin_time;
	}

	public void setBegin_time(long begin_time)
	{
		this.begin_time = begin_time;
	}

	public long getEnd_time()
	{
		return end_time;
	}

	public void setEnd_time(long end_time)
	{
		this.end_time = end_time;
	}

	public long getLast_time()
	{
		return last_time;
	}

	public void setLast_time(long last_time)
	{
		this.last_time = last_time;
	}

	public int getExpire_day()
	{
		return expire_day;
	}

	public void setExpire_day(int expire_day)
	{
		this.expire_day = expire_day;
	}

	public int getTotal_num()
	{
		return total_num;
	}

	public void setTotal_num(int total_num)
	{
		this.total_num = total_num;
	}

	public int getIs_effect()
	{
		return is_effect;
	}

	public void setIs_effect(int is_effect)
	{
		this.is_effect = is_effect;
	}

	public int getUser_count()
	{
		return user_count;
	}

	public void setUser_count(int user_count)
	{
		this.user_count = user_count;
	}

	public int getUser_limit()
	{
		return user_limit;
	}

	public void setUser_limit(int user_limit)
	{
		this.user_limit = user_limit;
	}

	public int getScore_limit()
	{
		return score_limit;
	}

	public void setScore_limit(int score_limit)
	{
		this.score_limit = score_limit;
	}

	public int getPoint_limit()
	{
		return point_limit;
	}

	public void setPoint_limit(int point_limit)
	{
		this.point_limit = point_limit;
	}

	public String getAvg_point()
	{
		return avg_point;
	}

	public void setAvg_point(String avg_point)
	{
		this.avg_point = avg_point;
	}

	public String getLast_time_format()
	{
		return last_time_format;
	}

	public void setLast_time_format(String last_time_format)
	{
		this.last_time_format = last_time_format;
	}

	public String getSupplier_info_name()
	{
		return supplier_info_name;
	}

	public void setSupplier_info_name(String supplier_info_name)
	{
		this.supplier_info_name = supplier_info_name;
	}

	public int getLess()
	{
		return less;
	}

	public void setLess(int less)
	{
		this.less = less;
	}

}

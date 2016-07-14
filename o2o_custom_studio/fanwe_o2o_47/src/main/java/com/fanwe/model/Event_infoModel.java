package com.fanwe.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fanwe.library.utils.SDCollectionUtil;

public class Event_infoModel
{
	private int id; // 活动ID
	private String name; // 活动名称
	private String icon; // 图片
	private long event_begin_time; // 活动开始时间
	private long event_end_time; // 活动结束时间
	private String event_begin_time_format;
	private String event_end_time_format;
	private long submit_begin_time; // 报名开始时间
	private long submit_end_time; // 报名结束时间
	private String submit_begin_time_format;
	private String submit_end_time_format;
	private int submit_count; // 报名总数
	private int total_count; // 活动名额
	private int score_limit; // 消耗积分
	private int point_limit; // 经验限制
	private long now_time; // 当前时间
	private String supplier_info_name; // 活动主商户名称
	private String content; // 活动明细
	private String address; // 活动地址
	private String avg_point; // 点评平均分
	private Map<String, Integer> submitted_data;
	private List<Event_fieldsModel> event_fields; // 报名表单字段
	private String share_url;

	// add
	private int is_verify = -1; // 用户报名状态

	public List<String> getFieldsIds()
	{
		List<String> listIds = new ArrayList<String>();
		if (!SDCollectionUtil.isEmpty(event_fields))
		{
			for (Event_fieldsModel model : event_fields)
			{
				listIds.add(String.valueOf(model.getId()));
			}
		}
		return listIds;
	}

	public Map<String, Object> getFieldsValues()
	{
		Map<String, Object> mapValues = new HashMap<String, Object>();
		if (!SDCollectionUtil.isEmpty(event_fields))
		{
			for (Event_fieldsModel model : event_fields)
			{
				mapValues.put(String.valueOf(model.getId()), model.getFieldValue());
			}
		}
		return mapValues;
	}

	public String getShare_url()
	{
		return share_url;
	}

	public void setShare_url(String share_url)
	{
		this.share_url = share_url;
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

	public long getEvent_begin_time()
	{
		return event_begin_time;
	}

	public void setEvent_begin_time(long event_begin_time)
	{
		this.event_begin_time = event_begin_time;
	}

	public long getEvent_end_time()
	{
		return event_end_time;
	}

	public void setEvent_end_time(long event_end_time)
	{
		this.event_end_time = event_end_time;
	}

	public String getEvent_begin_time_format()
	{
		return event_begin_time_format;
	}

	public void setEvent_begin_time_format(String event_begin_time_format)
	{
		this.event_begin_time_format = event_begin_time_format;
	}

	public String getEvent_end_time_format()
	{
		return event_end_time_format;
	}

	public void setEvent_end_time_format(String event_end_time_format)
	{
		this.event_end_time_format = event_end_time_format;
	}

	public long getSubmit_begin_time()
	{
		return submit_begin_time;
	}

	public void setSubmit_begin_time(long submit_begin_time)
	{
		this.submit_begin_time = submit_begin_time;
	}

	public long getSubmit_end_time()
	{
		return submit_end_time;
	}

	public void setSubmit_end_time(long submit_end_time)
	{
		this.submit_end_time = submit_end_time;
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

	public int getSubmit_count()
	{
		return submit_count;
	}

	public void setSubmit_count(int submit_count)
	{
		this.submit_count = submit_count;
	}

	public int getTotal_count()
	{
		return total_count;
	}

	public void setTotal_count(int total_count)
	{
		this.total_count = total_count;
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

	public long getNow_time()
	{
		return now_time;
	}

	public void setNow_time(long now_time)
	{
		this.now_time = now_time;
	}

	public String getSupplier_info_name()
	{
		return supplier_info_name;
	}

	public void setSupplier_info_name(String supplier_info_name)
	{
		this.supplier_info_name = supplier_info_name;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getAvg_point()
	{
		return avg_point;
	}

	public void setAvg_point(String avg_point)
	{
		this.avg_point = avg_point;
	}

	public Map<String, Integer> getSubmitted_data()
	{
		return submitted_data;
	}

	public void setSubmitted_data(Map<String, Integer> submitted_data)
	{
		this.submitted_data = submitted_data;
		if (submitted_data != null)
		{
			if (submitted_data.containsKey("is_verify"))
			{
				this.is_verify = submitted_data.get("is_verify");
			}
		}
	}

	public List<Event_fieldsModel> getEvent_fields()
	{
		return event_fields;
	}

	public void setEvent_fields(List<Event_fieldsModel> event_fields)
	{
		this.event_fields = event_fields;
	}

	public int getIs_verify()
	{
		return is_verify;
	}

	public void setIs_verify(int is_verify)
	{
		this.is_verify = is_verify;
	}

}

package com.fanwe.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fanwe.library.adapter.SDAdapter.SDSelectable;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDNumberUtil;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.utils.SDFormatUtil;

public class Deal_indexActModel extends BaseActModel implements SDSelectable
{
	private int id; // 商品ID
	private String name; // 商品名称
	private String sub_name; // 简短商品名称
	private String brief; // 简介
	private String current_price; // 当前价格
	private String origin_price; // 原价
	private int return_score_show; // 所需要的积分，buy_type为1时显示的价格
	private String icon;
	private long begin_time; // 开始时间
	private long end_time; // 结束时间
	private int time_status; // 时间状态 (0 未开始 / 1 可以兑换或者购买 / 2 已经过期)
	private long now_time; // 当前时间
	private int buy_count; // 已售数量
	private int buy_type; // 购买类型， 团购商品的类型0:普通 1:积分商品 2:订购 3秒杀
	private int is_shop;// 0:团购 1:商品
	private int is_collect;// 是否已经收藏商品 0：否 1：是
	private List<Deal_attrModel> deal_attr; // 商品属性数据
	private String avg_point; // 商品点评平均分
	private int dp_count; // 点评人数
	private int supplier_location_count; // 门店总数
	private long last_time; // 剩余的秒数
	private String last_time_format; // 剩余的秒数
	private List<Deal_tagsModel> deal_tags; // 商品标签
	private List<String> images; // 商品图集 230X140
	private List<String> oimages; // 商品图集原图
	private String description; // 商品详情 HTML 格式
	private List<StoreModel> supplier_location_list; // 门店数据列表
	private List<CommentModel> dp_list; // 点评数据列表
	private String page_title; // 标题栏名称
	private String share_url; // 分享链接
	private String notes; // 购买须知
	private Relate_dataModel relate_data;
	private int is_fx; // 2的时候出现分销相关操作
	private int is_my_fx; // 0:出现我要分销，1:出现取消分销
	private String set_meal;

	// add
	private String current_priceFormat;
	private String origin_priceFormat;
	private double current_price_double;
	private double origin_price_double;
	private boolean selected;

	public String getSet_meal()
	{
		return set_meal;
	}

	public void setSet_meal(String set_meal)
	{
		this.set_meal = set_meal;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public double getCurrent_price_double()
	{
		return current_price_double;
	}

	public void setCurrent_price_double(double current_price_double)
	{
		this.current_price_double = current_price_double;
	}

	public double getOrigin_price_double()
	{
		return origin_price_double;
	}

	public void setOrigin_price_double(double origin_price_double)
	{
		this.origin_price_double = origin_price_double;
	}

	public Relate_dataModel getRelate_data()
	{
		return relate_data;
	}

	public void setRelate_data(Relate_dataModel relate_data)
	{
		this.relate_data = relate_data;
	}

	public List<Deal_indexActModel> getListRelateModel()
	{
		List<Deal_indexActModel> listModel = null;
		if (relate_data != null)
		{
			listModel = relate_data.getGoodsList();
		}
		return listModel;
	}

	public int getIs_fx()
	{
		return is_fx;
	}

	public void setIs_fx(int is_fx)
	{
		this.is_fx = is_fx;
	}

	public int getIs_my_fx()
	{
		return is_my_fx;
	}

	public void setIs_my_fx(int is_my_fx)
	{
		this.is_my_fx = is_my_fx;
	}

	public int getIs_collect()
	{
		return is_collect;
	}

	public void setIs_collect(int is_collect)
	{
		this.is_collect = is_collect;
	}

	public int getReturn_score_show()
	{
		return return_score_show;
	}

	public void setReturn_score_show(int return_score_show)
	{
		this.return_score_show = return_score_show;
	}

	public int getIs_shop()
	{
		return is_shop;
	}

	public void setIs_shop(int is_shop)
	{
		this.is_shop = is_shop;
	}

	public boolean hasAttr()
	{
		return !SDCollectionUtil.isEmpty(deal_attr);
	}

	/**
	 * 清除选择的属性
	 */
	public void clearSelectedAttr()
	{
		List<Deal_attrModel> listAttr = getSelectedAttr();
		if (!SDCollectionUtil.isEmpty(listAttr))
		{
			for (Deal_attrModel model : listAttr)
			{
				model.getSelectedAttr().setSelected(false);
			}
		}
	}

	/**
	 * 获得第几个属性
	 * 
	 * @param index
	 * @return
	 */
	public Deal_attrModel getAttr(int index)
	{
		return SDCollectionUtil.get(deal_attr, index);
	}

	/**
	 * 获得未被选中的属性
	 * 
	 * @return
	 */
	public List<Deal_attrModel> getUnSelectedAttr()
	{
		List<Deal_attrModel> listAttr = new ArrayList<Deal_attrModel>();
		if (deal_attr != null)
		{
			for (Deal_attrModel attrModel : deal_attr)
			{
				if (attrModel.getSelectedAttr() == null)
				{
					listAttr.add(attrModel);
				}
			}
		}
		return listAttr;
	}

	/**
	 * 获得被选中的属性
	 * 
	 * @return
	 */
	public List<Deal_attrModel> getSelectedAttr()
	{
		List<Deal_attrModel> listAttr = new ArrayList<Deal_attrModel>();
		if (deal_attr != null)
		{
			for (Deal_attrModel attrModel : deal_attr)
			{
				if (attrModel.getSelectedAttr() != null)
				{
					listAttr.add(attrModel);
				}
			}
		}
		return listAttr;
	}

	/**
	 * 获得当前商品选中的属性id数据key:属性id,value:属性值id
	 * 
	 * @return
	 */
	public Map<String, Integer> getSelectedAttrId()
	{
		Map<String, Integer> mapAttrIds = new HashMap<String, Integer>();
		if (deal_attr != null)
		{
			for (Deal_attrModel attrModel : deal_attr)
			{
				Deal_attrValueModel attrValueModel = attrModel.getSelectedAttr();
				if (attrValueModel != null)
				{
					mapAttrIds.put(String.valueOf(attrModel.getId()), attrValueModel.getId());
				}
			}
		}
		return mapAttrIds;
	}

	/**
	 * 获得选中的商品属性的递增价格
	 * 
	 * @return
	 */
	public double getSelectedAttrPrice()
	{
		double price = 0;
		if (deal_attr != null)
		{
			for (Deal_attrModel attrModel : deal_attr)
			{
				Deal_attrValueModel attrValueModel = attrModel.getSelectedAttr();
				if (attrValueModel != null)
				{
					double attrPrice = attrValueModel.getPrice_double();
					price = SDNumberUtil.add(price, attrPrice, 2);
				}
			}
		}
		return price;
	}

	/**
	 * 获得现价总价（包括属性递增价格）
	 * 
	 * @return
	 */
	public double getCurrentPriceTotal()
	{
		return SDNumberUtil.add(current_price_double, getSelectedAttrPrice(), 2);
	}

	/**
	 * 获得现价总价（包括属性递增价格）
	 * 
	 * @return
	 */
	public double getOriginalPriceTotal()
	{
		return SDNumberUtil.add(origin_price_double, getSelectedAttrPrice(), 2);
	}

	public String getNotes()
	{
		return notes;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	public String getCurrent_priceFormat()
	{
		return current_priceFormat;
	}

	public void setCurrent_priceFormat(String current_priceFormat)
	{
		this.current_priceFormat = current_priceFormat;
	}

	public String getOrigin_priceFormat()
	{
		return origin_priceFormat;
	}

	public void setOrigin_priceFormat(String origin_priceFormat)
	{
		this.origin_priceFormat = origin_priceFormat;
	}

	public String getShare_url()
	{
		return share_url;
	}

	public void setShare_url(String share_url)
	{
		this.share_url = share_url;
	}

	public String getPage_title()
	{
		return page_title;
	}

	public void setPage_title(String page_title)
	{
		this.page_title = page_title;
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

	public String getBrief()
	{
		return brief;
	}

	public void setBrief(String brief)
	{
		this.brief = brief;
	}

	public String getCurrent_price()
	{
		return current_price;
	}

	public void setCurrent_price(String current_price)
	{
		this.current_price = current_price;
		this.current_priceFormat = SDFormatUtil.formatMoneyChina(current_price);
		this.current_price_double = SDTypeParseUtil.getDouble(current_price);
	}

	public String getOrigin_price()
	{
		return origin_price;
	}

	public void setOrigin_price(String origin_price)
	{
		this.origin_price = origin_price;
		this.origin_priceFormat = SDFormatUtil.formatMoneyChina(origin_price);
		this.origin_price_double = SDTypeParseUtil.getDouble(origin_price);
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
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

	public int getTime_status()
	{
		return time_status;
	}

	public void setTime_status(int time_status)
	{
		this.time_status = time_status;
	}

	public long getNow_time()
	{
		return now_time;
	}

	public void setNow_time(long now_time)
	{
		this.now_time = now_time;
	}

	public int getBuy_count()
	{
		return buy_count;
	}

	public void setBuy_count(int buy_count)
	{
		this.buy_count = buy_count;
	}

	public int getBuy_type()
	{
		return buy_type;
	}

	public void setBuy_type(int buy_type)
	{
		this.buy_type = buy_type;
	}

	public List<Deal_attrModel> getDeal_attr()
	{
		return deal_attr;
	}

	public void setDeal_attr(List<Deal_attrModel> deal_attr)
	{
		this.deal_attr = deal_attr;
	}

	public String getAvg_point()
	{
		return avg_point;
	}

	public void setAvg_point(String avg_point)
	{
		this.avg_point = avg_point;
	}

	public int getDp_count()
	{
		return dp_count;
	}

	public void setDp_count(int dp_count)
	{
		this.dp_count = dp_count;
	}

	public int getSupplier_location_count()
	{
		return supplier_location_count;
	}

	public void setSupplier_location_count(int supplier_location_count)
	{
		this.supplier_location_count = supplier_location_count;
	}

	public long getLast_time()
	{
		return last_time;
	}

	public void setLast_time(long last_time)
	{
		this.last_time = last_time;
	}

	public String getLast_time_format()
	{
		return last_time_format;
	}

	public void setLast_time_format(String last_time_format)
	{
		this.last_time_format = last_time_format;
	}

	public List<Deal_tagsModel> getDeal_tags()
	{
		return deal_tags;
	}

	public void setDeal_tags(List<Deal_tagsModel> deal_tags)
	{
		this.deal_tags = deal_tags;
	}

	public List<String> getImages()
	{
		return images;
	}

	public void setImages(List<String> images)
	{
		this.images = images;
	}

	public List<String> getOimages()
	{
		return oimages;
	}

	public void setOimages(List<String> oimages)
	{
		this.oimages = oimages;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public List<StoreModel> getSupplier_location_list()
	{
		return supplier_location_list;
	}

	public void setSupplier_location_list(List<StoreModel> supplier_location_list)
	{
		this.supplier_location_list = supplier_location_list;
	}

	public List<CommentModel> getDp_list()
	{
		return dp_list;
	}

	public void setDp_list(List<CommentModel> dp_list)
	{
		this.dp_list = dp_list;
	}

}

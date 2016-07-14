package com.fanwe.model;

public class NearByTuanItemModel
{
	private String city_name = null;
	private String goods_id = null;
	private String title = null;
	private String sub_name = null;
	private String image = null;

	private String start_date = null;
	private String end_date = null;
	private String ori_price = null;
	private String cur_price = null;
	private String goods_brief = null;

	private String cur_price_format = null;
	private String discount = null;
	private String address = null;
	private String num_unit = null;
	private String limit_num = null;
	private String goods_desc = null;
	private String is_shop = null;
	private String notes = null;
	private String supplier_location_id = null;
	private String any_refund = null;
	private String expire_refund = null;
	private String time_status = null;
	private String end_time = null;
	private String begin_time = null;
	private String sp_detail = null;
	private String sp_tel = null;
	private String saving_format = null;
	private String less_time = null;
	private String has_attr = null;
	private String has_delivery = null;
	private String has_mcod = null;
	private String has_cart = null;
	private String change_cart_request_server = null;
	private String is_refund = null;
	private String avg_point = null;
	private String distance = null;
	private NearByTuanItemAttrModel attr = null;
	private String share_content = null;

	// ------add------
	private String ori_price_format = null;
	private String buy_count = null;

	public String getCity_name()
	{
		return city_name;
	}

	public void setCity_name(String city_name)
	{
		this.city_name = city_name;
	}

	public String getGoods_id()
	{
		return goods_id;
	}

	public void setGoods_id(String goods_id)
	{
		this.goods_id = goods_id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getSub_name()
	{
		return sub_name;
	}

	public void setSub_name(String sub_name)
	{
		this.sub_name = sub_name;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public String getBuy_count()
	{
		return buy_count;
	}

	public void setBuy_count(String buy_count)
	{
		this.buy_count = "已售" + buy_count;
	}

	public String getStart_date()
	{
		return start_date;
	}

	public void setStart_date(String start_date)
	{
		this.start_date = start_date;
	}

	public String getEnd_date()
	{
		return end_date;
	}

	public void setEnd_date(String end_date)
	{
		this.end_date = end_date;
	}

	public String getOri_price()
	{
		return ori_price;
	}

	public void setOri_price(String ori_price)
	{
		this.ori_price = ori_price;
	}

	public String getCur_price()
	{
		return cur_price;
	}

	public void setCur_price(String cur_price)
	{
		this.cur_price = cur_price;
	}

	public String getGoods_brief()
	{
		return goods_brief;
	}

	public void setGoods_brief(String goods_brief)
	{
		this.goods_brief = goods_brief;
	}

	public String getOri_price_format()
	{
		return ori_price_format;
	}

	public void setOri_price_format(String ori_price_format)
	{
		this.ori_price_format = ori_price_format + "元";
	}

	public String getCur_price_format()
	{
		return cur_price_format;
	}

	public void setCur_price_format(String cur_price_format)
	{
		this.cur_price_format = cur_price_format;
	}

	public String getDiscount()
	{
		return discount;
	}

	public void setDiscount(String discount)
	{
		this.discount = discount;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getNum_unit()
	{
		return num_unit;
	}

	public void setNum_unit(String num_unit)
	{
		this.num_unit = num_unit;
	}

	public String getLimit_num()
	{
		return limit_num;
	}

	public void setLimit_num(String limit_num)
	{
		this.limit_num = limit_num;
	}

	public String getGoods_desc()
	{
		return goods_desc;
	}

	public void setGoods_desc(String goods_desc)
	{
		this.goods_desc = goods_desc;
	}

	public String getIs_shop()
	{
		return is_shop;
	}

	public void setIs_shop(String is_shop)
	{
		this.is_shop = is_shop;
	}

	public String getNotes()
	{
		return notes;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	public String getSupplier_location_id()
	{
		return supplier_location_id;
	}

	public void setSupplier_location_id(String supplier_location_id)
	{
		this.supplier_location_id = supplier_location_id;
	}

	public String getAny_refund()
	{
		return any_refund;
	}

	public void setAny_refund(String any_refund)
	{
		this.any_refund = any_refund;
	}

	public String getExpire_refund()
	{
		return expire_refund;
	}

	public void setExpire_refund(String expire_refund)
	{
		this.expire_refund = expire_refund;
	}

	public String getTime_status()
	{
		return time_status;
	}

	public void setTime_status(String time_status)
	{
		this.time_status = time_status;
	}

	public String getEnd_time()
	{
		return end_time;
	}

	public void setEnd_time(String end_time)
	{
		this.end_time = end_time;
	}

	public String getBegin_time()
	{
		return begin_time;
	}

	public void setBegin_time(String begin_time)
	{
		this.begin_time = begin_time;
	}

	public String getSp_detail()
	{
		return sp_detail;
	}

	public void setSp_detail(String sp_detail)
	{
		this.sp_detail = sp_detail;
	}

	public String getSp_tel()
	{
		return sp_tel;
	}

	public void setSp_tel(String sp_tel)
	{
		this.sp_tel = sp_tel;
	}

	public String getSaving_format()
	{
		return saving_format;
	}

	public void setSaving_format(String saving_format)
	{
		this.saving_format = saving_format;
	}

	public String getLess_time()
	{
		return less_time;
	}

	public void setLess_time(String less_time)
	{
		this.less_time = less_time;
	}

	public String getHas_attr()
	{
		return has_attr;
	}

	public void setHas_attr(String has_attr)
	{
		this.has_attr = has_attr;
	}

	public String getHas_delivery()
	{
		return has_delivery;
	}

	public void setHas_delivery(String has_delivery)
	{
		this.has_delivery = has_delivery;
	}

	public String getHas_mcod()
	{
		return has_mcod;
	}

	public void setHas_mcod(String has_mcod)
	{
		this.has_mcod = has_mcod;
	}

	public String getHas_cart()
	{
		return has_cart;
	}

	public void setHas_cart(String has_cart)
	{
		this.has_cart = has_cart;
	}

	public String getChange_cart_request_server()
	{
		return change_cart_request_server;
	}

	public void setChange_cart_request_server(String change_cart_request_server)
	{
		this.change_cart_request_server = change_cart_request_server;
	}

	public String getIs_refund()
	{
		return is_refund;
	}

	public void setIs_refund(String is_refund)
	{
		this.is_refund = is_refund;
	}

	public String getAvg_point()
	{
		return avg_point;
	}

	public void setAvg_point(String avg_point)
	{
		this.avg_point = avg_point;
	}

	public String getDistance()
	{
		return distance;
	}

	public void setDistance(String distance)
	{
		this.distance = distance;
	}

	public NearByTuanItemAttrModel getAttr()
	{
		return attr;
	}

	public void setAttr(NearByTuanItemAttrModel attr)
	{
		this.attr = attr;
	}

	public String getShare_content()
	{
		return share_content;
	}

	public void setShare_content(String share_content)
	{
		this.share_content = share_content;
	}
}
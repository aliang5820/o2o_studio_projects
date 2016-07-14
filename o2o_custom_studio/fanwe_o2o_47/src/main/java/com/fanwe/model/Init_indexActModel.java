package com.fanwe.model;

import java.util.List;

/**
 * 
 * @author yhz
 * @create time 2014-7-16
 */
public class Init_indexActModel extends BaseActModel
{
	private int _id;

	private int city_id;
	private String city_name;

	private int region_version;
	private int only_one_delivery;

	private String kf_phone;
	private String kf_email;
	private int about_info;

	private String version;
	private int page_size;
	private int has_region;
	private String program_title;
	private String index_logo;

	private int api_sina;
	private String sina_app_key;
	private String sina_app_secret;
	private String sina_bind_url;

	private int api_qq;
	private String qq_app_key;
	private String qq_app_secret;

	private int api_wx;
	private String wx_app_key;
	private String wx_app_secret;

	private List<CitylistModel> citylist;
	private List<CitylistModel> hot_city;

	private List<InitActNewslistModel> newslist;
	private List<InitActAddr_tlistModel> addr_tlist;
	private List<QuansModel> quanlist;
	private List<InitActDeal_cate_listModel> deal_cate_list;

	private List<InitActStart_pageModel> start_page_new;

	private User_infoModel user;
	private int register_rebate;// 邀请注册返利设置 0关闭 1购物车注册页都显示 2只在购物车页显示3只在注册页显示
	private int is_fx; // 1:会员中心显示分销，0:不显示
	private int is_dc; // 1:包含外卖插件，0:不包含外卖插件
	private int menu_user_withdraw; // 1:有提现功能，0:不显示
	private int menu_user_charge; // 1:有充值功能，0:不显示
	private int is_store_pay; // 1:到店支付功能，0:不显示

	// add
	private int is_xiaomi; // 1:小米商店审核

	public int getMenu_user_charge()
	{
		return menu_user_charge;
	}

	public void setMenu_user_charge(int menu_user_charge)
	{
		this.menu_user_charge = menu_user_charge;
	}

	public int getIs_xiaomi()
	{
		return is_xiaomi;
	}

	public void setIs_xiaomi(int is_xiaomi)
	{
		this.is_xiaomi = is_xiaomi;
	}

	public int getIs_store_pay()
	{
		return is_store_pay;
	}

	public void setIs_store_pay(int is_store_pay)
	{
		this.is_store_pay = is_store_pay;
	}

	public int getIs_fx()
	{
		return is_fx;
	}

	public void setIs_fx(int is_fx)
	{
		this.is_fx = is_fx;
	}

	public int getRegister_rebate()
	{
		return register_rebate;
	}

	public void setRegister_rebate(int register_rebate)
	{
		this.register_rebate = register_rebate;
	}

	public List<CitylistModel> getHot_city()
	{
		return hot_city;
	}

	public void setHot_city(List<CitylistModel> hot_city)
	{
		this.hot_city = hot_city;
	}

	public User_infoModel getUser()
	{
		return user;
	}

	public void setUser(User_infoModel user)
	{
		this.user = user;
	}

	public int getApi_wx()
	{
		return api_wx;
	}

	public void setApi_wx(int api_wx)
	{
		this.api_wx = api_wx;
	}

	public List<InitActStart_pageModel> getStart_page_new()
	{
		return start_page_new;
	}

	public void setStart_page_new(List<InitActStart_pageModel> start_page_new)
	{
		this.start_page_new = start_page_new;
	}

	public String getWx_app_key()
	{
		return wx_app_key;
	}

	public void setWx_app_key(String wx_app_key)
	{
		this.wx_app_key = wx_app_key;
	}

	public String getWx_app_secret()
	{
		return wx_app_secret;
	}

	public void setWx_app_secret(String wx_app_secret)
	{
		this.wx_app_secret = wx_app_secret;
	}

	public int get_id()
	{
		return _id;
	}

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public int getApi_qq()
	{
		return api_qq;
	}

	public void setApi_qq(int api_qq)
	{
		this.api_qq = api_qq;
	}

	public String getQq_app_key()
	{
		return qq_app_key;
	}

	public void setQq_app_key(String qq_app_key)
	{
		this.qq_app_key = qq_app_key;
	}

	public String getQq_app_secret()
	{
		return qq_app_secret;
	}

	public void setQq_app_secret(String qq_app_secret)
	{
		this.qq_app_secret = qq_app_secret;
	}

	public int getApi_sina()
	{
		return api_sina;
	}

	public void setApi_sina(int api_sina)
	{
		this.api_sina = api_sina;
	}

	public String getSina_app_key()
	{
		return sina_app_key;
	}

	public void setSina_app_key(String sina_app_key)
	{
		this.sina_app_key = sina_app_key;
	}

	public String getSina_app_secret()
	{
		return sina_app_secret;
	}

	public void setSina_app_secret(String sina_app_secret)
	{
		this.sina_app_secret = sina_app_secret;
	}

	public String getSina_bind_url()
	{
		return sina_bind_url;
	}

	public void setSina_bind_url(String sina_bind_url)
	{
		this.sina_bind_url = sina_bind_url;
	}

	public int getCity_id()
	{
		return city_id;
	}

	public void setCity_id(int city_id)
	{
		this.city_id = city_id;
	}

	public String getCity_name()
	{
		return city_name;
	}

	public void setCity_name(String city_name)
	{
		this.city_name = city_name;
	}

	public int getRegion_version()
	{
		return region_version;
	}

	public void setRegion_version(int region_version)
	{
		this.region_version = region_version;
	}

	public int getOnly_one_delivery()
	{
		return only_one_delivery;
	}

	public void setOnly_one_delivery(int only_one_delivery)
	{
		this.only_one_delivery = only_one_delivery;
	}

	public String getKf_phone()
	{
		return kf_phone;
	}

	public void setKf_phone(String kf_phone)
	{
		this.kf_phone = kf_phone;
	}

	public String getKf_email()
	{
		return kf_email;
	}

	public void setKf_email(String kf_email)
	{
		this.kf_email = kf_email;
	}

	public int getAbout_info()
	{
		return about_info;
	}

	public void setAbout_info(int about_info)
	{
		this.about_info = about_info;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public int getPage_size()
	{
		return page_size;
	}

	public void setPage_size(int page_size)
	{
		this.page_size = page_size;
	}

	public int getHas_region()
	{
		return has_region;
	}

	public void setHas_region(int has_region)
	{
		this.has_region = has_region;
	}

	public String getProgram_title()
	{
		return program_title;
	}

	public void setProgram_title(String program_title)
	{
		this.program_title = program_title;
	}

	public String getIndex_logo()
	{
		return index_logo;
	}

	public void setIndex_logo(String index_logo)
	{
		this.index_logo = index_logo;
	}

	public List<CitylistModel> getCitylist()
	{
		return citylist;
	}

	public void setCitylist(List<CitylistModel> citylist)
	{
		this.citylist = citylist;
	}

	public List<InitActNewslistModel> getNewslist()
	{
		return newslist;
	}

	public void setNewslist(List<InitActNewslistModel> newslist)
	{
		this.newslist = newslist;
	}

	public List<InitActAddr_tlistModel> getAddr_tlist()
	{
		return addr_tlist;
	}

	public void setAddr_tlist(List<InitActAddr_tlistModel> addr_tlist)
	{
		this.addr_tlist = addr_tlist;
	}

	public List<QuansModel> getQuanlist()
	{
		return quanlist;
	}

	public void setQuanlist(List<QuansModel> quanlist)
	{
		this.quanlist = quanlist;
	}

	public List<InitActDeal_cate_listModel> getDeal_cate_list()
	{
		return deal_cate_list;
	}

	public void setDeal_cate_list(List<InitActDeal_cate_listModel> deal_cate_list)
	{
		this.deal_cate_list = deal_cate_list;
	}

	public int getIs_dc()
	{
		return is_dc;
	}

	public void setIs_dc(int is_dc)
	{
		this.is_dc = is_dc;
	}

	public int getMenu_user_withdraw()
	{
		return menu_user_withdraw;
	}

	public void setMenu_user_withdraw(int menu_user_withdraw)
	{
		this.menu_user_withdraw = menu_user_withdraw;
	}

}

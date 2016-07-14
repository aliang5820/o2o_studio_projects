package com.fanwe.model;

import java.io.Serializable;
import java.util.List;

import com.fanwe.library.utils.SDTypeParseUtil;

public class Index_indexActModel extends BaseActModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<IndexActAdvsModel> advs;
	private List<IndexActIndexsModel> indexs;
	private List<StoreModel> supplier_list;
	private List<GoodsModel> deal_list;
	private List<GoodsModel> supplier_deal_list;
	private List<EventModel> event_list;
	private List<YouhuiModel> youhui_list;
	private List<IndexActCatesModel> cates;

	// 米果定制
	private List<GoodsModel> seckill;
	private String now_time;
	private String begin_time;

	public String getNow_time()
	{
		return now_time;
	}

	public void setNow_time(String now_time)
	{
		this.now_time = now_time;
	}

	public String getBegin_time()
	{
		return begin_time;
	}

	public void setBegin_time(String begin_time)
	{
		this.begin_time = begin_time;
	}

	public long getLeft_time()
	{
		long begin = SDTypeParseUtil.getLong(begin_time);
		long now = SDTypeParseUtil.getLong(now_time);
		return (begin - now);
	}

	public List<GoodsModel> getSeckill()
	{
		return seckill;
	}

	public void setSeckill(List<GoodsModel> seckill)
	{
		this.seckill = seckill;
	}

	private String zt_html;

	public String getZt_html()
	{
		return zt_html;
	}

	public void setZt_html(String zt_html)
	{
		this.zt_html = zt_html;
	}

	public List<IndexActAdvsModel> getAdvs()
	{
		return advs;
	}

	public void setAdvs(List<IndexActAdvsModel> advs)
	{
		this.advs = advs;
	}

	public List<IndexActIndexsModel> getIndexs()
	{
		return indexs;
	}

	public void setIndexs(List<IndexActIndexsModel> indexs)
	{
		this.indexs = indexs;
	}

	public List<StoreModel> getSupplier_list()
	{
		return supplier_list;
	}

	public void setSupplier_list(List<StoreModel> supplier_list)
	{
		this.supplier_list = supplier_list;
	}

	public List<GoodsModel> getDeal_list()
	{
		return deal_list;
	}

	public void setDeal_list(List<GoodsModel> deal_list)
	{
		this.deal_list = deal_list;
	}

	public List<GoodsModel> getSupplier_deal_list()
	{
		return supplier_deal_list;
	}

	public void setSupplier_deal_list(List<GoodsModel> supplier_deal_list)
	{
		this.supplier_deal_list = supplier_deal_list;
	}

	public List<EventModel> getEvent_list()
	{
		return event_list;
	}

	public void setEvent_list(List<EventModel> event_list)
	{
		this.event_list = event_list;
	}

	public List<YouhuiModel> getYouhui_list()
	{
		return youhui_list;
	}

	public void setYouhui_list(List<YouhuiModel> youhui_list)
	{
		this.youhui_list = youhui_list;
	}

	public List<IndexActCatesModel> getCates()
	{
		return cates;
	}

	public void setCates(List<IndexActCatesModel> cates)
	{
		this.cates = cates;
	}

}

package com.fanwe.model;

import java.util.List;
import java.util.Map;

public class DynamicModel
{

	public enum EnumShareType
	{
		/** 普通分享 */
		SHARE,

		/** 商品分享 */
		SHAREDEAL,
		/** 优惠券分享 */
		SHAREYOUHUI,
		/** 活动分享 */
		SHAREEVENT,

		/** 商品评论分享 */
		DEALCOMMENT,
		/** 优惠券评论分享 */
		YOUHUICOMMENT,
		/** 活动评论分享 */
		EVENTCOMMENT,
		/** 门店评论分享 */
		SLOCATIONCOMMENT,

		/** 活动报名分享 */
		EVENTSUBMIT;
	}

	private int id;
	private String title;
	private String content;
	private String o_path;
	private String image;
	private int image_count;
	private List<String> s_img; // 缩略图
	private List<String> b_img; // 原图
	private String type; // 分享类型
	private String type_txt;
	private int user_id; // 发布用户ID
	private String user_name; // 发布用户名称
	private String user_avatar; // 用户头像
	private int reply_count; // 回复数量
	private int fav_count; // 喜欢数量
	private String show_time; // 发布时间格式化
	private int reply_is_move; // 是否有更多回复
	private DynamicShare_objModel share_obj;
	private List<DynamicReplyModel> reply_list;

	// add
	private EnumShareType type_format_enum = EnumShareType.SHARE;
	private Map<String, String> mapKeyUrl;

	public void setMapKeyUrl(Map<String, String> mapKeyUrl)
	{
		this.mapKeyUrl = mapKeyUrl;
	}

	public Map<String, String> getMapKeyUrl()
	{
		return mapKeyUrl;
	}

	public DynamicShare_objModel getShare_obj()
	{
		return share_obj;
	}

	public void setShare_obj(DynamicShare_objModel share_obj)
	{
		this.share_obj = share_obj;
	}

	public EnumShareType getType_format_enum()
	{
		return type_format_enum;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getO_path()
	{
		return o_path;
	}

	public void setO_path(String o_path)
	{
		this.o_path = o_path;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public int getImage_count()
	{
		return image_count;
	}

	public void setImage_count(int image_count)
	{
		this.image_count = image_count;
	}

	public List<String> getS_img()
	{
		return s_img;
	}

	public void setS_img(List<String> s_img)
	{
		this.s_img = s_img;
	}

	public List<String> getB_img()
	{
		return b_img;
	}

	public void setB_img(List<String> b_img)
	{
		this.b_img = b_img;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
		if ("share".equals(type))
		{
			type_format_enum = EnumShareType.SHARE;
		} else if ("sharedeal".equals(type))
		{
			type_format_enum = EnumShareType.SHAREDEAL;
		} else if ("shareyouhui".equals(type))
		{
			type_format_enum = EnumShareType.SHAREYOUHUI;
		} else if ("shareevent".equals(type))
		{
			type_format_enum = EnumShareType.SHAREEVENT;
		} else if ("dealcomment".equals(type))
		{
			type_format_enum = EnumShareType.DEALCOMMENT;
		} else if ("youhuicomment".equals(type))
		{
			type_format_enum = EnumShareType.YOUHUICOMMENT;
		} else if ("eventcomment".equals(type))
		{
			type_format_enum = EnumShareType.EVENTCOMMENT;
		} else if ("slocationcomment".equals(type))
		{
			type_format_enum = EnumShareType.SLOCATIONCOMMENT;
		} else if ("eventsubmit".equals(type))
		{
			type_format_enum = EnumShareType.EVENTSUBMIT;
		}
	}

	public String getType_txt()
	{
		return type_txt;
	}

	public void setType_txt(String type_txt)
	{
		this.type_txt = type_txt;
	}

	public int getUser_id()
	{
		return user_id;
	}

	public void setUser_id(int user_id)
	{
		this.user_id = user_id;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public String getUser_avatar()
	{
		return user_avatar;
	}

	public void setUser_avatar(String user_avatar)
	{
		this.user_avatar = user_avatar;
	}

	public int getReply_count()
	{
		return reply_count;
	}

	public void setReply_count(int reply_count)
	{
		this.reply_count = reply_count;
	}

	public int getFav_count()
	{
		return fav_count;
	}

	public void setFav_count(int fav_count)
	{
		this.fav_count = fav_count;
	}

	public String getShow_time()
	{
		return show_time;
	}

	public void setShow_time(String show_time)
	{
		this.show_time = show_time;
	}

	public int getReply_is_move()
	{
		return reply_is_move;
	}

	public void setReply_is_move(int reply_is_move)
	{
		this.reply_is_move = reply_is_move;
	}

	public List<DynamicReplyModel> getReply_list()
	{
		return reply_list;
	}

	public void setReply_list(List<DynamicReplyModel> reply_list)
	{
		this.reply_list = reply_list;
	}

}

package com.fanwe.model;

public class DynamicShare_objModel
{

	private int id;
	private String image;
	private String share_url;
	private String content;
	private String o_path;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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

	public String getShare_url()
	{
		return share_url;
	}

	public void setShare_url(String share_url)
	{
		this.share_url = share_url;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

}

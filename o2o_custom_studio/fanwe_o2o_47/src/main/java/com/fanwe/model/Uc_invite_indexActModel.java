package com.fanwe.model;

public class Uc_invite_indexActModel extends BaseActModel
{
	private String share_url;
	private String invite_code;
	private String qrcode;

	public String getShare_url()
	{
		return share_url;
	}

	public void setShare_url(String share_url)
	{
		this.share_url = share_url;
	}

	public String getInvite_code()
	{
		return invite_code;
	}

	public void setInvite_code(String invite_code)
	{
		this.invite_code = invite_code;
	}

	public String getQrcode()
	{
		return qrcode;
	}

	public void setQrcode(String qrcode)
	{
		this.qrcode = qrcode;
	}

}

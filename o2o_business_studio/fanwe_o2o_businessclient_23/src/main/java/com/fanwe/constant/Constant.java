package com.fanwe.constant;

public class Constant
{

	public enum TitleType
	{
		TITLE_NONE, TITLE;
	}

	public static final class UserLoginState
	{
		public static final int UN_LOGIN = 0;
		public static final int LOGIN = 1;
		public static final int TEMP_LOGIN = 2;
	}

	public static final class ExtraConstant
	{
		public static final String EXTRA_ID = "extra_id";
		public static final String EXTRA_MODEL = "extra_model";
	}

	public static final class RequestCodeActicity
	{
		public static final int REQUESTCODENORMAL = 1;
	}

}

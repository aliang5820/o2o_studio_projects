package com.fanwe.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SDValidateUtil
{

	public static boolean isCellPhoneNumber(String strMobiles)
	{
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(strMobiles);
		return m.matches();
	}

}

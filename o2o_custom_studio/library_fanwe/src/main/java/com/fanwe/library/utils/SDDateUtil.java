package com.fanwe.library.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

public class SDDateUtil
{
	public static final long MILLISECONDS_DAY = 1000 * 60 * 60 * 24;
	public static final long MILLISECONDS_HOUR = 1000 * 60 * 60;
	public static final long MILLISECONDS_MINUTES = 1000 * 60;
	public static final long MILLISECONDS_SECOND = 1000;

	public static long getDuringDay(long mss)
	{
		return mss / MILLISECONDS_DAY;
	}

	public static long getDuringHours(long mss)
	{
		return (mss % MILLISECONDS_DAY) / MILLISECONDS_HOUR;
	}

	public static long getDuringMinutes(long mss)
	{
		return (mss % MILLISECONDS_HOUR) / MILLISECONDS_MINUTES;
	}

	public static long getDuringSeconds(long mss)
	{
		return (mss % MILLISECONDS_MINUTES) / MILLISECONDS_SECOND;
	}

	/**
	 * 将字符串的yyyy-MM-dd HH:mm:ss 转化为毫秒
	 * 
	 * @param dateStrlong
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long stringLongToMil(String dateStrlong)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			Date date = formatter.parse(dateStrlong);

			long milSec = date.getTime();
			return milSec;
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 将毫秒转化成 yyyy-MM-dd HH:mm:ss的字符串
	 * 
	 * @param milSec
	 *            毫秒
	 * @return
	 */
	public static String milToStringlong(Long milSec)
	{
		Date dateNow = new Date(milSec);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStrLong = formatter.format(dateNow);
		return dateStrLong;
	}

	/**
	 * 将毫秒转化成 yyyy-MM-dd的字符串
	 * 
	 * @param milSec
	 *            毫秒
	 * @return
	 */
	public static String milToStringShort(Long milSec)
	{
		Date dateNow = new Date(milSec);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateStrLong = formatter.format(dateNow);
		return dateStrLong;
	}

	/**
	 * 将毫秒转化成 yyyy.MM.dd HH:mm:ss的字符串
	 * 
	 * @param milSec
	 *            毫秒
	 * @return
	 */
	public static String milToStringlongPoint(Long milSec)
	{
		Date dateNow = new Date(milSec);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		String dateStrLong = formatter.format(dateNow);
		return dateStrLong;
	}

	/**
	 * 将毫秒转化成 yyyy.MM.dd的字符串
	 * 
	 * @param milSec
	 *            毫秒
	 * @return
	 */
	public static String milToStringShortPoint(Long milSec)
	{
		Date dateNow = new Date(milSec);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		String dateStrLong = formatter.format(dateNow);
		return dateStrLong;
	}

	/**
	 * 
	 * @return 返回当前Date类型的yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDateLong()
	{
		Date dateNow = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStrLong = formatter.format(dateNow);
		ParsePosition pos = new ParsePosition(8);
		Date dateNow_2 = formatter.parse(dateStrLong, pos);
		return dateNow_2;
	}

	/**
	 * @return 返回当前Date类型的yyyy-MM-dd
	 */
	public static Date getNowDateShortStart()
	{
		Date dateNow = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateStrShort = formatter.format(dateNow);
		ParsePosition pos = new ParsePosition(8);
		Date dateNow_2 = formatter.parse(dateStrShort, pos);
		return dateNow_2;
	}

	/**
	 * 
	 * @return 返回当前时间的yyyy-MM-dd HH:mm:ss字符串
	 */
	public static String getNowStringLong()
	{
		Date dateNow = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStrLong = formatter.format(dateNow);
		return dateStrLong;
	}

	/**
	 * 
	 * @return 返回当前时间的yyyy-MM-dd字符串
	 */
	public static String getNowStringShortStart()
	{
		Date dateNow = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateStrShortStart = formatter.format(dateNow);
		return dateStrShortStart;
	}

	/**
	 * @return 返回当前时间的HH:mm:ss字符串
	 */
	public static String getNowStringShortEnd()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date dateNow = new Date();
		String dateStrShortEnd = formatter.format(dateNow);
		return dateStrShortEnd;
	}

	public static Date stringLongToDateLong(String strDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static Date stringShortToDateShort(String strDate)
	{
		strDate = strDate.substring(0, strDate.lastIndexOf(" "));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * ����ʱ���ʽʱ��ת��Ϊ�ַ� yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * ��Tue Oct 29 13:48:42 �������α�׼ʱ��+0800 2013��ʽʱ��ת��Ϊ�ַ� yyyy-MM-dd
	 * HH:mm:ss
	 * 
	 * @param
	 * @return
	 */
	public static String dateStrToStrLong(String dateStr)
	{
		if (dateStr == null || dateStr.equals(""))
		{
			return "";
		}
		String year = dateStr.substring(dateStr.length() - 4);
		String month = "null";
		String day = dateStr.substring(8, 10);
		String hour = dateStr.substring(11, 13);
		String min = dateStr.substring(14, 16);
		String sec = dateStr.substring(17, 19);
		String monthEn = dateStr.substring(4, 7);
		if ("Jan".equals(monthEn))
		{
			month = 1 + "";
		}
		if ("Feb".equals(monthEn))
		{
			month = 2 + "";
		}
		if ("Mar".equals(monthEn))
		{
			month = 3 + "";
		}
		if ("Apr".equals(monthEn))
		{
			month = 4 + "";
		}
		if ("May".equals(monthEn))
		{
			month = 5 + "";
		}
		if ("Jun".equals(monthEn))
		{
			month = 6 + "";
		}
		if ("Jul".equals(monthEn))
		{
			month = 7 + "";
		}
		if ("Aug".equals(monthEn))
		{
			month = 8 + "";
		}
		if ("Sep".equals(monthEn))
		{
			month = 9 + "";
		}
		if ("Oct".equals(monthEn))
		{
			month = 10 + "";
		}
		if ("Nov".equals(monthEn))
		{
			month = 11 + "";
		}
		if ("Dec".equals(monthEn))
		{
			month = 12 + "";
		}

		return year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
	}

	/**
	 * ����ʱ���ʽʱ��ת��Ϊ�ַ� yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * ����ʱ���ʽ�ַ�ת��Ϊʱ�� yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * �õ�����ʱ��
	 * 
	 * @return
	 */
	public static Date getNow()
	{
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * ��ȡһ�����е����һ��
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day)
	{
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * �õ�����ʱ��
	 * 
	 * @return �ַ� yyyyMMdd HHmmss
	 */
	public static String getStringToday()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * �õ�����Сʱ
	 */
	public static String getHour()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * �õ����ڷ���
	 * 
	 * @return
	 */
	public static String getTime()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * ����û������ʱ���ʾ��ʽ�����ص�ǰʱ��ĸ�ʽ �����yyyyMMdd��ע����ĸy���ܴ�д��
	 * 
	 * @param sformat
	 *            yyyyMMddhhmmss
	 * @return
	 */
	public static String getUserDate(String sformat)
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * ����Сʱʱ���Ĳ�ֵ,���뱣֤����ʱ�䶼��"HH:MM"�ĸ�ʽ�������ַ��͵ķ���
	 */
	public static String getTwoHour(String st1, String st2)
	{
		String[] kk = null;
		String[] jj = null;
		kk = st1.split(":");
		jj = st2.split(":");
		if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
			return "0";
		else
		{
			double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
			double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
			if ((y - u) > 0)
				return y - u + "";
			else
				return "0";
		}
	}

	/**
	 * �õ��������ڼ�ļ������
	 */
	public static String getTwoDay(String sj1, String sj2)
	{
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try
		{
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e)
		{
			return "";
		}
		return day + "";
	}

	/**
	 * ʱ��ǰ�ƻ���Ʒ���,����JJ��ʾ����.
	 */
	public static String getPreTime(String sj1, String jj)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try
		{
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e)
		{
		}
		return mydate1;
	}

	/**
	 * �õ�һ��ʱ���Ӻ��ǰ�Ƽ����ʱ��,nowdateΪʱ��,delayΪǰ�ƻ���ӵ�����
	 */
	public static String getNextDay(String nowdate, String delay)
	{
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e)
		{
			return "";
		}
	}

	/**
	 * �ж��Ƿ�����
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate)
	{

		/**
		 * ��ϸ��ƣ� 1.��400��������꣬���� 2.���ܱ�4�����������
		 * 3.�ܱ�4���ͬʱ���ܱ�100����������� 3.�ܱ�4���ͬʱ�ܱ�100�����������
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0)
		{
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * ��������ʱ���ʽ 26 Apr 2006
	 * 
	 * @param str
	 * @return
	 */
	public static String getEDate(String str)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(str, pos);
		String j = strtodate.toString();
		String[] k = j.split(" ");
		return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
	}

	/**
	 * ��ȡһ���µ����һ��
	 * 
	 * @param dat
	 * @return
	 */
	public static String getEndDateOfMonth(String dat)
	{// yyyy-MM-dd
		String str = dat.substring(0, 8);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12)
		{
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11)
		{
			str += "30";
		} else
		{
			if (isLeapYear(dat))
			{
				str += "29";
			} else
			{
				str += "28";
			}
		}
		return str;
	}

	/**
	 * �ж϶���ʱ���Ƿ���ͬһ����
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear)
		{
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH))
		{
			// ���12�µ����һ�ܺ�������һ�ܵĻ������һ�ܼ���������ĵ�һ��
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH))
		{
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * ����������,���õ���ǰʱ�����ڵ�����ǵڼ���
	 * 
	 * @return
	 */
	public static String getSeqWeek()
	{
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	/**
	 * ���һ���������ڵ��ܵ����ڼ������ڣ���Ҫ�ҳ�2002��2��3�������ܵ�����һ�Ǽ���
	 * 
	 * @param sdate
	 * @param num
	 * @return
	 */
	public static String getWeek(String sdate, String num)
	{
		// ��ת��Ϊʱ��
		Date dd = SDDateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(dd);
		if (num.equals("1")) // ��������һ���ڵ�����
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		else if (num.equals("2")) // �������ڶ����ڵ�����
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		else if (num.equals("3")) // �������������ڵ�����
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		else if (num.equals("4")) // �������������ڵ�����
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		else if (num.equals("5")) // �������������ڵ�����
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		else if (num.equals("6")) // �������������ڵ�����
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		else if (num.equals("0")) // �������������ڵ�����
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 * ���һ�����ڣ����������ڼ����ַ�
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate)
	{
		// ��ת��Ϊʱ��
		Date date = SDDateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour�д�ľ������ڼ��ˣ��䷶Χ 1~7
		// 1=������ 7=��������������
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static String getWeekStr(String sdate)
	{
		String str = "";
		str = SDDateUtil.getWeek(sdate);
		if ("1".equals(str))
		{
			str = "������";
		} else if ("2".equals(str))
		{
			str = "����һ";
		} else if ("3".equals(str))
		{
			str = "���ڶ�";
		} else if ("4".equals(str))
		{
			str = "������";
		} else if ("5".equals(str))
		{
			str = "������";
		} else if ("6".equals(str))
		{
			str = "������";
		} else if ("7".equals(str))
		{
			str = "������";
		}
		return str;
	}

	/**
	 * ����ʱ��֮�������
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2)
	{
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// ת��Ϊ��׼ʱ��
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try
		{
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e)
		{
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * �γ����µ����� �� ��ݴ����һ��ʱ�䷵��һ���ṹ ������ ����һ ���ڶ� ������ ������
	 * ������ ������ �����ǵ��µĸ���ʱ�� �˺���ظ������һ�����������ڵ�����
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getNowMonth(String sdate)
	{
		// ȡ��ʱ�������µ�һ��
		sdate = sdate.substring(0, 8) + "01";

		// �õ�����µ�1�������ڼ�
		Date date = SDDateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int u = c.get(Calendar.DAY_OF_WEEK);
		String newday = SDDateUtil.getNextDay(sdate, (1 - u) + "");
		return newday;
	}

	/**
	 * ȡ����ݿ����� ��ɸ�ʽΪyyyymmddhhmmss+kλ�����
	 * 
	 * @param k
	 *            ��ʾ��ȡ��λ���������Լ���
	 */

	public static String getNo(int k)
	{

		return getUserDate("yyyyMMddhhmmss") + getRandom(k);
	}

	/**
	 * ����һ�������
	 * 
	 * @param i
	 * @return
	 */
	public static String getRandom(int i)
	{
		Random jjj = new Random();
		// int suiJiShu = jjj.nextInt(9);
		if (i == 0)
			return "";
		String jj = "";
		for (int k = 0; k < i; k++)
		{
			jj = jj + jjj.nextInt(9);
		}
		return jj;
	}

	/**
	 * 
	 * @param args
	 */
	public static boolean RightDate(String date)
	{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		;
		if (date == null)
			return false;
		if (date.length() > 10)
		{
			sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else
		{
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		try
		{
			sdf.parse(date);
		} catch (ParseException pe)
		{
			return false;
		}
		return true;
	}

}
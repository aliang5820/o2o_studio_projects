package com.fanwe.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.config.AppConfig;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.work.AppRuntimeWorker;

public class RequestModel
{
	public static final class RequestDataType
	{
		public static final int BASE64 = 0;
		public static final int AES = 4;
	}

	public static final class ResponseDataType
	{
		public static final int BASE64 = 0;
		public static final int JSON = 1;
		public static final int AES = 4;
	}

	// -----------------------------------------
	private int mRequestDataType = RequestDataType.BASE64;
	private int mResponseDataType = ResponseDataType.JSON;

	private Map<String, Object> mData = new HashMap<String, Object>();
	private Map<String, File> mDataFile = new HashMap<String, File>();

	private List<MultiFile> mMultiFile = new ArrayList<MultiFile>();

	private boolean mIsNeedCache = false;
	private boolean mIsNeedShowErrorTip = true;
	private boolean mIsNeedCheckLoginState = true;

	public List<MultiFile> getMultiFile()
	{
		return mMultiFile;
	}

	// 构造方法start
	public RequestModel(Map<String, Object> data)
	{
		super();
		this.mData = data;
		init();
	}

	public RequestModel()
	{
		super();
		init();
	}

	// 构造方法end

	private void init()
	{
		putUser();
		putSessionId();
		putAct("index");
		putCityId();
		putLocation();
		putRefId();
		put("from", "android");
		put("version_name", SDPackageUtil.getVersionName());
	}

	private void putRefId()
	{
		String refId = AppConfig.getRefId();
		if (!TextUtils.isEmpty(refId))
		{
			put("ref_uid", refId);
		}
	}

	public boolean isNeedCache()
	{
		return mIsNeedCache;
	}

	public void setIsNeedCache(boolean isNeedCache)
	{
		this.mIsNeedCache = isNeedCache;
	}

	public boolean isNeedShowErrorTip()
	{
		return mIsNeedShowErrorTip;
	}

	public void setIsNeedShowErrorTip(boolean isNeedShowErrorTip)
	{
		this.mIsNeedShowErrorTip = isNeedShowErrorTip;
	}

	public boolean isNeedCheckLoginState()
	{
		return mIsNeedCheckLoginState;
	}

	public void setIsNeedCheckLoginState(boolean isNeedCheckLoginState)
	{
		this.mIsNeedCheckLoginState = isNeedCheckLoginState;
	}

	public Map<String, File> getDataFile()
	{
		return mDataFile;
	}

	public void setDataFile(Map<String, File> dataFile)
	{
		this.mDataFile = dataFile;
	}

	public Map<String, Object> getData()
	{
		return mData;
	}

	public void setData(Map<String, Object> data)
	{
		this.mData = data;
	}

	public int getRequestDataType()
	{
		return mRequestDataType;
	}

	public void setRequestDataType(int requestDataType)
	{
		this.mRequestDataType = requestDataType;
	}

	public int getResponseDataType()
	{
		return mResponseDataType;
	}

	public void setResponseDataType(int responseDataType)
	{
		this.mResponseDataType = responseDataType;
	}

	public void put(String key, Object value)
	{
		mData.put(key, value);
	}

	public Object get(String key)
	{
		return mData.get(key);
	}

	public void putFile(String key, File file)
	{
		mDataFile.put(key, file);
	}

	public void putMultiFile(String key, File file)
	{
		mMultiFile.add(new MultiFile(key, file));
	}

	public void putSessionId()
	{
		String sessionId = AppConfig.getSessionId();
		if (!TextUtils.isEmpty(sessionId))
		{
			put("sess_id", sessionId);
		}
	}

	public void putUser()
	{
		LocalUserModel user = LocalUserModelDao.queryModel();
		if (user != null)
		{
			put("email", user.getUser_name());
			put("pwd", user.getUser_pwd());
		}
	}

	public void putCityId()
	{
		put("city_id", AppRuntimeWorker.getCity_id());
	}

	public void putLocation()
	{
		if (BaiduMapManager.getInstance().hasLocationSuccess())
		{
			put("m_latitude", BaiduMapManager.getInstance().getLatitude());
			put("m_longitude", BaiduMapManager.getInstance().getLongitude());
		}
	}

	public void putLocationScreen()
	{
		if (BaiduMapManager.getInstance().hasLocationSuccess())
		{
			double m_latitude = BaiduMapManager.getInstance().getLatitude();
			double m_longitude = BaiduMapManager.getInstance().getLongitude();

			put("latitude_top", m_latitude + 0.2);
			put("latitude_bottom", m_latitude - 0.2);
			put("longitude_left", m_longitude - 0.2);
			put("longitude_right", m_longitude + 0.2);
		}
	}

	public void putPage(int page)
	{
		put("page", page);
	}

	public void putPage(PageModel page)
	{
		putPage(page.getPage());
	}

	public void putCtl(String ctl)
	{
		put("ctl", ctl);
	}

	public void putAct(String act)
	{
		put("act", act);
	}

	public class MultiFile
	{
		public final String key;
		public final File file;

		public MultiFile(String key, File file)
		{
			this.key = key;
			this.file = file;
		}

		public String getKey()
		{
			return this.key;
		}

		public File getFile()
		{
			return this.file;
		}
	}

}

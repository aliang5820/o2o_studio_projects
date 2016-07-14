package com.fanwe.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

import com.fanwe.application.App;
import com.fanwe.config.AppConfig;
import com.fanwe.library.utils.SDPackageUtil;

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

	private boolean mIsNeedCache = true;
	private boolean mIsNeedShowErrorTip = true;
	private boolean mIsNeedCheckLoginState = true;

	public List<MultiFile> getmMultiFile()
	{
		return mMultiFile;
	}

	// 构造方法start
	public RequestModel(Map<String, Object> mData)
	{
		super();
		this.mData = mData;
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
		putSessionId();
		putAct("index");
		put("from", "android");
		put("version", SDPackageUtil.getVersionName());
	}

	public boolean ismIsNeedCache()
	{
		return mIsNeedCache;
	}

	public void setmIsNeedCache(boolean mIsNeedCache)
	{
		this.mIsNeedCache = mIsNeedCache;
	}

	public boolean ismIsNeedShowErrorTip()
	{
		return mIsNeedShowErrorTip;
	}

	public void setmIsNeedShowErrorTip(boolean mIsNeedShowErrorTip)
	{
		this.mIsNeedShowErrorTip = mIsNeedShowErrorTip;
	}

	public boolean ismIsNeedCheckLoginState()
	{
		return mIsNeedCheckLoginState;
	}

	public void setmIsNeedCheckLoginState(boolean mIsNeedCheckLoginState)
	{
		this.mIsNeedCheckLoginState = mIsNeedCheckLoginState;
	}

	public Map<String, File> getmDataFile()
	{
		return mDataFile;
	}

	public void setmDataFile(Map<String, File> mDataFile)
	{
		this.mDataFile = mDataFile;
	}

	public Map<String, Object> getmData()
	{
		return mData;
	}

	public void setmData(Map<String, Object> mData)
	{
		this.mData = mData;
	}

	public int getmRequestDataType()
	{
		return mRequestDataType;
	}

	public void setmRequestDataType(int mRequestDataType)
	{
		this.mRequestDataType = mRequestDataType;
	}

	public int getmResponseDataType()
	{
		return mResponseDataType;
	}

	public void setmResponseDataType(int mResponseDataType)
	{
		this.mResponseDataType = mResponseDataType;
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
		LocalUserModel user = App.getApp().getmLocalUser();
		if (user != null)
		{
			put("email", user.getAccount_name());
			put("pwd", user.getAccount_password());
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

	public void putCtlAct(String ctl, String act)
	{
		putCtl(ctl);
		putAct(act);
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

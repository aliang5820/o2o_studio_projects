package com.fanwe.library.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.fanwe.library.R;

/**
 * 带标题，listview，确定按钮和取消按钮的窗口
 * 
 * @author js02
 * 
 */
public class SDDialogListView extends SDDialogCustom
{

	public ListView mLv;

	public SDDialogListView()
	{
		super();
	}

	public SDDialogListView(Activity activity)
	{
		super(activity);
	}

	@Override
	protected void init()
	{
		super.init();
		addListView();
	}

	private void addListView()
	{
		View view = View.inflate(getContext(), R.layout.dialog_listview, null);
		mLv = (ListView) view.findViewById(R.id.lv_content);
		setCustomView(view);
	}

	public void setAdapter(BaseAdapter adapter)
	{
		mLv.setAdapter(adapter);
	}

}

package com.fanwe.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.CitylistModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.PinyinComparator;
import com.fanwe.work.AppRuntimeWorker;

public class CityListAdapter extends SDSimpleAdapter<CitylistModel>
{

	private Map<Integer, Integer> mMapLettersAsciisFirstPostion = new HashMap<Integer, Integer>();
	private PinyinComparator mComparator = new PinyinComparator();

	public CityListAdapter(List<CitylistModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_lv_citylist;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final CitylistModel model)
	{
		TextView tvLetter = ViewHolder.get(R.id.item_lv_citylist_tv_letter, convertView);
		TextView tvName = ViewHolder.get(R.id.item_lv_citylist_tv_city_name, convertView);
		LinearLayout ll_content = ViewHolder.get(R.id.ll_content, convertView);

		// 根据position获取分类的首字母的Char ascii值
		int modelFirstLettersAscii = getModelFirstLettersAscii(model);

		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getLettersAsciisFirstPosition(modelFirstLettersAscii))
		{
			SDViewUtil.show(tvLetter);
			tvLetter.setText(model.getSortLetters());
		} else
		{
			SDViewUtil.hide(tvLetter);
		}

		ll_content.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (AppRuntimeWorker.setCity_name(model.getName()))
				{
					mActivity.finish();
				} else
				{
					SDToast.showToast("设置城市失败");
				}
			}
		});

		SDViewBinder.setTextView(tvName, model.getName());
	}

	public int getModelFirstLettersAscii(CitylistModel model)
	{
		if (model != null)
		{
			String letters = model.getSortLetters();
			if (!TextUtils.isEmpty(letters) && letters.length() > 0)
			{
				return letters.charAt(0);
			} else
			{
				return 0;
			}
		} else
		{
			return 0;
		}
	}

	public int getLettersAsciisFirstPosition(int lettersAscii)
	{
		Integer position = mMapLettersAsciisFirstPostion.get(lettersAscii);
		if (position == null)
		{
			boolean isFound = false;
			for (int i = 0; i < mListModel.size(); i++)
			{
				String sortStr = mListModel.get(i).getSortLetters();
				char firstChar = sortStr.toUpperCase().charAt(0);
				if (firstChar == lettersAscii)
				{
					isFound = true;
					position = i;
					mMapLettersAsciisFirstPostion.put(lettersAscii, position);
					break;
				}
			}
			if (!isFound)
			{
				position = -1;
			}
		}
		return position;
	}

	@Override
	public void notifyDataSetChanged()
	{
		if (mListModel != null)
		{
			Collections.sort(mListModel, mComparator);
		}
		super.notifyDataSetChanged();
	}

}

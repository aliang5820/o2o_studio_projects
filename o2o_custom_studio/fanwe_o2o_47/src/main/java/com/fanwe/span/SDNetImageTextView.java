package com.fanwe.span;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.library.span.builder.SDMapDataSpanBuilder;
import com.fanwe.library.span.builder.SDSpanBuilder;
import com.fanwe.library.span.model.MatcherInfo;
import com.fanwe.library.span.view.SDSpanTextView;

public class SDNetImageTextView extends SDSpanTextView
{

	public SDNetImageTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public SDNetImageTextView(Context context)
	{
		super(context);
	}

	public void setImage(String url)
	{
		SDNetImageSpan span = new SDNetImageSpan(this);
		span.setImage(url);
		getSpanBuilder().reset().addSpan(span).buildTextView();
	}

	@Override
	public SDSpanBuilder createSpanBuilder()
	{
		SDSpanBuilder sb = new SDMapDataSpanBuilder<String>(this)
		{
			@Override
			protected List<String> getPatternList()
			{
				List<String> list = new ArrayList<String>();
				list.add("\\[([^\\[\\]]+)\\]");
				return list;
			}

			@Override
			protected Object createSpanByFindKey(MatcherInfo matcherInfo, String data)
			{
				SDNetImageSpan span = new SDNetImageSpan(getTextView());
				span.setImage(data);
				return span;
			}
		};
		return sb;
	}

	@SuppressWarnings("unchecked")
	public void setMapData(Map<String, String> mapData)
	{
		SDSpanBuilder sb = getSpanBuilder();
		if (sb instanceof SDMapDataSpanBuilder)
		{
			SDMapDataSpanBuilder<String> mSb = (SDMapDataSpanBuilder<String>) sb;
			mSb.setMapData(mapData);
		}
	}

}

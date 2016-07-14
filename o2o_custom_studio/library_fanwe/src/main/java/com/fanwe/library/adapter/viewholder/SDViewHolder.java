package com.fanwe.library.adapter.viewholder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDAdapter;

public abstract class SDViewHolder
{

    protected Activity mActivity;
    protected SDAdapter<?> mAdapter;

    public void setActivity(Activity activity)
    {
        this.mActivity = activity;
    }

    public void setAdapter(SDAdapter<?> adapter)
    {
        this.mAdapter = adapter;
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V find(int id, View convertView)
    {
        return (V) convertView.findViewById(id);
    }

    public abstract int getLayoutId(int position, View convertView, ViewGroup parent);

    public abstract void initViews(int position, View convertView, ViewGroup parent);

    public void updateView(int position, View convertView, ViewGroup parent, Object itemModel)
    {

    }

    public abstract void bindData(int position, View convertView, ViewGroup parent, Object itemModel);

}

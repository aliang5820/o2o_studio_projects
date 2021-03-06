package com.fanwe.library.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class SDSimpleAdapter<T> extends SDAdapter<T> {

    public SDSimpleAdapter(List<T> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    protected View onGetView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            int layoutId = getLayoutId(position, convertView, parent);
            convertView = mInflater.inflate(layoutId, null);
        }
        bindData(position, convertView, parent, getItem(position));
        return convertView;
    }

    public abstract int getLayoutId(int position, View convertView, ViewGroup parent);

    public abstract void bindData(int position, View convertView, ViewGroup parent, T model);

}

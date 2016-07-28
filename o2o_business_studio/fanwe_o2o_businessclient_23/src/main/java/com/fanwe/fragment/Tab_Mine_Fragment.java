package com.fanwe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.businessclient.R;

/**
 * Created by Edison on 2016/7/28.
 */
public class Tab_Mine_Fragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.act_mine, container, false);
        return view;
    }
}

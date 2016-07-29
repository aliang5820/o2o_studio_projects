package com.fanwe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.MediaHomeActivity;
import com.fanwe.businessclient.R;

/**
 * Created by Edison on 2016/7/28.
 */
public class Tab_Mine_Fragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.act_mine, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.mine_media).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_media:
                startActivity(new Intent(getActivity(), MediaHomeActivity.class));
                break;
        }
    }
}

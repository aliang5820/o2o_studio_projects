package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.apply.City;
import com.fanwe.constant.Constant;
import com.fanwe.o2o.newo2o.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edison on 2016/7/25.
 * 合伙人页面
 */
public class ApplyHHRActivity extends BaseActivity {
    private TextView cityView;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<View> mViewList = new ArrayList<>();//页卡视图集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_apply_hhr);
        initTitle();
        initData();

        ViewPager mViewPager = (ViewPager) findViewById(R.id.vp_view);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);

        LayoutInflater mInflater = LayoutInflater.from(this);

        //添加页卡视图
        mViewList.add(mInflater.inflate(R.layout.act_apply_hyd, mTabLayout, false));//页卡视图1
        mViewList.add(mInflater.inflate(R.layout.act_apply_person_hhr, mTabLayout, false));//页卡视图2

        //添加页卡标题
        mTitleList.add("企业合伙人");
        mTitleList.add("个人合伙人");

        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));

        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        //mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("申请合伙人");
        cityView = (TextView) findViewById(R.id.city);
    }

    private void initData() {
        City city = (City) getIntent().getSerializableExtra(Constant.ExtraConstant.EXTRA_MODEL);
        cityView.setText(city.getName());
    }

    public void onConfirm(View view) {
        //申请合伙人
        startActivity(new Intent(mActivity, ApplyPayActivity.class));
    }

    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }
}

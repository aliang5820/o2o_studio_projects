package com.fanwe;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.fanwe.constant.Constant;
import com.fanwe.fragment.MediaRewardFragment;
import com.fanwe.o2o.newo2o.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edison on 2016/7/31.
 */
public class MediaRewardActivity extends BaseActivity {

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<Fragment> mFragmentList = new ArrayList<>();//页卡视图集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_media_reward);
        initView();
    }

    private void initView() {
        mTitle.setMiddleTextTop("自媒体");

        ViewPager mViewPager = (ViewPager) findViewById(R.id.vp_view);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);

        //添加页卡视图
        mFragmentList.add(MediaRewardFragment.getInstance(Constant.Reward.ORDER));
        mFragmentList.add(MediaRewardFragment.getInstance(Constant.Reward.HHR));
        mFragmentList.add(MediaRewardFragment.getInstance(Constant.Reward.HYD));

        //添加页卡标题
        mTitleList.add("订单奖励");
        mTitleList.add("合伙人奖励");
        mTitleList.add("会员店奖励");

        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));

        FragmentAdapter mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }
    }
}
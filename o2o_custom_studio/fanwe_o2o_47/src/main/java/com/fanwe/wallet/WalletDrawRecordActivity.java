package com.fanwe.wallet;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.fanwe.BaseActivity;
import com.fanwe.constant.Constant;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.fragment.MediaNextLevelFragment;
import com.fanwe.fragment.WalletDrawRecordFragment;
import com.fanwe.fragment.WalletRecordFragment;
import com.fanwe.model.LocalUserModel;
import com.fanwe.o2o.newo2o.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edison on 2016/7/31.
 * 提现记录
 */
public class WalletDrawRecordActivity extends BaseActivity {

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<Fragment> mFragmentList = new ArrayList<>();//页卡视图集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(Constant.TitleType.TITLE);
        setContentView(R.layout.act_wallet_record);
        initView();
    }

    private void initView() {
        mTitle.setMiddleTextTop("提现记录查询");
        ViewPager mViewPager = (ViewPager) findViewById(R.id.vp_view);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);

        //添加页卡视图
        mFragmentList.add(WalletDrawRecordFragment.getInstance(0));//全部
        mFragmentList.add(WalletDrawRecordFragment.getInstance(1));//已发放
        mFragmentList.add(WalletDrawRecordFragment.getInstance(2));//待审核
        mFragmentList.add(WalletDrawRecordFragment.getInstance(3));//未成功

        //添加页卡标题
        mTitleList.add("全部");
        mTitleList.add("已发放");
        mTitleList.add("待审核");
        mTitleList.add("未成功");

        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));

        //MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        FragmentAdapter mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mViewPager.setOffscreenPageLimit(4);
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

package com.fanwe;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.fanwe.application.App;
import com.fanwe.apply.City;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;
import com.fanwe.fragment.ApplyHHR_Fragment;
import com.fanwe.fragment.ApplyPersonHHR_Fragment;
import com.fanwe.model.ApplyInfoModel;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edison on 2016/7/25.
 * 合伙人页面
 */
public class ApplyHHRActivity extends TitleBaseActivity {

    @ViewInject(R.id.city)
    private TextView cityView;
    public String supplier_id;
    private ApplyInfoModel applyInfoModel;
    public City city;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<Fragment> mFragmentList = new ArrayList<>();//页卡视图集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_apply_hhr);
        initTitle();
        initView();
    }

    private void initTitle() {
        mTitle.setText("申请合伙人");
    }

    private void initView() {
        ViewPager mViewPager = (ViewPager) findViewById(R.id.vp_view);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);

        int type = getIntent().getIntExtra(Constant.ExtraConstant.EXTRA_TYPE, -1);
        if (type == Constant.Apply.EDIT_PERSON_HHR) {
            //编辑个人合伙人
            applyInfoModel = (ApplyInfoModel) getIntent().getSerializableExtra(Constant.ExtraConstant.EXTRA_OTHER_MODEL);
            //添加页卡视图
            mFragmentList.add(ApplyPersonHHR_Fragment.getInstance(applyInfoModel));
            //添加页卡标题
            mTitleList.add("个人合伙人");
            //添加tab选项卡
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
            //初始化数据
            city = new City();
            city.setId(applyInfoModel.getArea_id());
            city.setName(applyInfoModel.getArea());
            supplier_id = applyInfoModel.getSupplier_id();
        } else if(type == Constant.Apply.EDIT_COMPANY_HHR) {
            //编辑企业合伙人
            applyInfoModel = (ApplyInfoModel) getIntent().getSerializableExtra(Constant.ExtraConstant.EXTRA_OTHER_MODEL);
            //添加页卡视图
            mFragmentList.add(ApplyHHR_Fragment.getInstance(applyInfoModel));
            //添加页卡标题
            mTitleList.add("企业合伙人");
            //添加tab选项卡
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
            //初始化数据
            city = new City();
            city.setId(applyInfoModel.getArea_id());
            city.setName(applyInfoModel.getArea());
            supplier_id = applyInfoModel.getSupplier_id();
        } else {
            city = (City) getIntent().getSerializableExtra(Constant.ExtraConstant.EXTRA_MODEL);
            supplier_id = App.getApp().getmLocalUser().getSupplier_id();
            //添加页卡视图
            mFragmentList.add(ApplyHHR_Fragment.getInstance(null));
            mFragmentList.add(ApplyPersonHHR_Fragment.getInstance(null));
            //添加页卡标题
            mTitleList.add("企业合伙人");
            mTitleList.add("个人合伙人");
            //添加tab选项卡
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        }
        cityView.setText(city.getName());

        FragmentAdapter mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
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

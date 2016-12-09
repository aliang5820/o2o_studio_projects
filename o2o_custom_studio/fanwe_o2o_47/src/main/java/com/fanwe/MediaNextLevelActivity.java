package com.fanwe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.constant.Constant;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.fragment.MediaNextLevelFragment;
import com.fanwe.model.LocalUserModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.QRCodeUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edison on 2016/7/31.
 * 我的下级
 */
public class MediaNextLevelActivity extends BaseActivity {

    @ViewInject(R.id.user_name)
    private TextView user_name;

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<Fragment> mFragmentList = new ArrayList<>();//页卡视图集合

    @ViewInject(R.id.user_level)
    private TextView user_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(Constant.TitleType.TITLE);
        setContentView(R.layout.act_media_next_level);
        initView();
    }

    public void onPoster(View view) {
        startActivity(new Intent(mActivity, MediaPosterActivity.class));
    }

    private void initView() {
        mTitle.setMiddleTextTop("自媒体");
        int user_type = getIntent().getIntExtra(Constant.ExtraConstant.EXTRA_TYPE, 0);
        showUserLevel(user_type);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.vp_view);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);

        //添加页卡视图
        /*LayoutInflater mInflater = LayoutInflater.from(this);
        mViewList.add(mInflater.inflate(R.layout.act_apply_hyd, mTabLayout, false));//页卡视图1
        mViewList.add(mInflater.inflate(R.layout.act_apply_person_hhr, mTabLayout, false));//页卡视图2
        mViewList.add(mInflater.inflate(R.layout.act_apply_person_hhr, mTabLayout, false));//页卡视图2*/
        mFragmentList.add(MediaNextLevelFragment.getInstance(1));
        mFragmentList.add(MediaNextLevelFragment.getInstance(2));
        mFragmentList.add(MediaNextLevelFragment.getInstance(3));

        //添加页卡标题
        mTitleList.add("金粉");
        mTitleList.add("银粉");
        mTitleList.add("铜粉");

        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));

        //MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        FragmentAdapter mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。

        LocalUserModel localUserModel = LocalUserModelDao.queryModel();
        user_name.setText(localUserModel.getUser_name());
    }

    private void showUserLevel(int user_type) {
        //0消费股东  1 会员店 ，2 商户合伙人，3个人合伙人
        String type = "消费股东";
        switch (user_type) {
            case 0:
                type = "消费股东";
                break;
            case 1:
                type = "会员店";
                break;
            case 2:
                type = "商户合伙人";
                break;
            case 3:
                type = "个人合伙人";
                break;
        }
        user_level.setText(type);
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

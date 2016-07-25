package com.fanwe;

import android.app.ActionBar.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.businessclient.R;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-6-4 下午5:32:28 类说明
 */
public class TitleBaseActivity extends BaseActivity {
    protected TextView mTitle;
    private LinearLayout mLlBack;

    @Override
    public void setContentView(int layoutResID) {
        View contentView = getLayoutInflater().inflate(layoutResID, null);

        setContentView(contentView);
    }

    private void registerTitleView(View titleView) {
        mLlBack = (LinearLayout) titleView.findViewById(R.id.ll_back);
        mLlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitle = (TextView) titleView.findViewById(R.id.tv_title);
    }

    @Override
    public void setContentView(View view) {
        ViewGroup viewGroup = null;
        View titleView = getLayoutInflater().inflate(R.layout.include_title, null);
        if (titleView != null) {
            registerTitleView(titleView);

            LinearLayout linAll = new LinearLayout(this);
            linAll.setOrientation(LinearLayout.VERTICAL);
            linAll.addView(titleView);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            linAll.addView(view, lp);
            viewGroup = linAll;
        } else {
            viewGroup = (ViewGroup) view;
        }
        super.setContentView(viewGroup);
    }

}

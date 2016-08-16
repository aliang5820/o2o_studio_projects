package com.fanwe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.apply.City;
import com.fanwe.apply.CityListActModel;
import com.fanwe.apply.MyLetterListView;
import com.fanwe.apply.PingYinUtil;
import com.fanwe.constant.Constant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Edison on 2016/7/24.
 * 城市选择页面
 */
public class ApplyCityActivity extends BaseActivity implements AbsListView.OnScrollListener {
    private BaseAdapter adapter;
    private ResultListAdapter resultListAdapter;
    private ListView personList;
    private ListView resultList;
    private TextView overlay; // 对话框首字母textview
    private MyLetterListView letterListView; // A-Z listview
    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private Handler handler;
    private OverlayThread overlayThread; // 显示首字母对话框
    private List<City> allCity_lists; // 所有城市列表
    private List<City> city_lists;// 城市列表
    private List<City> city_result;
    private TextView tvNoResult;//没有搜索结果
    private City selectedCity;//已选择的城市

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_apply_city);
        initTitle();
        personList = (ListView) findViewById(R.id.list_view);
        allCity_lists = new ArrayList<>();
        city_result = new ArrayList<>();
        resultList = (ListView) findViewById(R.id.search_result);
        EditText sh = (EditText) findViewById(R.id.sh);
        tvNoResult = (TextView) findViewById(R.id.tv_noresult);
        sh.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("".equals(s.toString())) {
                    letterListView.setVisibility(View.VISIBLE);
                    personList.setVisibility(View.VISIBLE);
                    resultList.setVisibility(View.GONE);
                    tvNoResult.setVisibility(View.GONE);
                } else {
                    city_result.clear();
                    letterListView.setVisibility(View.GONE);
                    personList.setVisibility(View.GONE);
                    getResultCityList(PingYinUtil.getPingYin(s.toString()));
                    if (city_result.size() <= 0) {
                        tvNoResult.setVisibility(View.VISIBLE);
                        resultList.setVisibility(View.GONE);
                    } else {
                        tvNoResult.setVisibility(View.GONE);
                        resultList.setVisibility(View.VISIBLE);
                        resultListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        letterListView = (MyLetterListView) findViewById(R.id.MyLetterListView01);
        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        alphaIndexer = new HashMap<>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //选中城市
                selectedCity = allCity_lists.get(position);
                for (City city : allCity_lists) {
                    if (TextUtils.equals(city.getName(), selectedCity.getName())) {
                        city.setSelected(true);
                    } else {
                        city.setSelected(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        personList.setAdapter(adapter);
        personList.setOnScrollListener(this);
        resultListAdapter = new ResultListAdapter(this, city_result);
        resultList.setAdapter(resultListAdapter);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //选中城市
                selectedCity = city_result.get(position);
                for (City city : city_result) {
                    if (TextUtils.equals(city.getName(), selectedCity.getName())) {
                        city.setSelected(true);
                    } else {
                        city.setSelected(false);
                    }
                }
                resultListAdapter.notifyDataSetChanged();
            }
        });
        initOverlay();
        cityInit();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("选择地区");
    }

    //下一步
    public void onConfirm(View view) {
        //如果没有选中城市，进行提示
        if(selectedCity == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setMessage("请选择申请城市");
            builder.setTitle("提示");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.create().show();
        } else {
            int appType = getIntent().getIntExtra(Constant.ExtraConstant.EXTRA_TYPE, -1);
            Intent intent;
            if(appType == Constant.Apply.HHR) {
                //选择合伙人
                intent = new Intent(mActivity, ApplyHHRActivity.class);
                intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Apply.HHR);
                intent.putExtra(Constant.ExtraConstant.EXTRA_MODEL, selectedCity);
            } else {
                //选择会员店
                intent = new Intent(mActivity, ApplyHYDActivity.class);
                intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Apply.HYD);
                intent.putExtra(Constant.ExtraConstant.EXTRA_MODEL, selectedCity);
            }
            startActivity(intent);
        }
    }

    private void cityInit() {
        requestCityList();
    }

    @SuppressWarnings("unchecked")
    private void getResultCityList(String keyword) {
        for(City city:allCity_lists) {
            if(city.getUname().contains(keyword)) {
                city_result.add(city);
            }
        }
        Collections.sort(city_result, comparator);
    }

    /**
     * a-z排序
     */
    @SuppressWarnings("rawtypes")
    Comparator comparator = new Comparator<City>() {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getUname().substring(0, 1);
            String b = rhs.getUname().substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    private void setAdapter(List<City> list) {
        adapter = new ListAdapter(this, list);
        personList.setAdapter(adapter);
    }

    private class ResultListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<City> results = new ArrayList<>();

        public ResultListAdapter(Context context, List<City> results) {
            inflater = LayoutInflater.from(context);
            this.results = results;
        }

        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_apply_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            City city = results.get(position);
            viewHolder.name.setText(city.getName());
            if (city.isSelected || (selectedCity != null && TextUtils.equals(city.getName(), selectedCity.getName()))) {
                viewHolder.name.setBackgroundResource(R.color.city_bg_color_selected);
                viewHolder.name.setTextColor(getResources().getColor(R.color.white));
            } else {
                viewHolder.name.setBackgroundResource(R.color.transparent);
                viewHolder.name.setTextColor(getResources().getColor(R.color.city_text_color_selected));
            }
            return convertView;
        }

        class ViewHolder {
            TextView name;
        }
    }

    public class ListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<City> list;

        public ListAdapter(Context context, List<City> list) {
            this.inflater = LayoutInflater.from(context);
            this.list = list;
            this.context = context;
            alphaIndexer = new HashMap<>();
            String[] sections = new String[list.size()]; // 存放存在的汉语拼音首字母
            for (int i = 0; i < list.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = getAlpha(list.get(i).getUname());
                // 上一个汉语拼音首字母，如果不存在为" "
                String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1).getUname()) : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(list.get(i).getUname());
                    alphaIndexer.put(name, i);
                    sections[i] = name;
                }
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        ViewHolder holder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_apply_list_item, null);
                holder = new ViewHolder();
                holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            City city = list.get(position);
            if(city.isSelected || (selectedCity != null && TextUtils.equals(city.getName(), selectedCity.getName()))) {
                holder.name.setBackgroundResource(R.color.city_bg_color_selected);
                holder.name.setTextColor(getResources().getColor(R.color.white));
            } else {
                holder.name.setBackgroundResource(R.color.transparent);
                holder.name.setTextColor(getResources().getColor(R.color.city_text_color_selected));
            }

            holder.name.setText(city.getName());
            String currentStr = getAlpha(city.getUname());
            String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getUname()) : " ";
            if (!previewStr.equals(currentStr)) {
                holder.alpha.setVisibility(View.VISIBLE);
                holder.alpha.setText(currentStr);
            } else {
                holder.alpha.setVisibility(View.GONE);
            }
            return convertView;
        }

        private class ViewHolder {
            TextView alpha; // 首字母标题
            TextView name; // 城市名字
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private boolean mReady;

    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {
        mReady = true;
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.view_overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    private boolean isScroll = false;

    private class LetterListViewListener implements MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            isScroll = false;
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                personList.setSelection(position);
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 1000);
            }
        }
    }

    // 设置overlay不可见
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    // 获得汉语拼音首字母
    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else {
            return "#";
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }

        if (mReady) {
            String name = allCity_lists.get(firstVisibleItem).getName();
            String pinyin = allCity_lists.get(firstVisibleItem).getUname();
            String text = PingYinUtil.converterToFirstSpell(pinyin).substring(0, 1).toUpperCase();
            overlay.setText(text);
            overlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            // 延迟一秒后执行，让overlay为不可见
            handler.postDelayed(overlayThread, 1000);
        }
    }

    /**
     * 获取城市列表
     */
    private void requestCityList() {
        RequestModel model = new RequestModel();
        model.putCtl("member");
        model.putAct("get_city_list");
        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<CityListActModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }

            @Override
            public void onSuccess(CityListActModel actModel) {
                if (actModel.getStatus() == 1) {
                    LogUtil.e("cityListSize：" + actModel.getCitylist().size());
                    city_lists = actModel.getCitylist();
                    Collections.sort(city_lists, comparator);
                    allCity_lists.addAll(city_lists);
                    setAdapter(allCity_lists);
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }
        });
    }
}

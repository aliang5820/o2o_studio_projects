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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
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
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.JsonUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

import java.io.IOException;
import java.io.InputStream;
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
    private ListView sourceList;
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
    private boolean mReady;
    private LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(Constant.TitleType.TITLE);
        setContentView(R.layout.act_apply_city);
        initTitle();
        inflater = LayoutInflater.from(mActivity);
        sourceList = (ListView) findViewById(R.id.list_view);
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
                    sourceList.setVisibility(View.VISIBLE);
                    resultList.setVisibility(View.GONE);
                    tvNoResult.setVisibility(View.GONE);
                } else {
                    city_result.clear();
                    letterListView.setVisibility(View.GONE);
                    sourceList.setVisibility(View.GONE);
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
        /*sourceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
        });*/
        sourceList.setAdapter(adapter);
        sourceList.setOnScrollListener(this);
        resultListAdapter = new ResultListAdapter(city_result);
        resultList.setAdapter(resultListAdapter);
        /*resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
        });*/
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
            SDToast.showToast("请选择申请地区");
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
        adapter = new ListAdapter(list);
        sourceList.setAdapter(adapter);
    }

    //搜索结果adapter
    private class ResultListAdapter extends ListAdapter {

        public ResultListAdapter(List<City> results) {
            super(results);
        }
    }

    //网络结果adapter
    public class ListAdapter extends BaseAdapter {
        private List<City> cityList;
        private ViewHolder holder;

        public ListAdapter(List<City> list) {
            this.cityList = list;
            alphaIndexer = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = getAlpha(list.get(i).getUname());
                // 上一个汉语拼音首字母，如果不存在为" "
                String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1).getUname()) : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(list.get(i).getUname());
                    alphaIndexer.put(name, i);
                }
            }
        }

        @Override
        public int getCount() {
            return cityList.size();
        }

        @Override
        public Object getItem(int position) {
            return cityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_apply_list_item, null);
                holder = new ViewHolder();
                holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
                holder.districtGridView = (GridView) convertView.findViewById(R.id.districtGridView);
                holder.districtGridView.setAdapter(new DistrictAdapter());
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //显示区县数据
            City city = cityList.get(position);
            ((DistrictAdapter)holder.districtGridView.getAdapter()).setDataList(city.getDistrict());
            String currentStr = getAlpha(city.getUname());
            String previewStr = (position - 1) >= 0 ? getAlpha(cityList.get(position - 1).getUname()) : " ";
            if (!previewStr.equals(currentStr)) {
                holder.alpha.setVisibility(View.VISIBLE);
                holder.alpha.setText(city.getName());
            } else {
                holder.alpha.setVisibility(View.GONE);
            }
            return convertView;
        }

        private class ViewHolder {
            TextView alpha; // 首字母标题
            GridView districtGridView; // 城市名字
        }
    }

    private class DistrictAdapter extends BaseAdapter {
        private List<City> districtList = new ArrayList<>();
        private ViewHolder holder;

        public void setDataList(List<City> districtList) {
            this.districtList.clear();
            this.districtList.addAll(districtList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return districtList.size();
        }

        @Override
        public Object getItem(int position) {
            return districtList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return districtList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_apply_district_item, null);
                holder = new ViewHolder();
                holder.districtItem = (RadioButton) convertView.findViewById(R.id.districtItem);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final City district = districtList.get(position);
            holder.districtItem.setText(district.getName());
            if(district.isSelected) {
                selectedCity = district;
                holder.districtItem.setChecked(true);
                holder.districtItem.setTextColor(getResources().getColor(R.color.white));
            } else {
                holder.districtItem.setChecked(false);
                holder.districtItem.setTextColor(getResources().getColor(R.color.gray9));
            }

            holder.districtItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    isScroll = false;
                    if(isChecked) {
                        //遍历数据，记录选择的区县
                        for(City city:allCity_lists) {
                            List<City> temp = city.getDistrict();
                            for(City j:temp) {
                                if(j.getId() == district.getId()) {
                                    j.setSelected(true);
                                } else {
                                    j.setSelected(false);
                                }
                            }
                        }
                    } else {
                        district.setSelected(false);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }

        private class ViewHolder {
            RadioButton districtItem; // 区县名字
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

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
                sourceList.setSelection(position);
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
                //TODO BEGIN
                //actModel = getTextData();
                //TODO END
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

    //todo
    private CityListActModel getTextData() {
        String testData = "";
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = getAssets().open("city.txt");
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            testData = new String(buffer, "UTF-8");
        } catch (IOException e) {
            // Should never happen!
            e.printStackTrace();
        }
        CityListActModel actModel = JsonUtil.json2Object(testData, CityListActModel.class);
        return actModel;
    }
}

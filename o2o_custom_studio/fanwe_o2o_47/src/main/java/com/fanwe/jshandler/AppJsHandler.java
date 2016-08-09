package com.fanwe.jshandler;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.fanwe.DiscoveryActivity;
import com.fanwe.DistributionManageActivity;
import com.fanwe.DistributionStoreWapActivity;
import com.fanwe.EventDetailActivity;
import com.fanwe.EventListActivity;
import com.fanwe.GoodsListActivity;
import com.fanwe.MainActivity;
import com.fanwe.NearbyVipActivity;
import com.fanwe.NoticeDetailActivity;
import com.fanwe.NoticeListActivity;
import com.fanwe.ScoresListActivity;
import com.fanwe.StoreDetailActivity;
import com.fanwe.StoreListActivity;
import com.fanwe.StorePayListActivity;
import com.fanwe.TuanDetailActivity;
import com.fanwe.TuanListActivity;
import com.fanwe.YouHuiDetailActivity;
import com.fanwe.YouHuiListActivity;
import com.fanwe.app.App;
import com.fanwe.constant.Constant.IndexType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.fragment.EventListFragment;
import com.fanwe.fragment.GoodsListFragment;
import com.fanwe.fragment.ScoresListFragment;
import com.fanwe.fragment.StoreListFragment;
import com.fanwe.fragment.StorePayListFragment;
import com.fanwe.fragment.TuanListFragment;
import com.fanwe.fragment.YouHuiListFragment;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.work.AppRuntimeWorker;
import com.sunday.eventbus.SDEventManager;

/**
 * app详情页js回调处理类
 *
 * @author Administrator
 */
public class AppJsHandler extends BaseJsHandler {
    private static final String DEFAULT_NAME = "App";

    public AppJsHandler(String name, Activity activity) {
        super(name, activity);
    }

    public AppJsHandler(Activity activity) {
        this(DEFAULT_NAME, activity);
    }

    @JavascriptInterface
    public void app_detail(int type, int id) {
        Intent intent = null;
        switch (type) {
            case IndexType.URL:
                break;
            case IndexType.TUAN_LIST:
                intent = new Intent(App.getApplication(), TuanListActivity.class);
                intent.putExtra(TuanListFragment.EXTRA_CATE_ID, id);
                break;
            case IndexType.GOODS_LIST:
                intent = new Intent(App.getApplication(), GoodsListActivity.class);
                intent.putExtra(GoodsListFragment.EXTRA_CATE_ID, id);
                break;
            case IndexType.SCORE_LIST:
                intent = new Intent(App.getApplication(), ScoresListActivity.class);
                intent.putExtra(ScoresListFragment.EXTRA_CATE_ID, id);
                break;
            case IndexType.EVENT_LIST:
                intent = new Intent(App.getApplication(), EventListActivity.class);
                intent.putExtra(EventListFragment.EXTRA_CATE_ID, id);
                break;
            case IndexType.YOUHUI_LIST:
                intent = new Intent(App.getApplication(), YouHuiListActivity.class);
                intent.putExtra(YouHuiListFragment.EXTRA_CATE_ID, id);
                break;
            case IndexType.STORE_LIST:
                intent = new Intent(App.getApplication(), StoreListActivity.class);
                intent.putExtra(StoreListFragment.EXTRA_CATE_ID, id);
                break;
            case IndexType.STORE_PAY_LIST:
                intent = new Intent(App.getApplication(), StorePayListActivity.class);
                intent.putExtra(StorePayListFragment.EXTRA_CATE_ID, id);
                break;
            case IndexType.NOTICE_LIST:
                intent = new Intent(App.getApplication(), NoticeListActivity.class);
                break;
            case IndexType.DEAL_DETAIL:
                intent = new Intent(App.getApplication(), TuanDetailActivity.class);
                intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, id);
                break;
            case IndexType.EVENT_DETAIL:
                intent = new Intent(App.getApplication(), EventDetailActivity.class);
                intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, id);
                break;
            case IndexType.YOUHUI_DETAIL:
                intent = new Intent(App.getApplication(), YouHuiDetailActivity.class);
                intent.putExtra(YouHuiDetailActivity.EXTRA_YOUHUI_ID, id);
                break;
            case IndexType.STORE_DETAIL:
                intent = new Intent(App.getApplication(), StoreDetailActivity.class);
                intent.putExtra(StoreDetailActivity.EXTRA_MERCHANT_ID, id);
                break;
            case IndexType.NOTICE_DETAIL:
                intent = new Intent(App.getApplication(), NoticeDetailActivity.class);
                intent.putExtra(NoticeDetailActivity.EXTRA_NOTICE_ID, id);
                break;
            case IndexType.SCAN:
                SDEventManager.post(SDActivityManager.getInstance().getLastActivity().getClass(), EnumEventTag.START_SCAN_QRCODE.ordinal());
                return;
            case IndexType.NEARUSER:
                intent = new Intent(App.getApplication(), NearbyVipActivity.class);
                break;
            case IndexType.DISTRIBUTION_STORE:
                intent = new Intent(App.getApplication(), DistributionStoreWapActivity.class);
                break;
            case IndexType.DISTRIBUTION_MANAGER:
                intent = new Intent(App.getApplication(), DistributionManageActivity.class);
                break;
            case IndexType.DISCOVERY:
                intent = new Intent(App.getApplication(), DiscoveryActivity.class);
                break;
            case IndexType.TAKEAWAY_LIST:
                // TODO 跳到外卖主界面，定位到外卖列表
                // intent = new Intent(App.getApplication(), MainActivity_dc.class);
                // intent.putExtra(MainActivity_dc.EXTRA_SELECT_INDEX, 0);
                break;
            case IndexType.RESERVATION_LIST:
                // TODO 跳到外卖主界面，定位到订座列表
                // intent = new Intent(App.getApplication(), MainActivity_dc.class);
                // intent.putExtra(MainActivity_dc.EXTRA_SELECT_INDEX, 1);
                break;
            default:

                break;
        }
        startActivity(intent);
    }

    @JavascriptInterface
    public void close_page() {
        finish();
    }

    @JavascriptInterface
    public void login() {
        Activity activity = SDActivityManager.getInstance().getLastActivity();
        AppRuntimeWorker.isLogin(activity);
    }

    @JavascriptInterface
    public void page_title(String title) {

    }

    @JavascriptInterface
    public void start_main() {
        Intent intent = new Intent(mActivity, MainActivity.class);
        startActivity(intent);
    }

}

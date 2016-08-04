package com.fanwe.model;

import java.util.List;

/**
 * Created by Edison on 2016/7/31.
 */
public class MediaNextLevelPageModel extends BaseActModel {

    private PageModel page = null;

    private List<MediaNextLevelCtlItemModel> item = null;

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }

    public List<MediaNextLevelCtlItemModel> getItem() {
        return item;
    }

    public void setItem(List<MediaNextLevelCtlItemModel> item) {
        this.item = item;
    }
}

package com.fanwe.model;

import java.util.List;

/**
 * Created by Edison on 2016/8/1.
 */
public class MediaRewardPageModel<T> extends BaseCtlActModel {

    private PageModel page = null;

    private List<T> item = null;

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }

    public List<T> getItem() {
        return item;
    }

    public void setItem(List<T> item) {
        this.item = item;
    }
}

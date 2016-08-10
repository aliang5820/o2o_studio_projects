package com.fanwe.model;

import java.util.List;

import lombok.Data;

/**
 * Created by Edison on 2016/8/1.
 */
@Data
public class MediaRewardPageModel extends BaseActModel {
    private PageModel page;
    private double total_num;
    private List<MediaRewardItemModel> data_list;
}

package com.fanwe.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/8/1.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MediaRewardPageModel extends BaseActModel {
    private PageModel page;
    private double total_num;
    private List<MediaRewardItemModel> data_list;
}

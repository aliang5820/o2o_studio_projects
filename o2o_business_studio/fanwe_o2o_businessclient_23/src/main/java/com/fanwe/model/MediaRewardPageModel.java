package com.fanwe.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/8/1.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MediaRewardPageModel extends BaseCtlActModel {
    private PageModel page;
    private double totalRewardMoney;
    private List<MediaRewardItemModel> item;
}

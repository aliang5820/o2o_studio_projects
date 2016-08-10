package com.fanwe.model;

import java.util.List;

import lombok.Data;

/**
 * Created by Edison on 2016/7/31.
 */
@Data
public class MediaNextLevelPageModel extends BaseCtlActModel {
    private PageModel page;
    private List<MediaNextLevelItemModel> relationList;
}

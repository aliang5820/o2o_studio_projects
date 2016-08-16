package com.fanwe.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/7/31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MediaNextLevelPageModel extends BaseCtlActModel {
    private PageModel page;
    private List<MediaNextLevelItemModel> relationList;
}

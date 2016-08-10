package com.fanwe.model;

import java.util.List;

import lombok.Data;

/**
 * Created by Edison on 2016/8/10.
 */
@Data
public class ApplyServiceTypeCtlActModel extends BaseCtlActModel {

    private List<ApplyServiceTypeModel> typeList;
}

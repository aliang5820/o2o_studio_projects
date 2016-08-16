package com.fanwe.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/8/10.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApplyServiceTypeCtlActModel extends BaseCtlActModel {

    private List<ApplyServiceTypeModel> typeList;
}

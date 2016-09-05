package com.fanwe.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yhz
 * @create time 2014-7-31
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BizUserCtlDoLoginActModel extends BaseCtlActModel {
    private ApplyResultModel check_info;//审核结果
    private AccountInfoModel account_info;
}

package com.fanwe.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/11/8.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WalletBindResultModel extends BaseActModel implements Serializable {
    private boolean bind_status;//绑定是否成功
    private String info;//提示信息
    private String card_id;
}

package com.fanwe.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/11/8.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WalletRecordPageModel extends BaseActModel {
    private PageModel page;
    private List<WalletRecordModel> item;
}

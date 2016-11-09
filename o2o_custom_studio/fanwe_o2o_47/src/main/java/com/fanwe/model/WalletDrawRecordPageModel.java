package com.fanwe.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/11/10.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WalletDrawRecordPageModel extends BaseActModel {
    private PageModel page;
    private List<WalletDrawRecordModel> item;
}

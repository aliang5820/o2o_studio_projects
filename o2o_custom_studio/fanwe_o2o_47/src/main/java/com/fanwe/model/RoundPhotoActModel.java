package com.fanwe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2017/2/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoundPhotoActModel extends BaseActModel {
    private String location_address;//全景照片地址
}

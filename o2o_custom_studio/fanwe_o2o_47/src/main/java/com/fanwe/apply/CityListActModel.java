package com.fanwe.apply;

import com.fanwe.model.BaseActModel;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/8/9.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CityListActModel extends BaseActModel {

    private List<City> citylist;
}

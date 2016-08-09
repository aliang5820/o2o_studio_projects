package com.fanwe.apply;

import java.io.Serializable;

import lombok.Data;

@Data
public class City implements Serializable {
    public int id;
    public String name;
    public String shorthand;
    public boolean isSelected;
}

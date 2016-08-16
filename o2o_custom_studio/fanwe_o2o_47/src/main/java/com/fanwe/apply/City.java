package com.fanwe.apply;

import java.io.Serializable;

import lombok.Data;

@Data
public class City implements Serializable {
    public int id;
    public int pid;
    public String name;
    public String uname;
    public int region_level;
    public boolean isSelected;
}

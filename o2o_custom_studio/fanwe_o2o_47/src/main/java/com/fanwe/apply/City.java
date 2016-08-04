package com.fanwe.apply;

import java.io.Serializable;

public class City implements Serializable {
    public String name;
    public String pinyi;
    public boolean isSelected;

    public City(String name, String pinyi) {
        super();
        this.name = name;
        this.pinyi = pinyi;
    }

    public City() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyi() {
        return pinyi;
    }

    public void setPinyi(String pinyi) {
        this.pinyi = pinyi;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

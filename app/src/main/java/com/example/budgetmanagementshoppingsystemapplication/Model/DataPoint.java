package com.example.budgetmanagementshoppingsystemapplication.Model;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class DataPoint {
    int xValue;
    Float yValue;

    public DataPoint(Integer xValue, Float yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public Integer getxValue() {
        return xValue;
    }

    public void setxValue(int xValue) {
        this.xValue = xValue;
    }

    public Float getyValue() {
        return yValue;
    }

    public void setyValue(Float yValue) {
        this.yValue = yValue;
    }
}

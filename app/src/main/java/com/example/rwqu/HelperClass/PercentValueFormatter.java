package com.example.rwqu.HelperClass;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class PercentValueFormatter extends ValueFormatter {

    private DecimalFormat mFormat;

    public PercentValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + " %";
    }
}


package com.example.rwqu.HelperClass;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class PercentageValueFormatter extends ValueFormatter {

    private int totalVotes;

    public PercentageValueFormatter(HorizontalBarChart barChart) {
    }

    @Override
    public String getFormattedValue(float value) {
        // Calculate the percentage based on totalVotes
        int percentage = Math.round(value * 100 / totalVotes);
        return percentage + "%";
    }


}

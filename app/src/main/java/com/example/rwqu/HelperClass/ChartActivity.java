package com.example.rwqu.HelperClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rwqu.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChartActivity extends AppCompatActivity {

    private HorizontalBarChart chart1, chart2, chart3;
    private TextView percentage1, percentage2, percentage3;
    private Map<String, Integer> votesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        // Initialize the HorizontalBarCharts
        chart1 = findViewById(R.id.chart1);
        chart2 = findViewById(R.id.chart2);
        chart3 = findViewById(R.id.chart3);

        // Initialize the TextViews for percentages
        percentage1 = findViewById(R.id.percentage1);
        percentage2 = findViewById(R.id.percentage2);
        percentage3 = findViewById(R.id.percentage3);

        // Disable chart interaction (clicking)
        disableChartInteraction(chart1);
        disableChartInteraction(chart2);
        disableChartInteraction(chart3);

        // Get data from Firebase and update the charts
        fetchVotesDataFromFirebase();
    }

    private void fetchVotesDataFromFirebase() {
        // Replace "Transaction" with your Firebase node reference
        FirebaseDatabase.getInstance().getReference("Transaction/votes")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        votesData = new HashMap<>();
                        for (DataSnapshot voteSnapshot : dataSnapshot.getChildren()) {
                            String voteKey = voteSnapshot.getKey();
                            int numOfVotes = voteSnapshot.getValue(Integer.class);
                            votesData.put(voteKey, numOfVotes);
                        }

                        updateCharts();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error here
                    }
                });
    }

    private void updateCharts() {
        if (votesData == null || votesData.isEmpty()) {
            return;
        }

        customizeBarChart(chart1, "numOfVotes_1", percentage1, 100);
        customizeBarChart(chart2, "numOfVotes_2", percentage2, 100);
        customizeBarChart(chart3, "numOfVotes_3", percentage3, 100);
    }

    private void customizeBarChart(HorizontalBarChart chart, String voteKey, TextView tvPercent, float maxScale) {
        int numOfVotes = votesData.get(voteKey);
        float totalVotes = getTotalVotes();

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, (float) numOfVotes * maxScale / totalVotes));

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColor(Color.BLUE);
        barDataSet.setDrawValues(false); // Hide values on the bars

        BarData barData = new BarData(barDataSet);

        // Hide labels and values on the X-axis
        chart.getXAxis().setEnabled(false);

        // Configure Y-axis scale
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(maxScale);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawZeroLine(true);
        chart.getAxisRight().setEnabled(false);

        // Hide grid lines and description
        chart.setDrawGridBackground(false);
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);

        // Hide legend
        chart.getLegend().setEnabled(false);

        // Assign the BarData to the chart
        chart.setData(barData);
        chart.invalidate();

        // Set percentage text
        tvPercent.setText(String.format("%.1f%%", (float) numOfVotes * 100 / totalVotes));
    }

    private void disableChartInteraction(HorizontalBarChart chart) {
        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setClickable(false);
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // Do nothing
            }

            @Override
            public void onNothingSelected() {
                // Do nothing
            }
        });
    }

    private float getTotalVotes() {
        int totalVotes = 0;
        for (int numOfVotes : votesData.values()) {
            totalVotes += numOfVotes;
        }
        return totalVotes;
    }
}
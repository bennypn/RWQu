package com.example.rwqu.HelperClass;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rwqu.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder> {

    private List<Candidate> candidates;
    private int totalVotes;

    public CandidateAdapter(List<Candidate> candidates, int totalVotes) {
        this.candidates = candidates;
        this.totalVotes = totalVotes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_candidate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Candidate candidate = candidates.get(position);

        // Set candidate name and number of votes
        holder.tvCandidateName.setText(candidate.getCandidateName());
        holder.tvNumOfVotes.setText(String.valueOf(candidate.getNumOfVotes()));

        // Set barchart
        holder.customizeBarChart(candidate.getNumOfVotes());

        // Calculate and set percentage
        float percentage = (float) candidate.getNumOfVotes() * 100 / totalVotes;
        holder.tvPercent.setText(String.format("%.1f%%", percentage));

        // Load image using Glide library
        Glide.with(holder.itemView.getContext())
                .load(candidate.getImg())
                .apply(RequestOptions.placeholderOf(R.drawable.image_1))
                .into(holder.imgCandidate);
    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }

    private float getMaxPercentage() {
        float maxPercentage = 0;
        for (Candidate candidate : candidates) {
            float percentage = (float) candidate.getNumOfVotes() * 100 / totalVotes;
            if (percentage > maxPercentage) {
                maxPercentage = percentage;
            }
        }
        return maxPercentage;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCandidate;
        private TextView tvCandidateName;
        private TextView tvNumOfVotes;
        private TextView tvPercent;
        private HorizontalBarChart horizontalBarChart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCandidate = itemView.findViewById(R.id.candidateImageView);
            tvCandidateName = itemView.findViewById(R.id.tvCandidateName);
            tvNumOfVotes = itemView.findViewById(R.id.tvNumOfVotes);
            tvPercent = itemView.findViewById(R.id.tvPercent);
            horizontalBarChart = itemView.findViewById(R.id.horizontalBarChart);
        }

        private void customizeBarChart(int numOfVotes) {
            horizontalBarChart.setDrawBarShadow(false);
            horizontalBarChart.setDrawValueAboveBar(true);
            horizontalBarChart.setDrawGridBackground(false);
            horizontalBarChart.setTouchEnabled(false);
            horizontalBarChart.setDragEnabled(false);
            horizontalBarChart.setScaleEnabled(false);
            horizontalBarChart.setPinchZoom(false);
            horizontalBarChart.setDoubleTapToZoomEnabled(false);
            horizontalBarChart.getLegend().setEnabled(false);
            horizontalBarChart.getDescription().setEnabled(false);
            horizontalBarChart.setClickable(false);

            // Create a BarData object and assign it to the chart
            BarData barData = getBarData(numOfVotes);
            barData.setValueTextSize(12f);

            // Calculate maxScale based on the maximum percentage
            float maxPercentage = getMaxPercentage();
            float maxScale = maxPercentage + 10; // Tambahkan padding 10 untuk memberikan ruang atas pada grafik
            barData.setBarWidth(0.9f); // Atur lebar batang berdasarkan maxScale

            // Hide labels and values on the X-axis
            horizontalBarChart.getXAxis().setEnabled(false);
            horizontalBarChart.getAxisLeft().setEnabled(false);
            horizontalBarChart.getAxisRight().setEnabled(false);

            // Configure Y-axis scale
            YAxis leftAxis = horizontalBarChart.getAxisLeft();
            leftAxis.setAxisMinimum(0f);
            leftAxis.setAxisMaximum(maxScale);
            leftAxis.setDrawLabels(false);
            leftAxis.setDrawAxisLine(false);
            leftAxis.setDrawGridLines(false);
            leftAxis.setDrawZeroLine(true);
            horizontalBarChart.getAxisRight().setEnabled(false);

            // Hide grid lines and description
            horizontalBarChart.setDrawGridBackground(false);
            Description description = new Description();
            description.setEnabled(false);
            horizontalBarChart.setDescription(description);

            // Hide legend
            horizontalBarChart.getLegend().setEnabled(false);

            // Assign the BarData to the chart
            horizontalBarChart.setData(barData);
            horizontalBarChart.invalidate();

            // Set percentage text
            float percentage = (float) numOfVotes * 100 / totalVotes;
            tvPercent.setText(String.format("%.1f%%", percentage));
        }

        @NonNull
        private BarData getBarData(int numOfVotes) {
            ArrayList<BarEntry> barEntries = new ArrayList<>();
            float percentage = (float) numOfVotes * 100 / totalVotes;
            barEntries.add(new BarEntry(0, percentage)); // Masukkan persentase sebagai nilai batang

            BarDataSet barDataSet = new BarDataSet(barEntries, "");
            barDataSet.setColor(Color.parseColor("#8B1E2E"));
            barDataSet.setDrawValues(false); // Hide values on the bars

            List<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet);

            return new BarData(dataSets);
        }
    }

    private class PercentageValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return String.format("%.1f%%", value);
        }
    }
}

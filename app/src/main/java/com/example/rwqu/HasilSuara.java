package com.example.rwqu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HasilSuara extends AppCompatActivity {

    private BarChart barChart;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_suara3);

        barChart = findViewById(R.id.barChart);

        // Inisialisasi Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Transaction").child("votes");

        // Ambil data dari Firebase dan tampilkan pada BarChart
        getDataFromFirebase();
    }

    private void getDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<BarEntry> entries = new ArrayList<>();
                final List<String> labels = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String label = snapshot.getKey();
                    int value = snapshot.getValue(Integer.class);
                    entries.add(new BarEntry(labels.size(), value));
                    labels.add(label);
                }

                BarDataSet dataSet = new BarDataSet(entries, "Votes");
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                dataSet.setValueTextColor(android.R.color.black);

                BarData barData = new BarData(dataSet);
                barChart.setData(barData);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setLabelRotationAngle(45f);
                xAxis.setDrawGridLines(false);

                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error jika dibutuhkan
            }
        });
    }
}
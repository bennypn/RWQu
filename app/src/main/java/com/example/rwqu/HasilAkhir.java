package com.example.rwqu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rwqu.HelperClass.Candidate;
import com.example.rwqu.HelperClass.CandidateAdapter;
import com.example.rwqu.HelperClass.PercentValueFormatter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HasilAkhir extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CandidateAdapter candidateAdapter;
    private List<Candidate> candidateList;
    private Integer numVote1 = 0, numVote2 = 0, numVote3 = 0;
    private Boolean checkData = false;
    protected static Integer voted, notVoted, canVote;
    private TextView memilih, tidakMemilih, berhakMemilih;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_akhir);
        recyclerView = findViewById(R.id.recyclerView4);
        Button btnBack = findViewById(R.id.btn_kembalihasilakhir);
        memilih = findViewById(R.id.txt_memilih);
        tidakMemilih = findViewById(R.id.txt_tidakMemilih);
        berhakMemilih = findViewById(R.id.txt_berhakMemilih);
        pieChart = findViewById(R.id.pieChart);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        candidateList = new ArrayList<>();
        candidateAdapter = new CandidateAdapter(candidateList, 0); // Set initial totalVotes to 0
        recyclerView.setAdapter(candidateAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(HasilAkhir.this,
                        WargaActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        // Read data from Firebase Realtime Database
        DatabaseReference query = FirebaseDatabase.getInstance().getReference().child("Transaction");
        if(!checkData){
            Refresh(query);
            checkData = true;
        }
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                candidateList.clear();
                int totalVotes = 0; // Initialize totalVotes
                for (DataSnapshot snapshot : dataSnapshot.child("candidate").getChildren()) {
                    Candidate candidate = snapshot.getValue(Candidate.class);
                    if (candidate != null) {
                        candidateList.add(candidate);
                        totalVotes += candidate.getNumOfVotes(); // Update totalVotes
                    }
                }
                candidateAdapter = new CandidateAdapter(candidateList, totalVotes); // Update totalVotes in the adapter
                recyclerView.setAdapter(candidateAdapter);
                canVote = dataSnapshot.child("totalWarlok").getValue(Integer.class);
                voted = totalVotes;
                notVoted = canVote - voted;
                berhakMemilih.setText(canVote.toString());
                memilih.setText(voted.toString());
                tidakMemilih.setText(notVoted.toString());
                setData(canVote, voted, notVoted);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error if data retrieval is canceled
            }
        });
    }

    private void Refresh(DatabaseReference query){
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numVote1 = dataSnapshot.child("numOfVotes_1").getValue(Integer.class);
                numVote2 = dataSnapshot.child("numOfVotes_2").getValue(Integer.class);
                numVote3 = dataSnapshot.child("numOfVotes_3").getValue(Integer.class);

                query.child("candidate/candidate_1/numOfVotes").setValue(numVote1);
                query.child("candidate/candidate_2/numOfVotes").setValue(numVote2);
                query.child("candidate/candidate_3/numOfVotes").setValue(numVote3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error if data retrieval is canceled
            }
        });
    }

    private void setData(int value1, int value2, int value3) {
        List<PieEntry> pieEntries = new ArrayList<>();

        // Tambahkan data ke pieEntries
        pieEntries.add(new PieEntry(value1, "Berhak Memilih"));
        pieEntries.add(new PieEntry(value2, "Memilih"));
        pieEntries.add(new PieEntry(value3, "Tidak memilih"));

        int color1 = Color.parseColor("#4198D7");
        int color2 = Color.parseColor("#D8B655");
        int color3 = Color.parseColor("#7A61BA");

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(color1, color2, color3); // Atur warna data
        dataSet.setValueTextColor(Color.BLACK); // Atur warna teks nilai data
        dataSet.setDrawValues(true); // Menampilkan label (nilai persentase)

        PieData data = new PieData(dataSet);
        data.setValueTextSize(12f); // Atur ukuran teks nilai data

        // Atur formatter untuk menampilkan nilai persentase
        PercentFormatter percentFormatter = new PercentFormatter(pieChart);
        data.setValueFormatter(percentFormatter);

        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false); // Menghilangkan description label
        pieChart.getLegend().setEnabled(false); // Menghilangkan indikator warna (legend)
        pieChart.setUsePercentValues(true); // Menggunakan nilai dalam persentase
        pieChart.setCenterText("%"); // Atur teks tengah (opsional)
        pieChart.setCenterTextSize(18f); // Atur ukuran teks tengah (opsional)

        pieChart.invalidate(); // Refresh chart
    }
    class MyValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            // Ubah nilai data menjadi string dan kembalikan sebagai label
            return String.valueOf((int) value); // Jika nilai data berupa int, jika berupa float bisa gunakan String.format("%.1f", value)
        }
    }

}

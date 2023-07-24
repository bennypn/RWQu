package com.example.rwqu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rwqu.HelperClass.Voter;
import com.example.rwqu.HelperClass.VoterAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataPemilih2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VoterAdapter adapter;
    private List<Voter> voterList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pemilih2);

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button saveButton = findViewById(R.id.btn_simpandatapemilih);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(DataPemilih2.this,
                        ScreenAdmin.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        // Data Voter
        voterList = new ArrayList<>();

        // Inisialisasi Firebase Database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Voter");

        // Ambil data dari Firebase
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                voterList.clear();
                for (DataSnapshot voterSnapshot : dataSnapshot.getChildren()) {
                    Voter voter = voterSnapshot.getValue(Voter.class);
                    voterList.add(voter);
                }
                // Perbarui RecyclerView setelah mendapatkan data dari Firebase
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle jika terjadi error saat mengambil data dari Firebase
            }
        });

        // Buat dan atur adapter untuk RecyclerView
        adapter = new VoterAdapter(voterList);
        recyclerView.setAdapter(adapter);

    }
}
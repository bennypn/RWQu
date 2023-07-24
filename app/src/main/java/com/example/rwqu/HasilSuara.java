package com.example.rwqu;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rwqu.HelperClass.Candidate;
import com.example.rwqu.HelperClass.CandidateAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HasilSuara extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CandidateAdapter candidateAdapter;
    private List<Candidate> candidateList;
    private Integer numVote1 = 0, numVote2 = 0, numVote3 = 0;
    private Boolean checkData = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_suara3);

        recyclerView = findViewById(R.id.recyclerView2);
        Button btnBack = findViewById(R.id.btn_kembalihasilpemilihan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        candidateList = new ArrayList<>();
        candidateAdapter = new CandidateAdapter(candidateList, 0); // Set initial totalVotes to 0
        recyclerView.setAdapter(candidateAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(HasilSuara.this,
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
        query.child("candidate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                candidateList.clear();
                int totalVotes = 0; // Initialize totalVotes
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Candidate candidate = snapshot.getValue(Candidate.class);
                    if (candidate != null) {
                        candidateList.add(candidate);
                        totalVotes += candidate.getNumOfVotes(); // Update totalVotes
                    }
                }
                candidateAdapter = new CandidateAdapter(candidateList, totalVotes); // Update totalVotes in the adapter
                recyclerView.setAdapter(candidateAdapter);
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
}

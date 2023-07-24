package com.example.rwqu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WargaActivity extends AppCompatActivity {
    private Boolean closeVote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warga);
        Button btnHasilSuara = findViewById(R.id.btn_hasilsuara);
        Button btnHasilAkhir = findViewById(R.id.btn_hasilakhirhalwarga);

        DatabaseReference query = FirebaseDatabase.getInstance().getReference().child("Transaction");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                closeVote = snapshot.child("closeVote").getValue(Boolean.class);
                btnHasilSuara.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        if (closeVote){
                            intent = new Intent(WargaActivity.this,
                                    HasilSuara.class);
                            startActivity(intent);
                        } else {
                            intent = new Intent(WargaActivity.this,
                                    SuaraSementara.class);
                            startActivity(intent);
                        }
                        overridePendingTransition(0, 0);
                    }
                });
                btnHasilAkhir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        if(closeVote){
                            intent = new Intent(WargaActivity.this,
                                    HasilAkhir.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }else{
                            Toast.makeText(getApplicationContext(),"Perhitungan Suara Belum Berakhir!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
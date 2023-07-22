package com.example.rwqu;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScanKtp extends AppCompatActivity {

    private EditText txtNama, txtNik, txtKtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_ktp);

        Button simpan = findViewById(R.id.btn_simpanscanktp);
        txtNama = findViewById(R.id.txt_inputnama);
        txtNik = findViewById(R.id.txt_inputnik);
        txtKtp = findViewById(R.id.txt_scanktp);

        DatabaseReference ktpRef = FirebaseDatabase.getInstance().getReference("Registration/voterUID");
        // Read from the database
        ktpRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                txtKtp.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(ScanKtp.this,
                        ScreenAdmin.class);
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Voter");
                String nama, ktp, nik;
                nama = txtNama.getEditableText().toString();
                ktp = txtKtp.getEditableText().toString();
                nik = txtNik.getEditableText().toString();

                if (!nama.equalsIgnoreCase("") || !ktp.equalsIgnoreCase("") || !nik.equalsIgnoreCase("")){
                    myRef.child(ktp).child("voterNIK").setValue(nik);
                    myRef.child(ktp).child("voterName").setValue(nama.toUpperCase());
                    myRef.child(ktp).child("voterUID").setValue(ktp);
                    Toast.makeText(getApplicationContext(),"Data berhasil disimpan",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    Toast.makeText(getApplicationContext(),"Mohon lengkapi data!",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
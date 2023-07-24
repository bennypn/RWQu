package com.example.rwqu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScreenAdmin extends AppCompatActivity {
    private boolean switchState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_admin);

        Button btnDataAdmin = findViewById(R.id.btn_pagedataadmin);
        Button btnDataPaslon = findViewById(R.id.btn_pagedatapaslon);
        Button btnDataPemilih = findViewById(R.id.btn_pagedatapemilih);
        Button btnRegis = findViewById(R.id.btn_pageregispemilih);
        Switch mySwitch = findViewById(R.id.switch1);

        DatabaseReference query = FirebaseDatabase.getInstance().getReference().child("Transaction");
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchState = isChecked;
                // Lakukan aksi berdasarkan nilai isChecked di sini
                if (isChecked) {
                    query.child("closeVote").setValue(true);
                } else {
                    query.child("closeVote").setValue(false);
                }
            }
        });
        btnDataAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(ScreenAdmin.this,
                        DataAdmin2.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        btnDataPaslon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(ScreenAdmin.this,
                        DataPaslon2.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        btnDataPemilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(ScreenAdmin.this,
                        DataPemilih2.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(ScreenAdmin.this,
                        ScanKtp.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}
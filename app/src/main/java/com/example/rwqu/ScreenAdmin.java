package com.example.rwqu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScreenAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_admin);

        Button btnDataAdmin = findViewById(R.id.btn_pagedataadmin);
        Button btnDataPaslon = findViewById(R.id.btn_pagedatapaslon);
        Button btnDataPemilih = findViewById(R.id.btn_pagedatapemilih);
        Button btnRegis = findViewById(R.id.btn_pageregispemilih);

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
package com.example.rwqu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WargaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warga);
        Button btnHasilSuara = findViewById(R.id.btn_hasilsuara);
        Button btnHasilAkhir = findViewById(R.id.btn_hasilakhirhalwarga);
        btnHasilSuara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(WargaActivity.this,
                        HasilSuara.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        btnHasilAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(WargaActivity.this,
                        HasilAkhir.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}
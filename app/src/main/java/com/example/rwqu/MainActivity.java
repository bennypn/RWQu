package com.example.rwqu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
//TEST 123
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdminPage = findViewById(R.id.btn_adminpage);
        Button btnLoginPage = findViewById(R.id.btn_wargapage);

        btnAdminPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this,
                        LoginAdmin2.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        btnLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this,
                        WargaActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}
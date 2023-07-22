package com.example.rwqu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DataPaslon2 extends AppCompatActivity {

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_paslon2);
        saveButton = findViewById(R.id.btn_simpandatapaslon);
        ImageView img = findViewById(R.id.imv_datapaslon);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(DataPaslon2.this,
                        ScreenAdmin.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}
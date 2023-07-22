package com.example.rwqu;

import static com.example.rwqu.SplashScreen.email;
import static com.example.rwqu.SplashScreen.password;
import static com.example.rwqu.SplashScreen.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DataAdmin2 extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button registerButton;
    private EditText usernameTextView, passwordTextView;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_admin2);

        progressBar = findViewById(R.id.progressBar2);
        registerButton = findViewById(R.id.btn_simpandataadmin);
        usernameTextView = findViewById(R.id.txt_iddataadmin);
        passwordTextView = findViewById(R.id.txt_psdataadmin);
        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

    }

    private void registerNewUser()
    {
        // Take the value of two edit texts in Strings
        user = usernameTextView.getText().toString();
        email = user + "@gmail.com";
        password = passwordTextView.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(getApplicationContext(),"Please enter username!!",Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),"Please enter password!!",Toast.LENGTH_LONG).show();
            return;
        }
        // show the visibility of progress bar to show loading
        progressBar.setVisibility(View.VISIBLE);

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Data Berhasil disimpan",Toast.LENGTH_LONG).show();
                            // hide the progress bar
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(DataAdmin2.this,
                                    ScreenAdmin.class);
                            startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(getApplicationContext(),"Data Gagal disimpan!!" + " Silahkan Coba lagi",Toast.LENGTH_LONG).show();
                            // hide the progress bar
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
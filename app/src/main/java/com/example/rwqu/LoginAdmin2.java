package com.example.rwqu;

import static com.example.rwqu.SplashScreen.email;
import static com.example.rwqu.SplashScreen.password;
import static com.example.rwqu.SplashScreen.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAdmin2 extends AppCompatActivity {
    private TextView registxt;
    private EditText usernameTextView, passwordTextView;
    private Button Btn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin2);

        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        usernameTextView = findViewById(R.id.txt_idloginadmin);
        passwordTextView = findViewById(R.id.txt_psloginadmin);
        Btn = findViewById(R.id.btn_masukloginadmin);
        progressBar = findViewById(R.id.progressBar);

        // Set on Click Listener on Sign-in button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Write a message to the database
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount()
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

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"Login successful!!",Toast.LENGTH_LONG).show();
                                    // hide the progress bar
                                    progressBar.setVisibility(View.GONE);

                                    // if sign-in is successful
                                    // intent to home activity
                                    Intent intent;
                                    if(user.equals("admin")){
                                        intent = new Intent(LoginAdmin2.this,
                                                ScreenAdmin.class);
                                        startActivity(intent);

                                    }else{
                                        Toast.makeText(getApplicationContext(),"Login failed!!",Toast.LENGTH_LONG).show();

                                        // hide the progress bar
                                        progressBar.setVisibility(View.GONE);
                                    }


                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),"Login failed!!",Toast.LENGTH_LONG).show();

                                    // hide the progress bar
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
    }

}
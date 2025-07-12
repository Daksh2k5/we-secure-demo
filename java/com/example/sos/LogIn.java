package com.example.sos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etConfirmPassword); // Check if this ID matches your XML
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
    }

    private void setupClickListeners() {
        // Login button - just go to home screen
        btnLogin.setOnClickListener(v -> {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LogIn.this, HomeScreen.class);
            startActivity(intent);
        });

        // Forgot password - just go to forgot password screen
        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LogIn.this, ForgotPassword.class);
            startActivity(intent);
        });

        // Sign up - just go to sign up screen
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LogIn.this, SignUp.class);
            startActivity(intent);
        });
    }

    /*
    // All validation and authentication logic commented out for demo
    private boolean validateInputs() {
        // Email and password validation would go here
        return true;
    }

    private void performAuthentication() {
        // API calls, database checks, etc. would go here
    }
    */
}
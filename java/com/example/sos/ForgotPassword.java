package com.example.sos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {

    private ImageView ivBack;
    private EditText etEmail;
    private Button btnSendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        etEmail = findViewById(R.id.etEmail);
        btnSendCode = findViewById(R.id.btnSendCode);
    }

    private void setupClickListeners() {
        // Back button
        ivBack.setOnClickListener(v -> finish());

        // Send code button - just show message and go back to login
        btnSendCode.setOnClickListener(v -> {
            Toast.makeText(this, "Reset code sent!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ForgotPassword.this, Verification.class);
            startActivity(intent);
            finish();
        });
    }

    /*
    // All email validation and reset code functionality commented out for demo
    private boolean validateEmail() {
        // Email validation would go here
        return true;
    }

    private void sendResetCode() {
        // API call to send reset code would go here
    }
    */
}
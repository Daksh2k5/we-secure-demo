package com.example.sos;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sos.HomeScreen;
import com.example.sos.LogIn;
import com.example.sos.R;

public class Verification extends AppCompatActivity {

    private ImageView ivBack;
    private EditText etCode1, etCode2, etCode3, etCode4;
    private Button btnVerify;
    private String email;
    private boolean isPasswordReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        getIntentData();
        initViews();
        setupClickListeners();
        setupOTPInput();
    }

    private void getIntentData() {
        email = getIntent().getStringExtra("email");
        isPasswordReset = getIntent().getBooleanExtra("isPasswordReset", false);
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        etCode1 = findViewById(R.id.etCode1);
        etCode2 = findViewById(R.id.etCode2);
        etCode3 = findViewById(R.id.etCode3);
        etCode4 = findViewById(R.id.etCode4);
        btnVerify = findViewById(R.id.btnVerify);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(v -> onBackPressed());

        btnVerify.setOnClickListener(v -> {
            // Skip validation for now - directly go to login page
            performVerification();

            /* COMMENTED OUT VALIDATION
            if (validateOTP()) {
                performVerification();
            }
            */
        });
    }

    private void setupOTPInput() {
        // Auto-move to next EditText when user enters a digit
        etCode1.addTextChangedListener(new OTPTextWatcher(etCode1, etCode2));
        etCode2.addTextChangedListener(new OTPTextWatcher(etCode2, etCode3));
        etCode3.addTextChangedListener(new OTPTextWatcher(etCode3, etCode4));
        etCode4.addTextChangedListener(new OTPTextWatcher(etCode4, null));
    }

    /* COMMENTED OUT VALIDATION METHOD
    private boolean validateOTP() {
        String code1 = etCode1.getText().toString().trim();
        String code2 = etCode2.getText().toString().trim();
        String code3 = etCode3.getText().toString().trim();
        String code4 = etCode4.getText().toString().trim();

        if (code1.isEmpty() || code2.isEmpty() || code3.isEmpty() || code4.isEmpty()) {
            Toast.makeText(this, "Please enter complete verification code", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    */

    private void performVerification() {
        // Skip OTP validation - directly navigate to login page
        Toast.makeText(this, "Verification successful", Toast.LENGTH_SHORT).show();

        // Always navigate to login page regardless of the flow
        Intent intent = new Intent(Verification.this, newPassword.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

        /* COMMENTED OUT - Original logic with different navigation paths
        String otp = etCode1.getText().toString() + etCode2.getText().toString() +
                etCode3.getText().toString() + etCode4.getText().toString();

        // TODO: Implement your OTP verification logic here
        // For now, just show success and navigate accordingly

        if (isPasswordReset) {
            // Navigate back to login or to reset password screen
            Intent intent = new Intent(Verification.this, LogIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            // Navigate to main app after successful registration verification
            Intent intent = new Intent(Verification.this, HomeScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    // Helper class for OTP input handling
    private class OTPTextWatcher implements TextWatcher {
        private EditText currentEditText;
        private EditText nextEditText;

        public OTPTextWatcher(EditText currentEditText, EditText nextEditText) {
            this.currentEditText = currentEditText;
            this.nextEditText = nextEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 1 && nextEditText != null) {
                nextEditText.requestFocus();
            } else if (s.length() == 0 && currentEditText != etCode1) {
                // Move back to previous EditText when backspace is pressed
                moveToPreviousEditText();
            }
        }

        private void moveToPreviousEditText() {
            if (currentEditText == etCode2) {
                etCode1.requestFocus();
            } else if (currentEditText == etCode3) {
                etCode2.requestFocus();
            } else if (currentEditText == etCode4) {
                etCode3.requestFocus();
            }
        }
    }
}
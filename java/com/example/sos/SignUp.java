package com.example.sos;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private ImageView ivBack;
    private EditText etName, etMobile, etEmail, etPassword, etConfirmPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(v -> onBackPressed());

        btnSignUp.setOnClickListener(v -> {
            // Skip validation for now - directly go to verification page
            performSignUp();

            /* COMMENTED OUT VALIDATION
            if (validateInputs()) {
                performSignUp();
            }
            */
        });
    }

    /* COMMENTED OUT VALIDATION METHOD
    private boolean validateInputs() {
        String name = etName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Name is required");
            etName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(mobile)) {
            etMobile.setError("Mobile number is required");
            etMobile.requestFocus();
            return false;
        }

        if (mobile.length() < 10) {
            etMobile.setError("Please enter a valid mobile number");
            etMobile.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError("Please confirm your password");
            etConfirmPassword.requestFocus();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
    */

    private void performSignUp() {
        // Skip validation - directly navigate to verification page
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(SignUp.this, HomeScreen.class);
        // Pass a default email for now since we're skipping validation
        intent.putExtra("email", "default@example.com");
        startActivity(intent);

        /* COMMENTED OUT - Original validation logic
        String name = etName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // TODO: Implement your sign up logic here
        // For now, just show a success message and navigate to verification

        intent.putExtra("email", email);
        */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
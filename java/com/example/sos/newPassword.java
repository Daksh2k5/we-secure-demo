package com.example.sos;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sos.R;

public class newPassword extends AppCompatActivity {

    private ImageView ivBack;
    private EditText etNewPassword;
    private EditText etConfirmPassword;
    private ImageView ivToggleNewPassword;
    private ImageView ivToggleConfirmPassword;
    private Button btnResetPassword;

    private boolean isNewPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        ivToggleNewPassword = findViewById(R.id.ivToggleNewPassword);
        ivToggleConfirmPassword = findViewById(R.id.ivToggleConfirmPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(v -> onBackPressed());

        // Password visibility toggles - commented out for now
        /*
        ivToggleNewPassword.setOnClickListener(v -> toggleNewPasswordVisibility());
        ivToggleConfirmPassword.setOnClickListener(v -> toggleConfirmPasswordVisibility());
        */

        btnResetPassword.setOnClickListener(v -> {
            // Commented out validation and reset logic
            // if (validatePasswords()) {
            //     resetPassword();
            // }

            // Simply go to login page
            goToLoginPage();
        });
    }

    /*
    private void toggleNewPasswordVisibility() {
        if (isNewPasswordVisible) {
            etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivToggleNewPassword.setImageResource(R.drawable.ic_eye_off);
        } else {
            etNewPassword.setTransformationMethod(null);
            ivToggleNewPassword.setImageResource(R.drawable.ic_eye_on);
        }
        isNewPasswordVisible = !isNewPasswordVisible;
        etNewPassword.setSelection(etNewPassword.getText().length());
    }

    private void toggleConfirmPasswordVisibility() {
        if (isConfirmPasswordVisible) {
            etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivToggleConfirmPassword.setImageResource(R.drawable.ic_eye_off);
        } else {
            etConfirmPassword.setTransformationMethod(null);
            ivToggleConfirmPassword.setImageResource(R.drawable.ic_eye_on);
        }
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
        etConfirmPassword.setSelection(etConfirmPassword.getText().length());
    }
    */

    /*
    private boolean validatePasswords() {
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError("New password is required");
            etNewPassword.requestFocus();
            return false;
        }

        if (newPassword.length() < 6) {
            etNewPassword.setError("Password must be at least 6 characters");
            etNewPassword.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError("Please confirm your password");
            etConfirmPassword.requestFocus();
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
    */

    /*
    private void resetPassword() {
        String newPassword = etNewPassword.getText().toString().trim();

        // TODO: Implement your password reset logic here
        // This would typically involve:
        // 1. Sending the new password to your backend
        // 2. Updating the user's password in the database
        // 3. Showing success message
        // 4. Navigating to login

        Toast.makeText(this, "Password reset successfully", Toast.LENGTH_SHORT).show();
        goToLoginPage();
    }
    */

    private void goToLoginPage() {
        // Show a simple message
        Toast.makeText(this, "Password reset completed", Toast.LENGTH_SHORT).show();

        // Navigate to login page - replace "LoginActivity" with your actual login activity class name
        Intent intent = new Intent(newPassword.this, LogIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
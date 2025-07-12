package com.example.sos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sos.R;

public class Settings extends AppCompatActivity {

    private ImageView ivBack;
    private ImageView ivProfilePicture;
    private TextView tvUserName;
    private Button btnPersonalDetails;
    private Button btnEmergencyContacts;
    private Button btnSOSSettings;
    private Button btnFakeCallOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        setupClickListeners();
        loadUserData();
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        tvUserName = findViewById(R.id.tvUserName);
        btnPersonalDetails = findViewById(R.id.btnPersonalDetails);
        btnEmergencyContacts = findViewById(R.id.btnEmergencyContacts);
        btnSOSSettings = findViewById(R.id.btnSOSSettings);
        btnFakeCallOptions = findViewById(R.id.btnFakeCallOptions);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(v -> onBackPressed());

        // Profile picture click - commented out for now
        /*
        ivProfilePicture.setOnClickListener(v -> {
            // TODO: Implement profile picture change functionality
            // This could open gallery/camera to change profile picture
            Toast.makeText(this, "Change profile picture", Toast.LENGTH_SHORT).show();
        });
        */

        btnPersonalDetails.setOnClickListener(v -> {
            // Commented out navigation to personal details
            // Intent intent = new Intent(Settings.this, PersonalDetailsActivity.class);
            // startActivity(intent);

            Toast.makeText(this, "Personal Details clicked", Toast.LENGTH_SHORT).show();
        });

        btnEmergencyContacts.setOnClickListener(v -> {
            // Commented out navigation to emergency contacts
            // Intent intent = new Intent(Settings.this, EmergencyContactsActivity.class);
            // startActivity(intent);

            Toast.makeText(this, "Emergency Contacts clicked", Toast.LENGTH_SHORT).show();
        });

        btnSOSSettings.setOnClickListener(v -> {
            // Commented out navigation to SOS settings
            // Intent intent = new Intent(Settings.this, SOSSettingsActivity.class);
            // startActivity(intent);

            Toast.makeText(this, "SOS Settings clicked", Toast.LENGTH_SHORT).show();
        });

        btnFakeCallOptions.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.this, FakeCallEdit.class);
            startActivity(intent);

            Toast.makeText(this, "Fake Call Options clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadUserData() {
        // Commented out user data loading functionality
        /*
        // TODO: Load user data from preferences, database, or API
        // This would typically involve:
        // 1. Getting user data from SharedPreferences or database
        // 2. Setting the user name
        // 3. Loading and setting profile picture

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userName = prefs.getString("user_name", "USER NAME");
        tvUserName.setText(userName);

        // Load profile picture if available
        String profilePicturePath = prefs.getString("profile_picture_path", "");
        if (!profilePicturePath.isEmpty()) {
            // Load and set profile picture
            loadProfilePicture(profilePicturePath);
        }
        */

        // For now, just set default values
        tvUserName.setText("USER NAME");
    }

    /*
    private void loadProfilePicture(String imagePath) {
        // TODO: Implement profile picture loading
        // This could use libraries like Glide or Picasso
        // Example with Glide:
        // Glide.with(this)
        //     .load(imagePath)
        //     .placeholder(R.drawable.ic_profile_placeholder)
        //     .error(R.drawable.ic_profile_placeholder)
        //     .circleCrop()
        //     .into(ivProfilePicture);
    }
    */

    /*
    private void updateUserName(String newUserName) {
        // TODO: Update user name in preferences/database
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_name", newUserName);
        editor.apply();

        tvUserName.setText(newUserName);
    }
    */

    /*
    private void logout() {
        // TODO: Implement logout functionality
        // This would typically involve:
        // 1. Clearing user session data
        // 2. Clearing SharedPreferences
        // 3. Navigating back to login screen

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(Settings.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh user data when returning to this activity
        // loadUserData();
    }
}
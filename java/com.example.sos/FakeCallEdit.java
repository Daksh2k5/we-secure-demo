package com.example.sos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class FakeCallEdit extends AppCompatActivity {

    public static Class<?> java;
    private EditText etName, etMobileNo;
    private ImageView ivProfilePicture;
    private Spinner spinnerOptions;
    private Button btnSelectImage, btnSave;

    private SharedPreferences sharedPreferences;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call_edit);

        // Initialize views
        etName = findViewById(R.id.etName);
        etMobileNo = findViewById(R.id.etMobileNo);
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        spinnerOptions = findViewById(R.id.spinnerOptions);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSave = findViewById(R.id.btnSave);

        sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);

        // Setup spinner
        String[] options = {"com.example.sos.FakeCallS", "com.example.sos.FakeCallV",
                "com.example.sos.FakeCallR", "com.example.sos.FakeCallO",
                "com.example.sos.FakeCallX"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions.setAdapter(adapter);

        // Setup image picker
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        android.net.Uri uri = result.getData().getData();
                        if (uri != null) {
                            ivProfilePicture.setImageURI(uri);
                            sharedPreferences.edit().putString("imageUri", uri.toString()).apply();
                        }
                    }
                }
        );

        // Load saved data
        loadSettings();

        // Button clicks
        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });
        btnSave.setOnClickListener(v -> saveSettings());
    }

    private void loadSettings() {
        etName.setText(sharedPreferences.getString("name", "Prince Negi"));
        etMobileNo.setText(sharedPreferences.getString("mobile", "9560696139"));

        String imageUri = sharedPreferences.getString("imageUri", "");
        if (!imageUri.isEmpty()) {
            ivProfilePicture.setImageURI(android.net.Uri.parse(imageUri));
        }

        int optionIndex = sharedPreferences.getInt("optionIndex", 0);
        spinnerOptions.setSelection(optionIndex);
    }

    private void saveSettings() {
        String name = etName.getText().toString();
        String mobile = etMobileNo.getText().toString();
        int selectedIndex = spinnerOptions.getSelectedItemPosition();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("mobile", mobile);
        editor.putInt("optionIndex", selectedIndex);
        editor.apply();

        Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show();
        finish();
    }
    public static String getSavedName(android.content.Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", MODE_PRIVATE);
        return prefs.getString("name", "Prince Negi");
    }

    public static String getSavedMobile(android.content.Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", MODE_PRIVATE);
        return prefs.getString("mobile", "+9560696139");
    }

    public static String getSavedImageUri(android.content.Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", MODE_PRIVATE);
        return prefs.getString("imageUri", "");
    }

    public static String getSavedOption(android.content.Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", MODE_PRIVATE);
        int index = prefs.getInt("optionIndex", 0);

        // Ensure these class names are accurate
        String[] options = {"com.example.sos.FakeCallS", "com.example.sos.FakeCallV",
                "com.example.sos.FakeCallR", "com.example.sos.FakeCallO",
                "com.example.sos.FakeCallX"};
        if (index < 0 || index >= options.length) {
            return options[0]; // Default to first option if index is out of bounds
        }
        return options[index];
    }

}
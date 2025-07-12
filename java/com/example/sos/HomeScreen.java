package com.example.sos;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeScreen extends AppCompatActivity {
    private MediaPlayer mediaPlayer = null;
    private boolean isSoundPlaying = false;
    private static final int CALL_PERMISSION_REQUEST = 100;

    private Button sosButton;
    private Button stopButton;
    String selectedOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_home_screen);

        // Apply insets for better UI adjustments
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get saved option from FakeCallEdit
        selectedOption = FakeCallEdit.getSavedOption(this);

        // Initialize buttons
        sosButton = findViewById(R.id.btnSOS);
        stopButton = findViewById(R.id.btnStop);

        // Set click listener for SOS button
        sosButton.setOnClickListener(v -> {
            vibratePhone();

            // Example of playing sound
            if (!isSoundPlaying) {
                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);

                mediaPlayer = MediaPlayer.create(HomeScreen.this, R.raw.alarm);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                isSoundPlaying = true;
            }

            // Example of making a call
            SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
            String callNumber = prefs.getString("realCall", "6263184001");

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + callNumber));

            if (ContextCompat.checkSelfPermission(HomeScreen.this, Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                startActivity(callIntent);
            } else {
                ActivityCompat.requestPermissions(
                        HomeScreen.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        CALL_PERMISSION_REQUEST
                );
                Toast.makeText(HomeScreen.this, "Please grant CALL_PHONE permission", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for STOP button - THIS WAS MISSING!
        stopButton.setOnClickListener(v -> {
            // Stop the alarm sound
            if (mediaPlayer != null && isSoundPlaying) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                isSoundPlaying = false;
                Toast.makeText(HomeScreen.this, "Alarm stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void vibratePhone() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(500);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted. Press SOS again.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "CALL_PHONE permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void OpenCall(View view) {
        String selectedOption = FakeCallEdit.getSavedOption(this);

        try {
            Intent intent = new Intent(this, Class.forName(selectedOption));
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: Class not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void OpenSettings(View view){
        Intent intent = new Intent(this,Settings.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up MediaPlayer when activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            isSoundPlaying = false;
        }
    }
}
package com.example.sos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import de.hdodenhof.circleimageview.CircleImageView;

public class FakeCallS extends AppCompatActivity {

    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fake_call_s);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView callername = findViewById(R.id.callername);
        String name = FakeCallEdit.getSavedName(this);
        callername.setText(name);

        TextView phoneno = findViewById(R.id.mobileno);
        String mobile = FakeCallEdit.getSavedMobile(this);
        phoneno.setText("Mobile +91"+mobile);

        CircleImageView profileImage = findViewById(R.id.profile_image);
        String imageUri = FakeCallEdit.getSavedImageUri(this);
        if (!imageUri.isEmpty()) {
            profileImage.setImageURI(android.net.Uri.parse(imageUri));
        }
        // Start vibration and ringtone when activity opens
        startVibrationAndRingtone();
    }

    private void startVibrationAndRingtone() {
        // Start vibration
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            // Vibrate in a pattern: wait 0ms, vibrate 1000ms, wait 1000ms, repeat
            long[] pattern = {0, 1000, 1000};
            vibrator.vibrate(pattern, 0); // 0 means repeat indefinitely
        }

        // Start ringtone
        try {
            Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(this, ringtoneUri);
            mediaPlayer.setLooping(true); // Loop the ringtone
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopVibrationAndRingtone() {
        // Stop vibration
        if (vibrator != null) {
            vibrator.cancel();
        }

        // Stop ringtone
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void OpenMainActivity(View view) {
        stopVibrationAndRingtone();
        finish();
    }

    public void OpenCallSend(View view) {
        stopVibrationAndRingtone();
        Intent intent = new Intent(this, FakeCallSend.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopVibrationAndRingtone();
    }
}
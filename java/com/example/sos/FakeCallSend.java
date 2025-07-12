package com.example.sos;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import de.hdodenhof.circleimageview.CircleImageView;

public class FakeCallSend extends AppCompatActivity {

    private TextView timerTextView;
    private Handler handler;
    private Runnable timerRunnable;
    private long startTime;
    private boolean timerRunning = false;

    // Proximity sensor variables
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fake_call_send);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView callername = findViewById(R.id.callernameend);
        String name = FakeCallEdit.getSavedName(this);
        callername.setText(name);

        TextView phoneno = findViewById(R.id.mobilenoend);
        String mobile = FakeCallEdit.getSavedMobile(this);
        phoneno.setText("Other +91"+mobile);
        CircleImageView profileImage = findViewById(R.id.profile_image);
        String imageUri = FakeCallEdit.getSavedImageUri(this);
        if (!imageUri.isEmpty()) {
            profileImage.setImageURI(android.net.Uri.parse(imageUri));
        }
        // Find the timer TextView
        timerTextView = findViewById(R.id.timerTextView);

        // Initialize handler
        handler = new Handler();

        // Initialize proximity sensor
        setupProximitySensor();

        // Start timer immediately when activity opens
        startTimer();
    }

    private void setupProximitySensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);

        // Create a wake lock that turns off the screen when proximity sensor is covered
        wakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "FakeCall:ProximityWakeLock");
    }

    private SensorEventListener proximityListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] < proximitySensor.getMaximumRange()) {
                    // Phone is near ear - turn off screen
                    if (!wakeLock.isHeld()) {
                        wakeLock.acquire();
                    }
                } else {
                    // Phone is away from ear - turn on screen
                    if (wakeLock.isHeld()) {
                        wakeLock.release();
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Not needed
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        // Register proximity sensor listener
        if (proximitySensor != null) {
            sensorManager.registerListener(proximityListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister proximity sensor listener
        sensorManager.unregisterListener(proximityListener);
        // Release wake lock if held
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        timerRunning = true;

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (timerRunning) {
                    long elapsed = System.currentTimeMillis() - startTime;
                    updateTimer(elapsed);
                    handler.postDelayed(this, 1000); // Update every second
                }
            }
        };

        handler.post(timerRunnable);
    }

    private void updateTimer(long elapsedTime) {
        int seconds = (int) (elapsedTime / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        String time = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(time);
    }

    public void OpenMainActivity(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop timer when activity is destroyed
        timerRunning = false;
        if (handler != null && timerRunnable != null) {
            handler.removeCallbacks(timerRunnable);
        }

        // Clean up proximity sensor
        sensorManager.unregisterListener(proximityListener);
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
    }
}
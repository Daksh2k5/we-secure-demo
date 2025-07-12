package com.example.sos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sos.LogIn;
import com.example.sos.R;

public class MainActivity extends AppCompatActivity {

    private Button btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        btnGetStarted = findViewById(R.id.btnGetStarted);
    }

    private void setupClickListeners() {
        btnGetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LogIn.class);
            startActivity(intent);
        });
    }
}

package com.example.sos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    String selectedOption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String selectedOption = FakeCallEdit.getSavedOption(this);
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

    public void OpenEdit(View view){
        Intent intent = new Intent(this,FakeCallEdit.class);
        startActivity(intent);
    }
}
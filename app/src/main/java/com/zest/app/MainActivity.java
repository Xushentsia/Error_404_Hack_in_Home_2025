package com.zest.app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(this, StoreActivity.class);
            startActivity(intent);
            // finish(); // убрать, если хочешь вернуться назад
        });
    }
}
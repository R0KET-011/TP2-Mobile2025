package com.example.teamwork.Activity.Team;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.teamwork.R;

public class TeamCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_create);

        findViewById(R.id.back).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.create).setOnClickListener(v -> {
            finish();
        });
    }
}
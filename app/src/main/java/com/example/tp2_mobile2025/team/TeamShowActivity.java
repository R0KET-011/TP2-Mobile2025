package com.example.tp2_mobile2025.team;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tp2_mobile2025.MainActivity;
import com.example.tp2_mobile2025.R;

public class TeamShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_show);

        findViewById(R.id.back).setOnClickListener(v -> {
            finish();
        });

        setInfos(this.getIntent());
        setButtons();
    }

    private void setInfos(Intent intent){
        Team team = intent.getParcelableExtra("team");

        TextView name = findViewById(R.id.name);
        TextView state = findViewById(R.id.state);
        TextView size = findViewById(R.id.size);
        TextView description = findViewById(R.id.description);

        assert team != null;
        name.setText(team.getName());
        state.setText(team.getState());
        state.setTextColor(team.getStateColor(this));
        size.setText(String.valueOf(team.getStudentsCount()));
        description.setText(team.getDescription());
    }
    private void setButtons(){
        findViewById(R.id.join).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
        });
    }
}
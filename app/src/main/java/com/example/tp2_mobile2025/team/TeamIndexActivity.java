package com.example.tp2_mobile2025.team;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp2_mobile2025.R;

import java.util.ArrayList;
import java.util.List;

public class TeamIndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_index);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        List<Team> teams = new ArrayList<>();
        String[] states = getResources().getStringArray(R.array.states);

        teams.add(new Team("TeamWork", states[0], "Description de fou qui est genre super representative de ce quil vas y avoir. Mais pour vrais je pense ya pas mieux que sa", List.of(new Student[]{
                new Student("time"),
                new Student("marcel"),
                new Student("marcel")
        })));

        teams.add(new Team("WorkLoad", states[1], "Description de fou qui est genre super representative de ce quil vas y avoir. Mais pour vrais je pense ya pas mieux que sa",  List.of(new Student[]{
                new Student("marcel"),
                new Student("marcel")
        })));
        teams.add(new Team("Make it real", states[2], "Description de fou qui est genre super representative de ce quil vas y avoir. Mais pour vrais je pense ya pas mieux que sa", List.of(new Student[]{
                new Student("marcel"),
                new Student("marcel")
        })));

        TeamAdapter adapter = new TeamAdapter(this, teams);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.back).setOnClickListener(v -> {
            finish();
        });
    }
}
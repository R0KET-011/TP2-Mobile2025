package com.example.teamwork.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Activity.Team.TeamIndexActivity;
import com.example.teamwork.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, TeamIndexActivity.class);
        intent.putExtra("projectId", 1);
        startActivity(intent);

        /*Intent intent = new Intent(this, CommentPopupActivity.class);
        intent.putExtra("teamId", 3);
        startActivity(intent);*/


    }
}
package com.example.teamwork.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Activity.Project.ProjectActivity;
import com.example.teamwork.Activity.Students.StudentListActivity;
import com.example.teamwork.Activity.Team.TeamIndexActivity;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.DatabaseSeeder;
import com.example.teamwork.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Authentication.setId(1);
        Authentication.setIsStudent(true);

        Intent intent = new Intent(this, ProjectActivity.class);
        startActivity(intent);

    }
}
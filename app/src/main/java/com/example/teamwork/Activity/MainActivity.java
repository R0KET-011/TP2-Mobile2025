package com.example.teamwork.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Activity.Auth.LoginActivity;
import com.example.teamwork.Activity.Auth.ProfileActivity;
import com.example.teamwork.Activity.Project.ProjectActivity;
import com.example.teamwork.Activity.ToDo.TodoIndexActivity;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Authentication.setId(2);
        Authentication.setIsStudent(false);
        Authentication.setCode(206242440);
        Authentication.setEmail("206242440@cegepsherbrooke.qc.ca");
        Authentication.setName("Antoine Blouin");

        Intent intent = new Intent(this, ProjectActivity.class);
        startActivity(intent);
    }
}
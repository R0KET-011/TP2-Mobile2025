package com.example.teamwork.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Activity.Auth.LoginActivity;
import com.example.teamwork.Activity.Auth.ProfileActivity;
import com.example.teamwork.Activity.Project.ProjectActivity;
import com.example.teamwork.Activity.ToDo.TodoIndexActivity;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Dao.UserDao;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.Database.Tables.User;
import com.example.teamwork.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(this::loadDbUser);
    }

    private void loadDbUser(){
        AppDatabase db = AppDatabase.getDatabase(this);
        User user = db.userDao().getUser();

        if(user != null){
            String email = (user.getEmail());
            int code = Integer.parseInt(email.substring(0, 9));
            Authentication.setCode(code);
            Authentication.setId(user.getId());
            Authentication.setEmail(user.getEmail());
            Authentication.setName(user.getFirst_name() + " " + user.getLast_name());
            Authentication.setIsStudent(user.isStudent());
            Intent intent = new Intent(this, ProjectActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
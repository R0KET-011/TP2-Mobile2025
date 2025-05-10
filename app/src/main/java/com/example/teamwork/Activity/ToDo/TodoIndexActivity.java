package com.example.teamwork.Activity.ToDo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Todo;
import com.example.teamwork.R;

import java.util.List;

public class TodoIndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_index);

        Intent intent = getIntent();
        int projectId = intent.getIntExtra("projectId", -1);
        projectId = 1;
        AppDatabase db = AppDatabase.getDatabase(this);
        db.todoDao().getTodosByProjectId(projectId).observe(
                this, (List<Todo> todos) -> {
                    try {
                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        TodoAdapter todoAdapter = new TodoAdapter(this, todos);
                        recyclerView.setAdapter(todoAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    } catch (Exception e) {
                        Log.d("RecycleView ERROR", "onCreate() returned: " + e);
                    }
                });
    }
}
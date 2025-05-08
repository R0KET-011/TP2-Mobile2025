package com.example.teamwork.Activity.Students;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Student;
import com.example.teamwork.R;

import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_list);
        // check for the team id
        Intent intent = getIntent();
        int teamId = intent.getIntExtra("teamId",-1);

        //si team est -1 (default) finish
        if (teamId == -1) {
            Toast.makeText(this, "Team id is at -1", Toast.LENGTH_SHORT).show();
        }

        //get students de la database
        AppDatabase appDatabase = AppDatabase.getDatabase(this);
        appDatabase.teamStudentDao().getStudentsForTeam(teamId).observe(this, (List<Student> students) -> {
            StudentAdapter adapter = new StudentAdapter(this, students);
            RecyclerView recyclerView = findViewById(R.id.rv_students);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });



        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
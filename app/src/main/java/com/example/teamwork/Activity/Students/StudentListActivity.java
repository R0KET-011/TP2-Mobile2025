package com.example.teamwork.Activity.Students;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
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

public class StudentListActivity extends AppCompatActivity implements View.OnClickListener {

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
            StudentAdapter adapter = new StudentAdapter(this, students, teamId);
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

    @Override
    public void onClick(View v) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.activity_comment_popup, null, false),100,100, true);

        pw.showAtLocation(v, Gravity.CENTER, 0, 0);
    }
}
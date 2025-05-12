package com.example.teamwork.Activity.Students;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Student;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.TeamStudent;
import com.example.teamwork.R;

public class StudentAddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText codeEditText;
    private Team team;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);

        db = AppDatabase.getDatabase(this);

        int teamId = getIntent().getIntExtra("teamId", -1);
        team = db.teamDao().getTeamByIdClass(teamId);

        setupButtons();
        codeEditText = findViewById(R.id.code);
    }

    private void setupButtons(){
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back){
            finish();
        } else if (v.getId() == R.id.confirm){
            addStudent();
        }
    }

    private void addStudent() {
        String codeText = codeEditText.getText().toString().trim();

        if (codeText.isEmpty()) {
            codeEditText.setError("Le code est requis");
            codeEditText.requestFocus();
            return;
        }

        int studentCode = Integer.parseInt(codeText);
        Student student = db.studentDao().getStudentByCode(studentCode);

        if (student == null){
            codeEditText.setError("L'étudiant n'existe pas");
            codeEditText.requestFocus();
            return;
        }

        TeamStudent teamStudent = new TeamStudent(team.getId(), student.getId(), "");
        db.teamStudentDao().deleteStudentFromProject(Authentication.getId(), team.getProjectId());
        db.teamStudentDao().insert(teamStudent);

        finish();
    }

}
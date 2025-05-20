package com.example.teamwork.Activity.Students;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Activity.MenuHelper.BaseActivity;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Student;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.TeamStudent;
import com.example.teamwork.R;
/****************************************
 Fichier : StudentAddActivity
 Auteur : Antoine Blouin
 Fonctionnalité : 32.2
 Date : 2025-05-13

 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date Nom Description
 =========================================================
 ****************************************/

/**
 * Activité pour ajouter un Étudiant a une équipe.
 */
public class StudentAddActivity extends BaseActivity implements View.OnClickListener {
    /**
     * Field texte pour le code de l'étudiant.
     */
    private EditText codeEditText;
    /**
     * L'objet team au quel add l'élève.
     */
    private Team team;
    /**
     * Instance de la database.
     */
    private AppDatabase db;

    /**
     * Override onCreate, set les variables et inputes de la vue.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);

        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        db = AppDatabase.getDatabase(this);

        int teamId = getIntent().getIntExtra("teamId", -1);
        team = db.teamDao().getTeamByIdClass(teamId);

        findViewById(R.id.confirm).setOnClickListener(this);
        codeEditText = findViewById(R.id.code);
    }

    /**
     * Override on click. fait les onClickListener des boutons.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm){
            addStudent();
        }
    }

    /**
     * Ajoute l'étudiant a l'équipe dans la bd.
     */
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
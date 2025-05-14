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

import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Student;
import com.example.teamwork.R;

import java.util.List;
/****************************************
 Fichier : StudentListActivity
 Auteur : Émeric Leclerc
 Fonctionnalité : 32.5
 Date : 2025-05-05

 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date Nom Description
 =========================================================
 ****************************************/


/**
 * Activité qui listes les étudiants de la BD.
 */
public class StudentListActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Override onCreate, set le recycler view et autre élément de la vue.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        studentAddListener(teamId);
    }

    /**
     * Set le listener du bouton pour ajouter des élèves.
     * @param teamId l'id de l'équipe au quel la vue appartient.
     */
    private void studentAddListener(int teamId){
        ImageView addIcon = findViewById(R.id.add);
        if (Authentication.isStudent())
            addIcon.setVisibility(View.GONE);
        else {
            addIcon.setOnClickListener( v -> {
                startStudentAddActivity(teamId);
            });
        }
    }

    /**
     * Start l'activité pour ajouter des élèves a une équipe.
     * @param teamId l'équipe au quel l'élève sera ajouter.
     */
    private void startStudentAddActivity(int teamId){
        Intent intent = new Intent(this, StudentAddActivity.class);
        intent.putExtra("teamId", teamId);
        startActivity(intent);
    }

    /**
     * Override onClick, fait les onClickListener des divers boutons.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.activity_comment_popup, null, false),100,100, true);

        pw.showAtLocation(v, Gravity.CENTER, 0, 0);
    }
}
package com.example.teamwork.Activity.Project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import com.example.teamwork.API.ApiClient;
import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.API.Repository.ProjectRepository;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.MenuHelper.BaseActivity;
import com.example.teamwork.R;

/**
 * Activity pour modifier un projet.
 */
public class ProjectEditActivity extends BaseActivity implements View.OnClickListener {
    /**
     * Instance de la base de donnée.
     */
    private AppDatabase db;
    /**
     * L'id du projet qui sera modifier.
     */
    int projectId;
    /**
     * Token d'autentification pour faire un call à l'API web.
     */
    String authToken;
    /**
     * Input fieds pour le nom.
     */
    EditText nameEdit;
    /**
     * Input fieds pour la description.
     */
    EditText descriptionEdit;
    /**
     * Input fieds pour la taille minimum d'une équipe.
     */
    EditText minEdit;
    /**
     * Input fieds pour la taille maximum d'une équipe.
     */
    EditText maxEdit;
    /**
     * Checkbox pour permettre au élèves de joindre une équipe par eux-même.
     */
    CheckBox joinableBox;
    /**
     * Checkbox pour permettre au élèves de créer des équipes eux même.
     */
    CheckBox creatableBox;
    /**
//     * Checkbox pour mettre les classes communes en critère.
     */
    CheckBox commonClassesBox;

    /**
     * Override onCreate, crée la vue et set les variables.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
        db = AppDatabase.getDatabase(this);

        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        projectId = getIntent().getIntExtra("projectId",-1);
        authToken = getIntent().getStringExtra("authToken");

        findView();
        setupButtons();
        setTextFields(projectId);
    }

    /**
     * Assignes les différentes vue a chaque variables avbec FindViewById.
     */
    private void findView(){
        nameEdit = findViewById(R.id.name);
        descriptionEdit = findViewById(R.id.description);
        minEdit = findViewById(R.id.min_per_team);
        maxEdit = findViewById(R.id.max_per_team);
        joinableBox = findViewById(R.id.joinable_checkbox);
        creatableBox = findViewById(R.id.creatable_checkbox);
        commonClassesBox = findViewById(R.id.common_classes_checkbox);
    }

    /**
     * set les onClickListener des boutons.
     */
    private void setupButtons(){
        findViewById(R.id.edit).setOnClickListener(this);
    }

    /**
     * Méthode qui valide le formulaire.
     * @param name nom du projet
     * @param min minimum d'élèves dans une équipe.
     * @param max maximum d'élèves dans une équipe.
     * @return
     */
    public boolean validateForm(String name, String min, String max) {
        if (name.isEmpty()){
            nameEdit.setError(getString(R.string.error_name));
            nameEdit.requestFocus();
            return false;
        }

        if (min.isEmpty()) {
            minEdit.setError(getString(R.string.error_min));
            minEdit.requestFocus();
            return false;
        }

        if (Integer.parseInt(min) < 1) {
            minEdit.setError(getString(R.string.error_minLow));
            minEdit.requestFocus();
            return false;
        }

        if (max.isEmpty()) {
            maxEdit.setError(getString(R.string.error_max));
            maxEdit.requestFocus();
            return false;
        }

        if (Integer.parseInt(max) < Integer.parseInt(min)){
            maxEdit.setError(getString(R.string.error_maxLow));
            maxEdit.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Défini le onClick pour les boutons du formulaire.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit) {
            if (!validateForm(nameEdit.getText().toString(),
                    String.valueOf(minEdit.getText().toString()),
                    String.valueOf(maxEdit.getText().toString()))) {return ;}

            Project project = new Project(projectId, nameEdit.getText().toString(),
                    descriptionEdit.getText().toString(), Integer.parseInt(minEdit.getText().toString()),
                    Integer.parseInt(maxEdit.getText().toString()), joinableBox.isChecked(),
                    creatableBox.isChecked(), commonClassesBox.isChecked());
            db.projectDao().update(project);

            ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
            ProjectRepository repository = new ProjectRepository(this);
            repository.sendUpdateProject(api, project);

            finish();
        }

    }

    /**
     * Set les input feilds avec les informations de la base de donnée.
     * @param projectId l'id du projet dont les info seront pris.
     */
    public void setTextFields(int projectId) {
        db.projectDao().getProjectById(projectId).observe(this, new Observer<Project>() {
            @Override
            public void onChanged(Project project) {
                Log.v("DEBUG", String.valueOf(project));
                nameEdit.setText(project.getName());
                descriptionEdit.setText(project.getDescription());
                minEdit.setText(String.valueOf(project.getMin_per_team()));
                maxEdit.setText(String.valueOf(project.getMax_per_team()));
                joinableBox.setChecked(project.getJoinable());
                creatableBox.setChecked(project.getCreatable());
                commonClassesBox.setChecked(project.getCommon_classes());
            }
            });
    }
}
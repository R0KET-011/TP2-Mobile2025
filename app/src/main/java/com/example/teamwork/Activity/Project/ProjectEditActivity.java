package com.example.teamwork.Activity.Project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import com.example.teamwork.API.ApiClient;
import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.API.Repository.ProjectRepository;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.R;

public class ProjectEditActivity extends AppCompatActivity implements View.OnClickListener {

    private AppDatabase db;
    int projectId;
    String authToken;
    EditText nameEdit, descriptionEdit, minEdit, maxEdit;
    CheckBox joinableBox, creatableBox, commonClassesBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
        db = AppDatabase.getDatabase(this);

        projectId = getIntent().getIntExtra("projectId",-1);
        authToken = getIntent().getStringExtra("authToken");

        findView();
        setupButtons();
        setTextFields(projectId);
    }

    private void findView(){
        nameEdit = findViewById(R.id.name);
        descriptionEdit = findViewById(R.id.description);
        minEdit = findViewById(R.id.min_per_team);
        maxEdit = findViewById(R.id.max_per_team);
        joinableBox = findViewById(R.id.joinable_checkbox);
        creatableBox = findViewById(R.id.creatable_checkbox);
        commonClassesBox = findViewById(R.id.common_classes_checkbox);
    }

    private void setupButtons(){
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.edit).setOnClickListener(this);
    }

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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.edit) {
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
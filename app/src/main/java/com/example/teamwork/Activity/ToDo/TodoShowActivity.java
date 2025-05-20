/****************************************
 Fichier : TodoShowActivity.java
 Auteur : Samy Larochelle
 Fonctionnalité : Fonctionalités 36.2 et 36.3 : Modification/Suppression d'une tâche
 Date : 05/10/2025
 Vérification :
 Date           Nom                 Approuvé
 =========================================================
 Historique de modifications :
 Date           Nom                 Description
 05/10/2025     Samy Larochelle     Création
 =========================================================
 ****************************************/
package com.example.teamwork.Activity.ToDo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.teamwork.Activity.MenuHelper.BaseActivity;
import com.example.teamwork.Activity.ToDo.Audio.AudioPermissionManager;
import com.example.teamwork.Activity.ToDo.Audio.AudioPlayer;
import com.example.teamwork.Activity.ToDo.Audio.AudioRecorder;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Todo;
import com.example.teamwork.R;
import com.google.android.material.snackbar.Snackbar;

/**
 * L'activité qui permet de read/update (montrer et modifier) un élément d'une to do list.
 */
public class TodoShowActivity extends BaseActivity implements View.OnClickListener {
    /**
     * Fields inputs pour le nom et la description de l'élément de la to do list.
     */
    EditText nameEditText, descriptionEditText;
    /**
     * Layout principale de l'activité.
     */
    LinearLayout layout;
    /**
     *  Élément de la to do list qui vas être modifier.
     */
    private Todo todo;
    /**
     * Instance de la database.
     */
    private AppDatabase db;
    /**
     * Le boutons qui marque l'élément comme complété.
     */
    private Button buttonComplete;

    /**
     * image view pour représenter les boutons play, enregistrer et delete.
     */
    private ImageView audio_play, audio_record, audio_trash;
    /**
     * Audio recorder pour enregistrer les messages vocaux.
     */
    private AudioRecorder audioRecorder;
    /**
     * Audio player pour jouer les messages enregistrés.
     */
    private AudioPlayer audioPlayer;
    /**
     * state de si le record est on ou off.
     */
    private boolean record_status = false;

    /**
     * Override de la méthode onCreate. Initialise les différentes variables et les écouteurs d'événements.
     * @param savedInstanceState Si l'activité est recréée après avoir été arrêtée précédemment,
     *     ce Bundle contient les données fournies précédemment par {@link #onSaveInstanceState}.
     *     <b><i>Remarque : Sinon, cette valeur est nulle.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_show);

        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        int todoId = intent.getIntExtra("todoId", -1);

        db = AppDatabase.getDatabase(this);
        db.todoDao().getTodoById(todoId).observe(this, todo -> {
            this.todo = todo;
            this.setViews();
            this.setAudio();
        });
    }

    /**
     * Méthode pour set les views.
     */
    public void setViews() {
        nameEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);
        buttonComplete = findViewById(R.id.buttonComplete);
        layout = findViewById(R.id.layout);

        nameEditText.setText(this.todo.getNom());
        descriptionEditText.setText(this.todo.getDescription());
        if (this.todo.isCompleted())
            buttonComplete.setText(R.string.todo_mark_uncompleted);
        else
            buttonComplete.setText(R.string.todo_mark_completed);

        buttonComplete.setOnClickListener(this);
        findViewById(R.id.buttonDelete).setOnClickListener(this);
    }

    /**
     * Set ce que font les différent buttons lorsque l'on clique dessus.
     * @param v La vue qui a été cliquer.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonDelete) {
            db.todoDao().delete(todo);
            finish();
        }
        if (v.getId() == R.id.buttonComplete) {
            todo.setCompleted(!todo.isCompleted());
            if (todo.isCompleted())
                buttonComplete.setText(R.string.todo_mark_uncompleted);
            else
                buttonComplete.setText(R.string.todo_mark_completed);
        }
        //audio
        else if (v.getId() == R.id.iv_audio_mic) {
            mic_btn();
        }
        else if (v.getId() == R.id.iv_audio_play) {
            play_btn();
        }
        else if (v.getId() == R.id.iv_audio_trash) {
            //delete l'audio file, et enleve la ref dans la bd.
            audioPlayer.trash(todo.getLien_audio());
            todo.setLien_audio(null);
            audio_trash.setVisibility(View.GONE);
            audio_play.setVisibility(View.GONE);
            Snackbar.make(layout, R.string.audio_deleted, Snackbar.LENGTH_SHORT).show();
        }

    }

    /**
     * S'assure que tout les components pour l'audio est bien set.
     */
    private void setAudio() {
        audio_play = findViewById(R.id.iv_audio_play);
        audio_record = findViewById(R.id.iv_audio_mic);
        audio_trash = findViewById(R.id.iv_audio_trash);
        audio_play.setOnClickListener(this);
        audio_record.setOnClickListener(this);
        audio_trash.setOnClickListener(this);
        audioRecorder = new AudioRecorder(this);
        audioPlayer = new AudioPlayer(this);

        if (todo.getLien_audio() != null && !todo.getLien_audio().trim().isEmpty()) {
            audio_trash.setVisibility(View.VISIBLE);
            audio_play.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Fait la logique du bouton d'enregistrement. enregistre ou stop dépendament de si le micro enregistre ou non.
     */
    private void mic_btn() {
        //is recording, need to stop
        if (record_status) {
            audioRecorder.stop();
            todo.setLien_audio(audioRecorder.getLastOutputPath());
            //change icone pour mic
            audio_record.setImageResource(R.drawable.microphone);
            record_status = false;
            //check pour show le delete / play
            if (todo.getLien_audio() != null && !todo.getLien_audio().trim().isEmpty()) {
                audio_trash.setVisibility(View.VISIBLE);
                audio_play.setVisibility(View.VISIBLE);
            }

        }
        //is not recording, need to start
        else {
            //check permission avant de record
            if (!AudioPermissionManager.hasRecordAudioPermission(this)) {
                AudioPermissionManager.requestRecordAudioPermission(this);
                return;
            }
            //si il y avais un autre record, delete.
            audioRecorder.start();
            //change icon pour stop
            audio_record.setImageResource(R.drawable.player_stop);
            record_status = true;
        }
    }

    /**
     * Fait la logique pour le boutton play. joue ou stop dépendament de si un enregistrement joue.
     */
    private void play_btn(){
        //is playing, need to pause
        if (audioPlayer.isPlaying()) {
            audioPlayer.stop();
            //set texture to play
            audio_play.setImageResource(R.drawable.player_play);

        }
        //not playing, need to pause
        else {
            audioPlayer.start(todo.getLien_audio());
            //set texture to pause if playing
            if (audioPlayer.isPlaying()) {
                audio_play.setImageResource(R.drawable.player_stop);
            }
        }
    }

    /**
     * Vérifie si la permission d'utiliser l'audio a été accordée.
     * @param requestCode Le code de requête passé dans {@link #requestPermissions}.
     * @param permissions Les permissions demandées. Jamais null.
     * @param grantResults Les résultats d'autorisation correspondants, qui sont soit
     *                     {@link android.content.pm.PackageManager#PERMISSION_GRANTED},
     *                     soit {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Jamais null.
     * @param deviceId L'identifiant de l'appareil pour lequel les permissions ont été demandées.
     *                 L'appareil principal/physique est associé à {Context#DEVICE_ID_DEFAULT},
     *                 et les appareils virtuels reçoivent des identifiants uniques.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        if (AudioPermissionManager.wasRecordAudioPermissionGranted(requestCode, grantResults)) {
            mic_btn();
        }
        else {
            Snackbar.make(layout, R.string.permission_denied, Snackbar.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);
    }

    /**
     * Add des options au menu existant.
     * @param item The menu item that was selected.
     *
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (nameEditText.getText().toString().isEmpty()) {
                nameEditText.setError(getString(R.string.todo_error_name));
                nameEditText.requestFocus();
            } else {
                todo.setNom(nameEditText.getText().toString());
                todo.setDescription(descriptionEditText.getText().toString());
                db.todoDao().update(todo);
                //check audio before leaving
                if (record_status) audioRecorder.stop();
                if (audioPlayer.isPlaying()) audioPlayer.stop();
                finish();
            }
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }


}
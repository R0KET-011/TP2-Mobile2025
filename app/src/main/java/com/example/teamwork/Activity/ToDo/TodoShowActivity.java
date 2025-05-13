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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Activity.ToDo.Audio.AudioPermissionManager;
import com.example.teamwork.Activity.ToDo.Audio.AudioPlayer;
import com.example.teamwork.Activity.ToDo.Audio.AudioRecorder;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Todo;
import com.example.teamwork.R;

public class TodoShowActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nameEditText, descriptionEditText;
    LinearLayout layout;
    private Todo todo;
    private AppDatabase db;
    private Button buttonDelete, buttonEdit, buttonComplete;

    private ImageView audio_play, audio_record, audio_trash;
    private AudioRecorder audioRecorder;
    private AudioPlayer audioPlayer;
    private boolean record_status = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_show);

        Intent intent = getIntent();
        int todoId = intent.getIntExtra("todoId", -1);

        db = AppDatabase.getDatabase(this);
        db.todoDao().getTodoById(todoId).observe(this, todo -> {
            this.todo = todo;
            this.setViews();
            this.setAudio();
        });
    }

    public void setViews() {
        nameEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);
        buttonComplete = findViewById(R.id.buttonComplete);
        layout = findViewById(R.id.layout);

        nameEditText.setText(this.todo.getNom());
        descriptionEditText.setText(this.todo.getDescription());
        if (this.todo.isCompleted())
            buttonComplete.setText("Marquer comme\nincomplété");
        else
            buttonComplete.setText("Marquer comme\ncomplété");

        buttonComplete.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.buttonDelete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            if (nameEditText.getText().toString().isEmpty()) {
                nameEditText.setError("Veuillez entrer un nom de tâche");
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
        }
        if (v.getId() == R.id.buttonDelete) {
            db.todoDao().delete(todo);
            finish();
        }
        if (v.getId() == R.id.buttonComplete) {
            todo.setCompleted(!todo.isCompleted());
            if (todo.isCompleted())
                buttonComplete.setText("Marquer comme\nincomplété");
            else
                buttonComplete.setText("Marquer comme\ncomplété");
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
            Toast.makeText(this, "Audio deleted.", Toast.LENGTH_SHORT).show();
        }

    }

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

    private void play_btn(){
        //is playing, need to pause
        if (audioPlayer.isPlaying()) {
            audioPlayer.pause();
            //set texture to play
            audio_play.setImageResource(R.drawable.player_play);

        }
        //not playing, need to pause
        else {
            audioPlayer.start(todo.getLien_audio());
            //set texture to pause if playing
            if (audioPlayer.isPlaying()) {
                audio_play.setImageResource(R.drawable.player_pause);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        if (AudioPermissionManager.wasRecordAudioPermissionGranted(requestCode, grantResults)) {
            mic_btn();
        }
        else {
            Toast.makeText(this, "Permission wasn't granted", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);
    }
}
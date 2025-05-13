package com.example.teamwork.Activity.ToDo.Audio;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.media.MediaRecorder;
import android.widget.Toast;
import android.Manifest;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.Objects;

/**
 * Classe pour gérer un MediaRecorder. dois avoir les permissions pour RECORD_AUDIO.
 */
public class AudioRecorder {

    private MediaRecorder recorder;
    private String outputPath;
    private final Context context;

    /**
     * Créer un nouveau AudioRecorder
     * @param context context dans le quel le recorder sera utiliser
     */
    public AudioRecorder(Context context) {
        this.context = context;
    }

    /**
     * Start le record.
     */
    public void start() {
        // check si le recorder est déja on
        if (recorder != null) {
            Toast.makeText(context, "Recorder allready recording.", Toast.LENGTH_SHORT).show();
            return;
        }
        //check la permission
        if (!(ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(context, "Wasn't granted permission to use recorder.", Toast.LENGTH_SHORT).show();
            return;
        }

        //prepare le fichier
        String dir = Objects.requireNonNull(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)).getAbsolutePath();
        outputPath = dir + Long.toString(System.currentTimeMillis()/1000) + ".3gp";

        //prepare le recorder
        try {
            recorder = new MediaRecorder();
            recorder.setAudioSource(android.media.MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(android.media.MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(outputPath);
            recorder.setAudioEncoder(android.media.MediaRecorder.AudioEncoder.AMR_NB);
            recorder.prepare();
            recorder.start();
        }
        catch (IOException e) {
            Toast.makeText(context, "an error occured", Toast.LENGTH_SHORT).show();
            recorder.release();
            recorder = null;
        }

    }

    /**
     * Stop le record
     */
    public void stop() {
        if (recorder == null) {
            Toast.makeText(context, "Recorder Allready stopped", Toast.LENGTH_SHORT).show();
            return;
        }
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    /**
     * get le path du dernier file a avoir été record. a prendre généralement apres avoir stop().
     * @return return le path
     */
    public String getLastOutputPath() {
        return outputPath;
    }
}
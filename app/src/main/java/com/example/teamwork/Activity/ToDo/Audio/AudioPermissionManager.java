package com.example.teamwork.Activity.ToDo.Audio;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Classe pour Gérer la permission d'enregistrer.
 */
public class AudioPermissionManager {
    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final int REQUEST_CODE = 102;

    /**
     * Check si la permission d'utiliser l'audio a été granted
     * @param activity l'activité qui demande d'utiliser la permission
     * @return return si la permission est granted ou non.
     */
    public static boolean hasRecordAudioPermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request la permission d'utiliser l'enregistrement audio
     * @param activity l'activité qui demande d'utiliser la permission
     */
    public static void requestRecordAudioPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{RECORD_AUDIO}, REQUEST_CODE);
    }

    /**
     * Check si la permission à été granted. doit être utliser dans une méthode override onRequestPermissionsResult
     * @param requestCode RequestCode retourner par la méthode onRequestPermissionsResult
     * @param grantResults tableau de Int retorner par la méthode onRequestPermissionsResult
     * @return return si la permission à été granted ou non.
     */
    public static boolean wasRecordAudioPermissionGranted(int requestCode, int[] grantResults) {
        return requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }
}
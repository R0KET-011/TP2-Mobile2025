package com.example.tp2_mobile2025.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tp2_mobile2025.students.Student;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "Teamwork";
    private static final int DATABASE_VERSION = 1;


    // Tables
    private static final String STUDENTS = "students";
    private static final String TEAMS = "teams";
    private static final String ETATS = "etats";
    private static final String PROJECTS = "projects";
    private static final String TODOS = "todos";
    private static final String ACCOUNT = "account";

    //champs commun
    private static final String ID = "id";
    private static final String NOM = "nom";
    private static final String DESCRIPTION = "description";

    // Champs - STUDENTS
    private static final String COMMENTAIRE = "commentaire";
    private static final String ID_TEAM = "id_team";

    // Champs - TEAMS
    private static final String ID_ETAT = "id_etat";
    private static final String ID_PROJECT = "id_project";
    private static final String NB_MEMBRES = "nb_membres";
    private static final String IS_MINE = "is_mine";

    // Champs - ETATS
    private static final String COULEUR = "couleur";

    // Champs - PROJECTS
    private static final String ID_TODO = "id_todo";
    private static final String MAX_TEAM = "max_team";
    private static final String MIN_TEAM = "min_team";
    private static final String JOINABLE = "joinable";
    private static final String CREATABLE = "creatable";
    private static final String MEME_CLASSES = "meme_classes";

    // Champs - TODOS
    private static final String LIEN_AUDIO = "lien_audio";

    // Champs - ACCOUNT
    private static final String EMAIL = "email";
    private static final String TOKEN = "token";
    private static final String IMAGE = "image";


    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context){
        if (sqLiteManager == null) {
            sqLiteManager = new SQLiteManager(context);
        }
        return sqLiteManager;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder();

        // Table Students
        sql.append("CREATE TABLE ").append(STUDENTS).append(" (")
                .append(ID).append(" INTEGER PRIMARY KEY, ")
                .append(NOM).append(" VARCHAR(255) NOT NULL, ")
                .append(COMMENTAIRE).append(" VARCHAR(255), ")
                .append(ID_TEAM).append(" INTEGER, ")
                .append("FOREIGN KEY(").append(ID_TEAM).append(") REFERENCES ")
                .append(TEAMS).append("(").append(ID).append(") ON DELETE CASCADE")
                .append(");");
        db.execSQL(sql.toString());
        sql.setLength(0);

        // Table Teams
        sql.append("CREATE TABLE ").append(TEAMS).append(" (")
                .append(ID).append(" INTEGER PRIMARY KEY, ")
                .append(NOM).append(" VARCHAR(255) NOT NULL, ")
                .append(DESCRIPTION).append(" TEXT, ")
                .append(ID_ETAT).append(" INTEGER, ")
                .append(ID_PROJECT).append(" INTEGER, ")
                .append(NB_MEMBRES).append(" INTEGER, ")
                .append(IS_MINE).append(" BOOLEAN, ")
                .append("FOREIGN KEY(").append(ID_ETAT).append(") REFERENCES ")
                .append(ETATS).append("(").append(ID).append(") ON DELETE CASCADE, ")
                .append("FOREIGN KEY(").append(ID_PROJECT).append(") REFERENCES ")
                .append(PROJECTS).append("(").append(ID).append(") ON DELETE CASCADE")
                .append(");");
        db.execSQL(sql.toString());
        sql.setLength(0);

        // Table Etats
        sql.append("CREATE TABLE ").append(ETATS).append(" (")
                .append(ID).append(" INTEGER PRIMARY KEY, ")
                .append(NOM).append(" VARCHAR(255) NOT NULL, ")
                .append(COULEUR).append(" VARCHAR(255)")
                .append(");");
        db.execSQL(sql.toString());
        sql.setLength(0);

        // Table Projects
        sql.append("CREATE TABLE ").append(PROJECTS).append(" (")
                .append(ID).append(" INTEGER PRIMARY KEY, ")
                .append(NOM).append(" VARCHAR(255) NOT NULL, ")
                .append(DESCRIPTION).append(" TEXT, ")
                .append(ID_TODO).append(" INTEGER, ")
                .append(MAX_TEAM).append(" INTEGER, ")
                .append(MIN_TEAM).append(" INTEGER, ")
                .append(JOINABLE).append(" BOOLEAN, ")
                .append(CREATABLE).append(" BOOLEAN, ")
                .append(MEME_CLASSES).append(" BOOLEAN, ")
                .append("FOREIGN KEY(").append(ID_TODO).append(") REFERENCES ")
                .append(TODOS).append("(").append(ID).append(") ON DELETE CASCADE")
                .append(");");
        db.execSQL(sql.toString());
        sql.setLength(0);

        // Table Todos
        sql.append("CREATE TABLE ").append(TODOS).append(" (")
                .append(ID).append(" INTEGER PRIMARY KEY, ")
                .append(NOM).append(" VARCHAR(255) NOT NULL, ")
                .append(DESCRIPTION).append(" TEXT, ")
                .append(LIEN_AUDIO).append(" TEXT")
                .append(");");
        db.execSQL(sql.toString());
        sql.setLength(0);

        // Table Account
        sql.append("CREATE TABLE ").append(ACCOUNT).append(" (")
                .append(ID).append(" INTEGER PRIMARY KEY, ")
                .append(EMAIL).append(" VARCHAR(255), ")
                .append(TOKEN).append(" TEXT, ")
                .append(IMAGE).append(" TEXT")
                .append(");");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + STUDENTS);
            db.execSQL("DROP TABLE IF EXISTS " + TEAMS);
            db.execSQL("DROP TABLE IF EXISTS " + ETATS);
            db.execSQL("DROP TABLE IF EXISTS " + PROJECTS);
            db.execSQL("DROP TABLE IF EXISTS " + TODOS);
            db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT);
            onCreate(db);
        }
    }

    public void saveComment(int student_id, @Nullable String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (comment != null) {
            values.put(COMMENTAIRE, comment);
        } else {
            values.putNull(COMMENTAIRE);
        }

        db.update(STUDENTS, values, ID + " = ?", new String[]{String.valueOf(student_id)});
    }
    @Nullable
    public Student[] getStudentFromTeam(int team_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from "+STUDENTS+ " where "+ID_TEAM+" = "+team_id;

        Student[] students = null;

        try (Cursor result = db.rawQuery(sql,null)) {

        }
        return students;
    }
}

package com.example.teamwork.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.teamwork.API.Repository.GroupProjectRepository;
import com.example.teamwork.Database.Dao.CourseDao;
import com.example.teamwork.Database.Dao.GroupDao;
import com.example.teamwork.Database.Dao.GroupProjectDao;
import com.example.teamwork.Database.Dao.ProjectDao;
import com.example.teamwork.Database.Dao.StudentDao;
import com.example.teamwork.Database.Dao.TeamDao;
import com.example.teamwork.Database.Dao.TeamStudentDao;
import com.example.teamwork.Database.Dao.TodoDao;
import com.example.teamwork.Database.Dao.UserDao;
import com.example.teamwork.Database.Tables.Course;
import com.example.teamwork.Database.Tables.GroupProject;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.Database.Tables.Student;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.TeamStudent;
import com.example.teamwork.Database.Tables.Todo;
import com.example.teamwork.Database.Tables.User;
import com.example.teamwork.Database.Tables.Group;

import java.util.concurrent.Executors;

@Database(entities = {Student.class, Team.class, Project.class, TeamStudent.class, User.class,
        Todo.class, Course.class, Group.class, GroupProject.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        synchronized (AppDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "app_database")
//                        .addCallback(new RoomDatabase.Callback() {
//                            @Override
//                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                                super.onCreate(db);
//                                Executors.newSingleThreadExecutor().execute(() -> {
//                                    DatabaseSeeder.seed(getDatabase(context));
//                                });
//                            }
//                        })
                        .allowMainThreadQueries()
                        .build();
            }
            return (INSTANCE);
        }
    }

    public abstract StudentDao studentDao();

    public abstract TeamDao teamDao();

    public abstract ProjectDao projectDao();

    public abstract UserDao userDao();

    public abstract TodoDao todoDao();

    public abstract TeamStudentDao teamStudentDao();

    public abstract CourseDao courseDao();

    public abstract GroupDao groupDao();

    public abstract GroupProjectDao groupProjectDao();
}
package com.example.teamwork.Database;

import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.Database.Tables.Student;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.TeamStudent;
import com.example.teamwork.Database.Tables.Todo;

public class DatabaseSeeder {
    public static void seed(AppDatabase appDatabase) {
        Project project1 = new Project(
                1,
                "Projet intégrateur - Web",
                "Ce projet permet d'appliquer les compétences acquises dans les cours 420-404-SH (Méthodologie du développement logiciel) 420-446-SH (Développement d'applications mobiles), ainsi que le cours Web actuel. Chaque volet du projet est consacré à l’un de ces cours, sur une période d’environ une semaine, afin de créer une solution intégrée complète.",
                2,
                3,
                false,
                false,
                true//,
//                4118,
//                "Développement Web avancé"
        );

        Project project2 = new Project(
                2,
                "Conception d'un thermostat connecté",
                "Ce projet vous permettra de développer un thermostat connecté complet en CircuitPython. Vous allez acquérir des données de température, les traiter, les afficher, les stocker localement et les partager sur le cloud. Ce projet intègre plusieurs concepts importants en IoT : acquisition de données, traitement, stockage, affichage et communication.",
                2,
                4,
                true,
                true,
                false//,
//                4118,
//                "Introduction à l'utilisation d'objets connectés"
        );
        appDatabase.projectDao().insertAll(project1, project2);

        Team team1 = new Team(1, "TeamWork", "Totalement conforme", "Ce logiciel aura pour objectif d'orchestrer la formation des groupe.", project1.getId());
        Team team2 = new Team(2, "CafeGamer", "Partiellement conforme", "Description", project1.getId());
        Team team3 = new Team(3, "Walmart", "Non conforme", "Description", project1.getId());
        appDatabase.teamDao().insertAll(team1, team2, team3);

        Student student1 = new Student(1, 206242440 , "Antoine", "Blouin");
        Student student2 = new Student(2, 202648441, "Samy", "Larochelle");
        Student student3 = new Student(3, 202694862, "Kevin", "Larochelle");
        Student student4 = new Student(4, 208476863, "Emeric", "Leclerc");
        Student student5 = new Student(5, 209852364, "Arnaud", "Lecuyer");
        Student student6 = new Student(6, 283751255, "Samuel", "Pomerleau");
        Student student7 = new Student(7, 208471256, "Jeremy", "Drapeau");
        appDatabase.studentDao().insertAll(student1, student2, student3, student4, student5, student6, student7);

        TeamStudent teamStudent2 = new TeamStudent(team1.getId(), student2.getId(), null);
        TeamStudent teamStudent3 = new TeamStudent(team1.getId(), student3.getId(), null);
        TeamStudent teamStudent4 = new TeamStudent(team1.getId(), student4.getId(), null);
        TeamStudent teamStudent5 = new TeamStudent(team2.getId(), student5.getId(), null);
        TeamStudent teamStudent6 = new TeamStudent(team2.getId(), student6.getId(), null);
        TeamStudent teamStudent7 = new TeamStudent(team3.getId(), student7.getId(), null);
        appDatabase.teamStudentDao().insertAll(teamStudent2, teamStudent3, teamStudent4, teamStudent5, teamStudent6, teamStudent7);

        Todo todo1 = new Todo(1, "Manger les saucisses", "Avec fourchette", "", false);
        Todo todo2 = new Todo(1, "Sauter sur le trampoline", "", "", false);
        Todo todo3 = new Todo(1, "Lui avouer", "C'est dur", "", false);
        appDatabase.todoDao().insertAll(todo1, todo2, todo3);
    }
}

package com.example.teamwork.Database;

import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.Database.Tables.Student;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.TeamStudent;

public class DatabaseSeeder {
    public static void seed(AppDatabase appDatabase) {
        Project project1 = new Project(1, 2, 5, "Projet intégrateur - Web", "Dans ce module, vous aurez la possibilité de mettre en pratique tous les éléments des cours 420-404-SH Méthodologie du développement logiciel, 420-446-SH Développement d'applications mobiles de même que le cours de Web actuel et ce, dans le cadre d'un projet intégrateur. Environ une semaine sera dédiée pour chacun des cours afin de réaliser le projet intégrateur.", true, true, true);
        Project project2 = new Project(2, 3, 6, "Projet intégrateur - Mobile", "Description", true, true, false);
        appDatabase.projectDao().insertAll(project1, project2);

        Team team1 = new Team(1, "TeamWork", "Totalement conforme", "Ce logiciel aura pour objectif d'orchestrer la formation des groupe.", project1.getId());
        Team team2 = new Team(2, "CafeGamer", "Partiellement conforme", "Description", project1.getId());
        Team team3 = new Team(3, "Walmart", "Non conforme", "Description", project1.getId());
        appDatabase.teamDao().insertAll(team1, team2, team3);

        Student student1 = new Student(1, "Antoine", "Blouin", "Essayer d'aider, marche comme ça peut");
        Student student2 = new Student(2, "Samy", "Larochelle", "Bonne compréhension du projet");
        Student student3 = new Student(3, "Kevin", "Larochelle", "Plonger dans son API");
        Student student4 = new Student(4, "Emeric", "Leclerc", "Dédié à la tâche");
        appDatabase.studentDao().insertAll(student1, student2, student3, student4);

        TeamStudent teamStudent1 = new TeamStudent(team1.getId(), student1.getId(), null);
        TeamStudent teamStudent2 = new TeamStudent(team1.getId(), student2.getId(), null);
        TeamStudent teamStudent3 = new TeamStudent(team1.getId(), student3.getId(), null);
        appDatabase.teamStudentDao().insertAll(teamStudent1, teamStudent2, teamStudent3);
    }
}

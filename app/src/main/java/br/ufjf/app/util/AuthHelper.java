package br.ufjf.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import br.ufjf.app.model.Student;

/**
 * Created by Jorge Augusto da Silva Moreira on 12/06/2015.
 */
public class AuthHelper {
    private static final String PREF_ID = "student_id";
    private static final String PREF_EMAIL = "student_email";
    private static final String PREF_NAME = "student_name";
    private static final String PREF_COURSE = "student_course";

    public static Student getStudent(Context context) throws StudentNotLoggedIn {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String id = sharedPreferences.getString(PREF_ID, null);

        if (id == null)
            throw new StudentNotLoggedIn();

        return new Student(
                id,
                sharedPreferences.getString(PREF_NAME, null),
                sharedPreferences.getString(PREF_EMAIL, null),
                sharedPreferences.getString(PREF_COURSE, null)
        );
    }

    public static void registerLogin(Context context, Student student) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(PREF_ID, student.getId())
                .putString(PREF_EMAIL, student.getEmail())
                .putString(PREF_NAME, student.getName())
                .putString(PREF_COURSE, student.getCourse())
                .commit();
    }

    public static void registerLogout(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .remove(PREF_ID)
                .remove(PREF_EMAIL)
                .remove(PREF_NAME)
                .remove(PREF_COURSE)
                .commit();
    }

    public static class StudentNotLoggedIn extends Exception {
        public StudentNotLoggedIn() {
            super("The student has not logged in.");
        }
    }
}

package br.ufjf.app.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jorge Augusto da Silva Moreira on 12/06/2015.
 */
public class Student {
    private final String name;
    private final String email;
    private final String course;

    public Student(String name, String email, String course) {
        this.name = name;
        this.email = email;
        this.course = course;
    }

    public Student(JSONObject data) throws JSONException {
        name = data.getString(ServerDB.Student.NAME);
        email = data.getString(ServerDB.Student.EMAIL);
        course = null; //todo data.getString(ServerDB.Student.COURSE);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCourse() {
        return course;
    }
}

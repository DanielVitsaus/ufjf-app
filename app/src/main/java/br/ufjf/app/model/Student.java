package br.ufjf.app.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jorge Augusto da Silva Moreira on 12/06/2015.
 */
public class Student {
    private final String id;
    private final String name;
    private final String email;
    private final String course;

    public Student(String id, String name, String email, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
    }

    public Student(JSONObject data) throws JSONException {
        id = data.getString(ServerDB.Student.ID);
        name = data.getString(ServerDB.Student.NAME);
        email = data.getString(ServerDB.Student.EMAIL);
        course = data.getString(ServerDB.Student.COURSE);
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

    public String getId() {
        return id;
    }
}

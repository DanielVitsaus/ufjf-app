package br.ufjf.app.model.survey;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public abstract class Question {
    private final String title;

    public Question(JSONObject data) throws JSONException {
        this.title = data.getString(ServerDB.Survey.Question.TITLE);
    }

    public String getTitle() {
        return title;
    }
}

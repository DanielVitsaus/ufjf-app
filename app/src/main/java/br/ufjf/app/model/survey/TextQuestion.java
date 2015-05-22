package br.ufjf.app.model.survey;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class TextQuestion extends Question {
    private final boolean singleLine;

    public TextQuestion(JSONObject data) throws JSONException {
        super(data);
        singleLine = data.getBoolean(ServerDB.Survey.Question.SINGLE_LINE);
    }

    public boolean isSingleLine() {
        return singleLine;
    }
}

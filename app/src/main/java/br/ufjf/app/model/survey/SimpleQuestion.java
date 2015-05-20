package br.ufjf.app.model.survey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class SimpleQuestion extends Question {
    private String answer;

    public SimpleQuestion(JSONObject data) throws JSONException {
        super(data);
    }
}

package br.ufjf.app.model.survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public abstract class ChoiceQuestion extends Question {
    private final String[] options;

    public ChoiceQuestion(JSONObject data) throws JSONException {
        super(data);

        JSONArray alternativesArray = data.getJSONArray(ServerDB.Survey.Question.OPTIONS);
        this.options = new String[alternativesArray.length()];
        for (int i = 0; i < alternativesArray.length(); i++){
            options[i] = alternativesArray.getString(i);
        }
    }

    public String[] getOptions() {
        return options;
    }
}

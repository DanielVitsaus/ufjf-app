package br.ufjf.app.model.survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class ChoiceQuestion extends Question {
    private final String[] options;
    private final boolean singleChoice;

    public ChoiceQuestion(JSONObject data) throws JSONException {
        super(data);

        singleChoice = data.getBoolean(ServerDB.Survey.Question.SINGLE_CHOICE);

        JSONArray alternativesArray = data.getJSONArray(ServerDB.Survey.Question.OPTIONS);
        this.options = new String[alternativesArray.length()];
        for (int i = 0; i < alternativesArray.length(); i++)
            options[i] = alternativesArray.getString(i);
    }

    public String[] getOptions() {
        return options;
    }

    public boolean isSingleChoice() {
        return singleChoice;
    }
}

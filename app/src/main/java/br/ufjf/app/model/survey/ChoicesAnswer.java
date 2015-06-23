package br.ufjf.app.model.survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 17/06/2015.
 */
public class ChoicesAnswer extends Answer {
    private boolean[] choices;

    public ChoicesAnswer(boolean[] choices) {
        this.choices = choices;
    }

    public boolean[] getChoices() {
        return choices;
    }

    public void setChoices(boolean[] choices) {
        this.choices = choices;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONArray choices = new JSONArray();
        for (boolean anAnswer : this.choices)
            choices.put(anAnswer);

        return new JSONObject()
                .put(ServerDB.Answer.Item.TYPE, ServerDB.QuestionType.CHOICE)
                .put(ServerDB.Answer.Item.CHOICES, choices);
    }
}

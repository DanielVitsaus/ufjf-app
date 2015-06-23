package br.ufjf.app.model.survey;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 10/06/2015.
 */
public class ValueAnswer extends Answer {
    private int value;
    private boolean scale;

    public ValueAnswer(int value, boolean scale) {
        this.value = value;
        this.scale = scale;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return new JSONObject()
                .put(ServerDB.Answer.Item.TYPE, scale ? ServerDB.QuestionType.SCALE : ServerDB.QuestionType.CHOICE)
                .put(ServerDB.Answer.Item.VALUE, value);
    }
}

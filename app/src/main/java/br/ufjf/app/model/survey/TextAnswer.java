package br.ufjf.app.model.survey;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 10/06/2015.
 */
public class TextAnswer extends Answer {
    private String text;

    public TextAnswer(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return new JSONObject()
                .put(ServerDB.Answer.Item.TYPE, ServerDB.QuestionType.TEXT)
                .put(ServerDB.Answer.Item.TEXT, text);
    }
}

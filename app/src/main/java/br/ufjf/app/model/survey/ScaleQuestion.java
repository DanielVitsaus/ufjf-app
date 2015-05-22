package br.ufjf.app.model.survey;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class ScaleQuestion extends Question {
    private final int min;
    private final int max;
    private int answer;

    public ScaleQuestion(JSONObject data) throws JSONException {
        super(data);
        min = data.getInt(ServerDB.Survey.Question.MIN);
        max = data.getInt(ServerDB.Survey.Question.MAX);
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}

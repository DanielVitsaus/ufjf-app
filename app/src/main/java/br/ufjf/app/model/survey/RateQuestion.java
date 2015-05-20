package br.ufjf.app.model.survey;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class RateQuestion extends Question {
    private final double min;
    private final double max;
    private float answer;

    public RateQuestion(JSONObject data) throws JSONException {
        super(data);
        min = data.getDouble(ServerDB.Survey.Question.MIN);
        max = data.getDouble(ServerDB.Survey.Question.MAX);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public float getAnswer() {
        return answer;
    }

    public void setAnswer(float answer) {
        this.answer = answer;
    }
}

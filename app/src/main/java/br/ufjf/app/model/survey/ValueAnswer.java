package br.ufjf.app.model.survey;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 10/06/2015.
 */
public class ValueAnswer extends Answer {
    private int answer;

    public ValueAnswer(int answer) {
        this.answer = answer;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return new JSONObject()
                .put(ServerDB.Student.SurveyAnswer.Answer.TYPE, ServerDB.QuestionType.SCALE)
                .put(ServerDB.Student.SurveyAnswer.Answer.ANSWER, answer);
    }
}

package br.ufjf.app.model.survey;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 10/06/2015.
 */
public class TextAnswer extends Answer {
    private String answer;

    public TextAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return new JSONObject()
                .put(ServerDB.Student.SurveyAnswer.Answer.TYPE, ServerDB.QuestionType.TEXT)
                .put(ServerDB.Student.SurveyAnswer.Answer.ANSWER, answer);
    }
}

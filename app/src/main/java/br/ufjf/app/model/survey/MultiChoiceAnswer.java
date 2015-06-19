package br.ufjf.app.model.survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 17/06/2015.
 */
public class MultiChoiceAnswer extends Answer {
    private boolean[] answer;

    public MultiChoiceAnswer(boolean[] answer) {
        this.answer = answer;
    }

    public boolean[] getAnswer() {
        return answer;
    }

    public void setAnswer(boolean[] answer) {
        this.answer = answer;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONArray choices = new JSONArray();
        for (boolean anAnswer : answer)
            choices.put(anAnswer);

        return new JSONObject()
                .put(ServerDB.Student.SurveyAnswer.Answer.TYPE, ServerDB.QuestionType.CHOICE)
                .put(ServerDB.Student.SurveyAnswer.Answer.ANSWER, choices);
    }
}

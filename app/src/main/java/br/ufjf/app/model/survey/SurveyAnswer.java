package br.ufjf.app.model.survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 17/06/2015.
 */
public class SurveyAnswer {
    private String surveyId;
    private Answer[] answers;

    public SurveyAnswer(String surveyId, int size) {
        this.surveyId = surveyId;
        this.answers = new Answer[size];
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public JSONObject toJSON() throws JSONException {
        JSONArray array = new JSONArray();
        for (Answer answer : answers)
            array.put(answer.toJSON());

        return new JSONObject()
                .put(ServerDB.Student.SurveyAnswer.SURVEY, surveyId)
                .put(ServerDB.Student.SurveyAnswer.ANSWERS, array);
    }
}

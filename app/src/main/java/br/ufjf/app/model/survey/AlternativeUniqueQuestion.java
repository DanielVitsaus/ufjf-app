package br.ufjf.app.model.survey;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class AlternativeUniqueQuestion extends AlternativeQuestion {
    private int answer;

    public AlternativeUniqueQuestion(JSONObject data) throws JSONException {
        super(data);

        answer = data.getInt(ServerDB.Survey.Question.ANSWER);
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}

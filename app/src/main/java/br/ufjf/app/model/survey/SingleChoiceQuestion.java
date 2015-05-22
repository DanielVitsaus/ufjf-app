package br.ufjf.app.model.survey;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class SingleChoiceQuestion extends ChoiceQuestion {
    private int answer;

    public SingleChoiceQuestion(JSONObject data) throws JSONException {
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

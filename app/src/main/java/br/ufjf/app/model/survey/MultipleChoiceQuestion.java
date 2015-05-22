package br.ufjf.app.model.survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class MultipleChoiceQuestion extends ChoiceQuestion {
    private boolean[] answer;

    public MultipleChoiceQuestion(JSONObject data) throws JSONException {
        super(data);

        JSONArray answerArray = data.getJSONArray(ServerDB.Survey.Question.ANSWER);
        this.answer = new boolean[answerArray.length()];
        for (int i = 0; i < answerArray.length(); i++)
            answer[i] = answerArray.getBoolean(i);
    }

    public boolean[] getAnswer() {
        return answer;
    }

    public void setAnswer(boolean[] answer) {
        this.answer = answer;
    }
}

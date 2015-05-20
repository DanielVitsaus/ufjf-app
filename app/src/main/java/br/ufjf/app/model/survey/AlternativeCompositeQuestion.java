package br.ufjf.app.model.survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class AlternativeCompositeQuestion extends AlternativeQuestion {
    private int[] answer;

    public AlternativeCompositeQuestion(JSONObject data) throws JSONException {
        super(data);

        JSONArray answerArray = data.getJSONArray(ServerDB.Survey.Question.ANSWER);
        this.answer = new int[answerArray.length()];
        for (int i = 0; i < answerArray.length(); i++)
            answer[i] = answerArray.getInt(i);
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}

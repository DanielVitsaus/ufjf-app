package br.ufjf.app.model.survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class Survey {
    private final String id;
    private final String title;
    private final String description;
    private final Question[] questions;
    private final int visibility;

    public Survey(JSONObject data) throws JSONException {
        // Parse basic info
        id = data.getString(ServerDB.Survey.ID);
        title = data.getString(ServerDB.Survey.TITLE);
        description = data.getString(ServerDB.Survey.DESCRIPTION);
        visibility = data.getJSONObject(ServerDB.Survey.VISIBILITY).getInt(ServerDB.Survey.Visibility.TYPE);

        // Parse questions
        JSONArray questionsArray = data.getJSONArray(ServerDB.Survey.QUESTIONS);
        if (questionsArray != null) {
            int length = questionsArray.length();
            questions = new Question[length];
            for (int i = 0; i < length; i++) {
                JSONObject questionJson = questionsArray.getJSONObject(i);
                switch (questionJson.getInt(ServerDB.Survey.Question.TYPE)) {
                    case ServerDB.QuestionType.TEXT:
                        questions[i] = new TextQuestion(questionJson);
                        break;
                    case ServerDB.QuestionType.CHOICE:
                        questions[i] = new ChoiceQuestion(questionJson);
                        break;
                    case ServerDB.QuestionType.SCALE:
                        questions[i] = new ScaleQuestion(questionJson);
                        break;
                }
            }
        } else
            questions = null;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public int getVisibility() {
        return visibility;
    }
}

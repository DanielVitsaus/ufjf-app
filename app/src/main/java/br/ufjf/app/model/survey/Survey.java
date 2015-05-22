package br.ufjf.app.model.survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.ufjf.app.model.ServerDB;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class Survey {
    private final String title;
    private final String description;
    private final ArrayList<Question> questions;

    public Survey(String title, String description, ArrayList<Question> questions) {
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    public Survey(JSONObject survey) throws JSONException {
        // Parse basic info
        title = survey.getString(ServerDB.Survey.TITLE);
        description = survey.getString(ServerDB.Survey.DESCRIPTION);

        // Parse questions
        questions = new ArrayList<>();
        JSONArray questionsArray = survey.getJSONArray(ServerDB.Survey.QUESTIONS);
        for (int i = 0; i < questionsArray.length(); i++) {
            JSONObject questionJson = questionsArray.getJSONObject(i);
            switch (questionJson.getInt(ServerDB.Survey.Question.TYPE)) {
                case ServerDB.QuestionTypes.SIMPLE:
                    questions.add(new TextQuestion(questionJson));
                    break;
                case ServerDB.QuestionTypes.ALTERNATIVE_UNIQUE:
                    questions.add(new SingleChoiceQuestion(questionJson));
                    break;
                case ServerDB.QuestionTypes.ALTERNATIVE_COMPOSITE:
                    questions.add(new MultipleChoiceQuestion(questionJson));
                    break;
                case ServerDB.QuestionTypes.RATE:
                    questions.add(new ScaleQuestion(questionJson));
                    break;
                case ServerDB.QuestionTypes.VARIATION:
                    questions.add(new VariationQuestion(questionJson));
                    break;
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}

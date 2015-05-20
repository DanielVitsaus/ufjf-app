package br.ufjf.app.model.survey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class VariationQuestion extends Question {
    private float min;
    private float max;

    public VariationQuestion(JSONObject data) throws JSONException {
        super(data);
    }
}

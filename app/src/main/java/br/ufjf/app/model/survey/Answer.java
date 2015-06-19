package br.ufjf.app.model.survey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jorge Augusto da Silva Moreira on 17/06/2015.
 */
public abstract class Answer {
    public abstract JSONObject toJSON() throws JSONException;
}

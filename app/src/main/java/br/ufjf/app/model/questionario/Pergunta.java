package br.ufjf.app.model.questionario;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.DBRemoto;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public abstract class Pergunta {
    private final String titulo;

    public Pergunta(JSONObject data) throws JSONException {
        this.titulo = data.getString(DBRemoto.Questionario.Pergunta.TITULO);
    }

    public String getTitulo() {
        return titulo;
    }
}

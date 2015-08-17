package br.ufjf.app.model.questionario;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.DBRemoto;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class PerguntaEscala extends Pergunta {
    private final int min;
    private final int max;

    public PerguntaEscala(JSONObject data) throws JSONException {
        super(data);
        min = data.getInt(DBRemoto.Questionario.Pergunta.MIN);
        max = data.getInt(DBRemoto.Questionario.Pergunta.MAX);
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}

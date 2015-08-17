package br.ufjf.app.model.questionario;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.DBRemoto;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class PerguntaTexto extends Pergunta {
    private final boolean umaLinha;

    public PerguntaTexto(JSONObject data) throws JSONException {
        super(data);
        umaLinha = data.getBoolean(DBRemoto.Questionario.Pergunta.UMA_LINHA);
    }

    public boolean isUmaLinha() {
        return umaLinha;
    }
}

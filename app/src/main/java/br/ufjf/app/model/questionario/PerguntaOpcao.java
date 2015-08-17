package br.ufjf.app.model.questionario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.DBRemoto;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class PerguntaOpcao extends Pergunta {
    private final String[] opcoes;
    private final boolean escolhaUnica;

    public PerguntaOpcao(JSONObject data) throws JSONException {
        super(data);

        escolhaUnica = data.getBoolean(DBRemoto.Questionario.Pergunta.OPCAO_UNICA);

        JSONArray alternativesArray = data.getJSONArray(DBRemoto.Questionario.Pergunta.OPCOES);
        this.opcoes = new String[alternativesArray.length()];
        for (int i = 0; i < alternativesArray.length(); i++)
            opcoes[i] = alternativesArray.getString(i);
    }

    public String[] getOpcoes() {
        return opcoes;
    }

    public boolean isEscolhaUnica() {
        return escolhaUnica;
    }
}

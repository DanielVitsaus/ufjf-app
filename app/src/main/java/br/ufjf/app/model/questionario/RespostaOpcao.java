package br.ufjf.app.model.questionario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.DBRemoto;

/**
 * Created by Jorge Augusto da Silva Moreira on 17/06/2015.
 */
public class RespostaOpcao extends Resposta {
    private boolean[] opcoes;

    public RespostaOpcao(boolean[] opcoes) {
        this.opcoes = opcoes;
    }

    public boolean[] getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(boolean[] opcoes) {
        this.opcoes = opcoes;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONArray choices = new JSONArray();
        for (boolean anAnswer : this.opcoes)
            choices.put(anAnswer);

        return new JSONObject()
                .put(DBRemoto.Resposta.Item.TIPO, DBRemoto.TipoPergunta.OPCAO)
                .put(DBRemoto.Resposta.Item.OPCOES, choices);
    }
}

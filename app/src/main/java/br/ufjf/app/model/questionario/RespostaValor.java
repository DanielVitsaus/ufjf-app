package br.ufjf.app.model.questionario;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.DBRemoto;

/**
 * Created by Jorge Augusto da Silva Moreira on 10/06/2015.
 */
public class RespostaValor extends Resposta {
    private int valor;
    private boolean escala;

    public RespostaValor(int valor, boolean escala) {
        this.valor = valor;
        this.escala = escala;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return new JSONObject()
                .put(DBRemoto.Resposta.Item.TIPO, escala ? DBRemoto.TipoPergunta.ESCALA : DBRemoto.TipoPergunta.OPCAO)
                .put(DBRemoto.Resposta.Item.VALOR, valor);
    }
}

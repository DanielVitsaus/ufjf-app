package br.ufjf.app.model.questionario;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.DBRemoto;

/**
 * Created by Jorge Augusto da Silva Moreira on 10/06/2015.
 */
public class RespostaTexto extends Resposta {
    private String texto;

    public RespostaTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return new JSONObject()
                .put(DBRemoto.Resposta.Item.TIPO, DBRemoto.TipoPergunta.TEXTO)
                .put(DBRemoto.Resposta.Item.TEXTO, texto);
    }
}

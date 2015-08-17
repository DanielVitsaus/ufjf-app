package br.ufjf.app.model.questionario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.DBRemoto;

/**
 * Created by Jorge Augusto da Silva Moreira on 17/06/2015.
 */
public class RespostaQuestionario {
    private String idEstudante;
    private String idQuestionario;
    private Resposta[] respostas;

    public RespostaQuestionario(String idEstudante, String idQuestionario, int size) {
        this.idEstudante = idEstudante;
        this.idQuestionario = idQuestionario;
        this.respostas = new Resposta[size];
    }

    public Resposta[] getRespostas() {
        return respostas;
    }

    public JSONObject toJSON() throws JSONException {
        JSONArray array = new JSONArray();
        for (Resposta resposta : respostas)
            array.put(resposta.toJSON());

        return new JSONObject()
                .put(DBRemoto.Resposta.ESTUDANTE, idEstudante)
                .put(DBRemoto.Resposta.QUESTIONARIO, idQuestionario)
                .put(DBRemoto.Resposta.ITENS, array);
    }
}

package br.ufjf.app.model.questionario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufjf.app.model.DBRemoto;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class Questionario {
    private final String id;
    private final String titulo;
    private final String descricao;
    private final Pergunta[] perguntas;
    private final int visibilidade;

    public Questionario(JSONObject data) throws JSONException {
        // Parse basic info
        id = data.getString(DBRemoto.Questionario.ID);
        titulo = data.getString(DBRemoto.Questionario.TITULO);
        descricao = data.getString(DBRemoto.Questionario.DESCRICAO);
        visibilidade = data.getJSONObject(DBRemoto.Questionario.VISIBILIDADE).getInt(DBRemoto.Questionario.Visibilidade.TIPO);

        // Parse perguntas
        JSONArray questionsArray = data.getJSONArray(DBRemoto.Questionario.PERGUNTAS);
        if (questionsArray != null) {
            int length = questionsArray.length();
            perguntas = new Pergunta[length];
            for (int i = 0; i < length; i++) {
                JSONObject questionJson = questionsArray.getJSONObject(i);
                switch (questionJson.getInt(DBRemoto.Questionario.Pergunta.TIPO)) {
                    case DBRemoto.TipoPergunta.TEXTO:
                        perguntas[i] = new PerguntaTexto(questionJson);
                        break;
                    case DBRemoto.TipoPergunta.OPCAO:
                        perguntas[i] = new PerguntaOpcao(questionJson);
                        break;
                    case DBRemoto.TipoPergunta.ESCALA:
                        perguntas[i] = new PerguntaEscala(questionJson);
                        break;
                }
            }
        } else
            perguntas = null;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Pergunta[] getPerguntas() {
        return perguntas;
    }

    public int getVisibilidade() {
        return visibilidade;
    }
}

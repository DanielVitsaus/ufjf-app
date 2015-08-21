package br.ufjf.app.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jorge Augusto da Silva Moreira on 12/06/2015.
 */
public class Aluno {
    private final String id;
    private final String nome;
    private final String email;
    private final String curso;

    public Aluno(String id, String nome, String email, String curso) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.curso = curso;
    }

    public Aluno(JSONObject data) throws JSONException {
        id = data.getString(DBRemoto.Estudante.ID);
        nome = data.getString(DBRemoto.Estudante.NOME);
        email = data.getString(DBRemoto.Estudante.EMAIL);
        curso = data.getString(DBRemoto.Estudante.CURSO);
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCurso() {
        return curso;
    }

    public String getId() {
        return id;
    }
}

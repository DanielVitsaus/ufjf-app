package br.ufjf.app.model.info;

import com.google.api.client.util.Key;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by cgco on 18/08/15.
 */
public class ProReitoria {

    @Key
    private String nome;
    @Key
    private String email;
    @Key
    private String[] telefones;
    @Key
    private List<ProReitoria> adjuntas;

    public ProReitoria(){

    }

    public ProReitoria(JSONObject json) throws JSONException {
        this.nome = json.getString("nome");
        this.email = json.getString("email");

        JSONArray array = json.getJSONArray("telefones");
        this.telefones = new String[array.length()];
        for(int i = 0; i < array.length(); i++){
            this.telefones[i] = array.getString(i);
        }
    }

    public ProReitoria(String nome, String email, String[] telefones) {
        this.nome = nome;
        this.email = email;
        this.telefones = telefones;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getTelefones() {
        return telefones;
    }

    public void setTelefones(String[] telefones) {
        this.telefones = telefones;
    }

    public List<ProReitoria> getAdjuntas() {
        return adjuntas;
    }

    public void setAdjuntas(List<ProReitoria> adjuntas) {
        this.adjuntas = adjuntas;
    }
}

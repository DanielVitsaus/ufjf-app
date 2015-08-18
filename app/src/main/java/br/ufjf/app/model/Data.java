package br.ufjf.app.model;

import com.google.api.client.util.Key;

/**
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class Data {
    @Key("day_start")
    private int diaInicio;
    @Key("day_end")
    private int diaTermino;
    @Key("month")
    private int mes;
    @Key("title")
    private String titulo;

    public Data(){

    }

    public Data(int diaInicio, int diaTermino, int mes, String titulo) {
        this.diaInicio = diaInicio;
        this.diaTermino = diaTermino;
        this.mes = mes;
        this.titulo = titulo;
    }

    public int getDiaInicio() {
        return diaInicio;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getMes() {
        return mes;
    }

    public int getDiaTermino() {
        return diaTermino;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }
}

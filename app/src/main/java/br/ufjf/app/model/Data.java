package br.ufjf.app.model;

/**
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class Data {
    private final int diaInicio;
    private final int diaTermino;
    private final int mes;
    private final String titulo;

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
}

package br.ufjf.app.model.noticias;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jorge Augusto da Silva Moreira on 30/01/2015.
 */
public class Noticia implements Parcelable {

    public static final Parcelable.Creator<Noticia> CREATOR = new Parcelable.Creator<Noticia>() {
        public Noticia createFromParcel(Parcel in) {
            return new Noticia(in);
        }

        public Noticia[] newArray(int size) {
            return new Noticia[size];
        }
    };
    private String titulo;
    private String link;
    private String data;
    private String conteudo;

    public Noticia() {

    }

    protected Noticia(Parcel in) {
        this.titulo = in.readString();
        this.link = in.readString();
        this.data = in.readString();
        this.conteudo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.titulo);
        dest.writeString(this.link);
        dest.writeString(this.data);
        dest.writeString(this.conteudo);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String title) {
        this.titulo = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}

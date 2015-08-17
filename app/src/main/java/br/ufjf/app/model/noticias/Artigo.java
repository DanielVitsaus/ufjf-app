package br.ufjf.app.model.noticias;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jorge Augusto da Silva Moreira on 30/01/2015.
 */
public class Artigo implements Parcelable {

    public static final Parcelable.Creator<Artigo> CREATOR = new Parcelable.Creator<Artigo>() {
        public Artigo createFromParcel(Parcel in) {
            return new Artigo(in);
        }

        public Artigo[] newArray(int size) {
            return new Artigo[size];
        }
    };
    private String title;
    private String link;
    private String date;
    private String content;

    public Artigo() {

    }

    protected Artigo(Parcel in) {
        this.title = in.readString();
        this.link = in.readString();
        this.date = in.readString();
        this.content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.link);
        dest.writeString(this.date);
        dest.writeString(this.content);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

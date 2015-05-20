package br.ufjf.app.model.news;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jorge Augusto da Silva Moreira on 30/01/2015.
 */
public class Article implements Parcelable {

    private String title;
    private String link;
    private String date;
    private String content;

    public Article(){

    }

    protected Article(Parcel in) {
        this.title = in.readString();
        this.link = in.readString();
        this.date = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

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

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

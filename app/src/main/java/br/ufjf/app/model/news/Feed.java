package br.ufjf.app.model.news;

import java.util.ArrayList;

/**
 * Created by Jorge Augusto da Silva Moreira on 30/01/2015.
 */
public class Feed {

    private String titulo;
    private String link;
    private ArrayList<Article> articles;

    public Feed(){

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

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
}

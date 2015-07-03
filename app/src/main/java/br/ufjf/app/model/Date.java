package br.ufjf.app.model;

/**
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class Date {
    private final int day;
    private final String title;

    public Date(int day, String title) {
        this.day = day;
        this.title = title;
    }

    public int getDay() {
        return day;
    }

    public String getTitle() {
        return title;
    }
}

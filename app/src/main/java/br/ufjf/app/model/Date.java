package br.ufjf.app.model;

/**
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class Date {
    private final int day;
    private final String title;
    private final String description;

    public Date(int day, String title, String description) {
        this.day = day;
        this.title = title;
        this.description = description;
    }

    public int getDay() {
        return day;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

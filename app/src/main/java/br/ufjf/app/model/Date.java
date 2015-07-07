package br.ufjf.app.model;

/**
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class Date {
    private final int day;
    private final int month;
    private final String title;

    public Date(int day, int month, String title) {
        this.day = day;
        this.month = month;
        this.title = title;
    }

    public int getDay() {
        return day;
    }

    public String getTitle() {
        return title;
    }

    public int getMonth() {
        return month;
    }
}

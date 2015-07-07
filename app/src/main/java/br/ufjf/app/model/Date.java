package br.ufjf.app.model;

/**
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class Date {
    private final int dayStart;
    private final int dayEnd;
    private final int month;
    private final String title;

    public Date(int dayStart, int dayEnd, int month, String title) {
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.month = month;
        this.title = title;
    }

    public int getDayStart() {
        return dayStart;
    }

    public String getTitle() {
        return title;
    }

    public int getMonth() {
        return month;
    }

    public int getDayEnd() {
        return dayEnd;
    }
}

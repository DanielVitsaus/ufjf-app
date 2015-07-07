package br.ufjf.app.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class AcademicCalendar {
    private int year;
    private HashMap<Integer, Integer> monthsPositions;
    private List<Date> dates;

    public AcademicCalendar(int year, JSONArray data) throws JSONException {
        this.year = year;
        monthsPositions = new HashMap<>();
        dates = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject date = data.getJSONObject(i);
            int month = date.getInt(ServerDB.Date.MONTH) - 1; // Conversion to java.util.Calendar constant
            if (!monthsPositions.containsKey(month))
                monthsPositions.put(month, i);
            dates.add(new Date(
                    date.getInt(ServerDB.Date.DAY_START),
                    date.has(ServerDB.Date.DAY_END) ? date.getInt(ServerDB.Date.DAY_END) : -1,
                    month,
                    date.getString(ServerDB.Date.TITLE)
            ));
        }
    }

    public int getYear() {
        return year;
    }

    public List<Date> getDatesByMonth(int month) {
        if (monthsPositions.containsKey(month)) {
            List<Date> datesInMonth = new ArrayList<>();
            int startIndex = monthsPositions.get(month);
            for (int i = startIndex; i < dates.size() && dates.get(i).getMonth() == month; i++)
                datesInMonth.add(dates.get(i));
            return datesInMonth;
        } else return null;
    }

}

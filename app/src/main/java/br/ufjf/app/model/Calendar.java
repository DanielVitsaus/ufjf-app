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
public class Calendar {
    private int year;
    private HashMap<Integer, List<Date>> dates;

    public Calendar(int year, JSONArray data) throws JSONException {
        this.year = year;
        dates = new HashMap<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject date = data.getJSONObject(i);
            int month = date.getInt(ServerDB.Date.MONTH) - 1; // Conversion to java.util.Calendar constant
            List<Date> datesInMonth = dates.get(month);
            if (datesInMonth == null) {
                datesInMonth = new ArrayList<>();
                dates.put(month, datesInMonth);
            }
            datesInMonth.add(new Date(
                    date.getInt(ServerDB.Date.DAY),
                    date.getString(ServerDB.Date.TITLE)
            ));
        }
    }

    public int getYear() {
        return year;
    }

    public List<Date> getDatesByMonth(int month){
        return dates.get(month);
    }

}

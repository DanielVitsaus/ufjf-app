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
public class CalendarioAcademico {
    private int ano;
    private HashMap<Integer, Integer> posicoesMeses;
    private List<Data> datas;

    public CalendarioAcademico(int ano, JSONArray data) throws JSONException {
        this.ano = ano;
        posicoesMeses = new HashMap<>();
        datas = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject date = data.getJSONObject(i);
            int month = date.getInt(DBRemoto.Data.MES) - 1; // Conversion to java.util.Calendar constant
            if (!posicoesMeses.containsKey(month))
                posicoesMeses.put(month, i);
            datas.add(new Data(
                    date.getInt(DBRemoto.Data.DIA_INICIAL),
                    date.has(DBRemoto.Data.DAY_TERMINO) ? date.getInt(DBRemoto.Data.DAY_TERMINO) : -1,
                    month,
                    date.getString(DBRemoto.Data.TITULO)
            ));
        }
    }

    public int getAno() {
        return ano;
    }

    public List<Data> getDatasPorMes(int month) {
        if (posicoesMeses.containsKey(month)) {
            List<Data> datesInMonth = new ArrayList<>();
            int startIndex = posicoesMeses.get(month);
            for (int i = startIndex; i < datas.size() && datas.get(i).getMes() == month; i++)
                datesInMonth.add(datas.get(i));
            return datesInMonth;
        } else return null;
    }

}

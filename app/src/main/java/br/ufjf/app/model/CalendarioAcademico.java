package br.ufjf.app.model;

import com.google.api.client.util.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Contém as datas do calendário acadêmico de um ano
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class CalendarioAcademico {
    private HashMap<Integer, Integer> posicoesMeses;
    @Key("dates")
    private List<Data> datas;

    public void prepararDatas() {
        if (datas != null) {
            posicoesMeses = new HashMap<>();
            for (int i = 0; i < datas.size(); i++) {
                Data data = datas.get(i);
                data.setMes(data.getMes() - 1); // Conversão para constante de java.util.Calendar
                if (!posicoesMeses.containsKey(data.getMes()))
                    posicoesMeses.put(data.getMes(), i);
            }
        }
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

package br.ufjf.app.ui;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import br.ufjf.app.model.info.ProReitoria;
import br.ufjf.app.ui.adapter.ProReitoriaAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Exibe a lista de questionários disponìveis para o estudante responder
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class InfoActivity extends DrawerActivity {

    private static final String TESTE = "[{" +
            "\"nome\":\"Pró-Reitoria de Recursos Humanos\"," +
            "\"email\":\"secretaria.prorh@ufjf.edu.br\"," +
            "\"telefones\":[\"(32) 2102-3930\"]" +
            "}]";

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer_list);
        inicializarToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.lista);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        try {
            JSONArray json = new JSONArray(TESTE);
            List<ProReitoria> reitorias = new ArrayList<>();
            for (int i = 0; i < json.length(); i++)
                reitorias.add(new ProReitoria(json.getJSONObject(i)));

            recyclerView.setAdapter(new ProReitoriaAdapter(reitorias, new ProReitoriaAdapter.OnItemSelecionadoListener() {
                @Override
                public void onArtigoSelecionado(ProReitoria proReitoria) {
                    new AlertDialog.Builder(InfoActivity.this)
                            .setTitle(proReitoria.getNome())
                            .setMessage("Email: " + proReitoria.getEmail() + "\nTelefones: " + proReitoria.getTelefones()[0])
                            .show();
                }
            }));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

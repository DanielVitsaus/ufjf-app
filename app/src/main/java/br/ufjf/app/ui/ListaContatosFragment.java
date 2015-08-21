package br.ufjf.app.ui;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import br.ufjf.app.model.Contato;
import br.ufjf.app.ui.adapter.ContatosAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Exibe a lista de contatos
 */
public class ListaContatosFragment extends Fragment {

    /**
     * Exemplos de teste
     **/
    private static final String PRO_REITORIAS = "[{" +
            "\"nome\":\"Pró-Reitoria de Recursos Humanos\"," +
            "\"email\":\"secretaria.prorh@ufjf.edu.br\"," +
            "\"telefones\":[\"(32) 2102-3930\"]" +
            "}]";

    private static final String COORDENAÇÕES = "[{\"nome\":\"Administração\"}," +
            "{\"nome\":\"Artes Visuais\"}," +
            "{\"nome\":\"Bacharelado Interdisciplinar em Ciências Humanas\"}," +
            "{\"nome\":\"Bacharelado Interdisciplinar em Artes e Design\"}," +
            "{\"nome\":\"Arquitetura e Urbanismo\"}," +
            "{\"nome\":\"Bacharelado em Música\"}," +
            "{\"nome\":\"Ciências Biológicas\"}]";

    private static final String UNIDADES = "[{\"nome\":\"Instituto de Ciências Exatas\"}," +
            "{\"nome\":\"Instituo de Ciências Humanas\"}," +
            "{\"nome\":\"Instituo de Ciências Biológicas\"}]";

    private static final String OUTROS = "[{\"nome\":\"Arquivo Central\"}," +
            "{\"nome\":\"Centro de Biologia da Reprodução\"}," +
            "{\"nome\":\"Centro de Educação à Distância\"}," +
            "{\"nome\":\"Hospital Universitário\"}," +
            "{\"nome\":\"Centro de Ciências\"}," +
            "{\"nome\":\"Centro Ibero Americano\"}," +
            "{\"nome\":\"Critt – Centro Regional de Inovação e Transferência de Tecnologia\"}," +
            "{\"nome\":\"Cine-Theatro Central\"}," +
            "{\"nome\":\"Museu de Arte Murilo Mendes – MAMM\"}]";

    private RecyclerView recyclerView;
    private List<Contato> contatos;

    private static final String ARG_TIPO = "tipo";

    // Tipos de contatos
    public static final int TIPO_PRO_REITORIAS = 0;
    public static final int TIPO_COORDENACOES = 1;
    public static final int TIPO_UNIDADES = 2;
    public static final int TIPO_OUTROS = 3;

    /**
     * Prepara uma nova instancia de ListaContatosFragment
     * @param tipo Tipo de contatos a ser exibido. Deve ser TIPO_COORDENACOES, TIPO_COORDENACOES, TIPO_UNIDADES ou TIPO_OUTROS
     * @return Um ListaContatosFragment para o tipo desejado de contatos
     */
    public static ListaContatosFragment obterNovo(int tipo) {
        Bundle args = new Bundle();
        args.putInt(ARG_TIPO, tipo);
        ListaContatosFragment contatosFragment = new ListaContatosFragment();
        contatosFragment.setArguments(args);
        return contatosFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // Define o tipo de contatos e lê o JSON correspondente
            // todo obter do servidor
            JSONArray json;
            switch (getArguments().getInt(ARG_TIPO)) {
                case TIPO_PRO_REITORIAS:
                    json = new JSONArray(PRO_REITORIAS);
                    break;
                case TIPO_COORDENACOES:
                    json = new JSONArray(COORDENAÇÕES);
                    break;
                case TIPO_UNIDADES:
                    json = new JSONArray(UNIDADES);
                    break;
                default:
                    json = new JSONArray(OUTROS);
                    break;
            }

            contatos = new ArrayList<>();
            for (int i = 0; i < json.length(); i++)
                contatos.add(new Contato(json.getJSONObject(i)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.lista);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configura a lista
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ContatosAdapter(contatos, new OnItemSelecionadoListener<Contato>() {
            /**
             * Exibe as informaçoes do contato
             * @param contato COntato selecionado
             */
            @Override
            public void onItemSelecionado(Contato contato) {
                // Monta a lista de contatos da pro-reitoria selecionada
                final String email = contato.getEmail();
                final String[] telefones = contato.getTelefones();

                CharSequence[] contatos = new CharSequence[
                        (email != null ? 1 : 0)
                                + (telefones != null ? telefones.length : 0)
                        ];

                // Exibe o e-mail primeiro
                if (email != null)
                    contatos[0] = email;
                // Telefones a seguir
                if (telefones != null)
                    System.arraycopy(telefones, 0, contatos, (email != null ? 1 : 0), telefones.length);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle(contato.getNome());

                // Adiciona a lista de informações para contato
                if (contatos.length > 0)
                    builder.setItems(contatos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int posicao) {
                            if (email != null) {
                                if (posicao == 0) enviarEmail(email);
                                else {
                                    if (telefones != null)
                                        telefonar(telefones[posicao - 1]);
                                }
                            } else if (telefones != null) telefonar(telefones[posicao]);
                        }

                        private void enviarEmail(String email) {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("message/rfc822");
                            i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                            try {
                                startActivity(Intent.createChooser(i, getString(R.string.enviar_email_)));
                            } catch (ActivityNotFoundException ex) {
                                Toast.makeText(getActivity(), R.string.sem_app_email, Toast.LENGTH_SHORT).show();
                            }
                        }

                        private void telefonar(String telefone) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + telefone));
                            startActivity(intent);
                        }
                    });
                else
                    builder.setMessage("Nenhuma informação disponível");

                builder.show();
            }
        }));
    }
}
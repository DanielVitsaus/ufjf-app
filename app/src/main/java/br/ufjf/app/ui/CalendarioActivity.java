package br.ufjf.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ufjf.app.async.ObterCalendarioTask;
import br.ufjf.app.model.CalendarioAcademico;
import br.ufjf.app.model.Data;
import br.ufjf.app.ui.adapter.DatasAdapter;
import br.ufjf.app.ui.adapter.MesesAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Exibe o calendário acadêmico
 *
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class CalendarioActivity extends DrawerActivity implements MesFragment.Listener {

    private static final int COD_REQ_LEMBRETE = 15487;

    private ViewPager viewPager;
    private MesesAdapter mesesAdapter;
    private CalendarioAcademico calendarioAcademico;
    private RecyclerView recyclerView;
    private DatasAdapter datasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        inicializarToolbar();

        new ObterCalendarioTask(new ObterCalendarioTask.Callback() {
            @Override
            public void onFinish(CalendarioAcademico calendarioAcademico) {
                if (calendarioAcademico != null) {
                    CalendarioActivity.this.calendarioAcademico = calendarioAcademico;

                    // Obtem os nomes dos meses
                    Calendar cal = java.util.Calendar.getInstance();
                    List<String> nomesMeses = new ArrayList<>();
                    for (int i = 0; i < 12; i++) {
                        cal.set(java.util.Calendar.MONTH, i);
                        String monthName = cal.getDisplayName(java.util.Calendar.MONTH, java.util.Calendar.LONG, getResources().getConfiguration().locale);
                        monthName = Character.toUpperCase(monthName.charAt(0)) + monthName.substring(1);
                        nomesMeses.add(monthName);
                    }

                    // Configura o ViewPager
                    mesesAdapter = new MesesAdapter(getSupportFragmentManager(), nomesMeses);
                    viewPager = (ViewPager) findViewById(R.id.calendar_pager);
                    viewPager.setAdapter(mesesAdapter);
                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            // Rola a lista de datas até a primeira do mês atual
                            int monthPosition = datasAdapter.getMonthPosition(position);
                            if (monthPosition >= 0)
                                recyclerView.smoothScrollToPosition(monthPosition);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                    // Adiciona as abas
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(viewPager);

                    // Configura o adapter para a lista de datas
                    datasAdapter = new DatasAdapter(calendarioAcademico, nomesMeses, new DatasAdapter.OnDataSelecionadaListener() {
                        @Override
                        public void onDataSelecionada(View viewMenu, final Data data) {
                            // Exibe um menu para criar lembrete
                            final PopupMenu menu = new PopupMenu(CalendarioActivity.this, viewMenu);
                            menu.inflate(R.menu.date_item);
                            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    if (menuItem.getItemId() == R.id.create_reminder) {
                                        // Envia um novo lembrete para o Google Calendar
                                        Intent intent = new Intent(Intent.ACTION_EDIT)
                                                .setData(CalendarContract.Events.CONTENT_URI)
                                                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                                                .putExtra(CalendarContract.Events.TITLE, data.getTitulo());

                                        Calendar cal = Calendar.getInstance();
                                        cal.set(Calendar.DAY_OF_MONTH, data.getDiaInicio());
                                        cal.set(Calendar.MONTH, data.getMes());

                                        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());

                                        if (data.getDiaTermino() != -1)
                                            cal.set(Calendar.DAY_OF_MONTH, data.getDiaTermino());
                                        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis());

                                        startActivityForResult(intent, COD_REQ_LEMBRETE);
                                    }
                                    return true;
                                }
                            });
                            menu.show();
                        }
                    });

                    // Configura a lista de datas
                    LinearLayoutManager layoutManager = new LinearLayoutManager(CalendarioActivity.this);
                    recyclerView = (RecyclerView) findViewById(R.id.dates_list);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(datasAdapter);
                } else {
                    //todo mostrar mensagem de erro
                }
            }
        }).execute(2015);
    }

    @Override
    public List<Data> getDatas(int mes) {
        if (calendarioAcademico == null)
            return null;
        return calendarioAcademico.getDatasPorMes(mes);
    }

    @Override
    public void onDiaClick(Data data) {
        // Rola até a data do dia
        recyclerView.smoothScrollToPosition(datasAdapter.getPosicaoData(data));
    }
}

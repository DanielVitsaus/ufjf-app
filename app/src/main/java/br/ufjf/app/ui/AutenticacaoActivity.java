package br.ufjf.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.ufjf.app.async.SignInTask;
import br.ufjf.app.model.Estudante;
import br.ufjf.dcc.pesquisa.R;

/**
 * Permite o login do estudante
 * Created by Jorge Augusto da Silva Moreira on 12/06/2015.
 */
public class AutenticacaoActivity extends ToolbarActivity {
    private EditText campoEmail;
    private EditText campoSenha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        inicializarToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        campoEmail = (EditText) findViewById(R.id.auth_email);
        campoSenha = (EditText) findViewById(R.id.auth_password);
    }

    public void entrar(final View view) {
        view.setEnabled(false);
        new SignInTask(this, new SignInTask.Callback() {
            @Override
            public void onFinish(Estudante estudante) {
                if (estudante != null) {
                    Toast.makeText(AutenticacaoActivity.this, R.string.success_signin, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, new Intent().putExtra("name", estudante.getNome()).putExtra("course", estudante.getCurso()));
                    finish();
                } else {
                    Toast.makeText(AutenticacaoActivity.this, R.string.wrong_credentials, Toast.LENGTH_SHORT).show();
                    view.setEnabled(true);
                }
            }
        }).execute(campoEmail.getText().toString(), campoSenha.getText().toString());
    }
}

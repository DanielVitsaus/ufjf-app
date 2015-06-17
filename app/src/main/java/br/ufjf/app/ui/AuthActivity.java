package br.ufjf.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.ufjf.app.async.SignInTask;
import br.ufjf.app.model.Student;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 12/06/2015.
 */
public class AuthActivity extends ToolbarActivity {
    private EditText mEmailField;
    private EditText mPasswordField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initializeToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEmailField = (EditText) findViewById(R.id.auth_email);
        mPasswordField = (EditText) findViewById(R.id.auth_password);
    }

    public void signIn(final View view) {
        view.setEnabled(false);
        new SignInTask(this, new SignInTask.Callback() {
            @Override
            public void onFinish(Student student) {
                if (student != null) {
                    Toast.makeText(AuthActivity.this, R.string.success_signin, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, new Intent().putExtra("name", student.getName()).putExtra("email", student.getEmail()));
                    finish();
                } else {
                    Toast.makeText(AuthActivity.this, R.string.wrong_credentials, Toast.LENGTH_SHORT).show();
                    view.setEnabled(true);
                }
            }
        }).execute(mEmailField.getText().toString(), mPasswordField.getText().toString());
    }
}

package br.ufjf.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import br.ufjf.app.model.Aluno;

/**
 * Recupera e salva informações do aluno nas preferências do app
 * Created by Jorge Augusto da Silva Moreira on 12/06/2015.
 */
public class AutenticacaoHelper {
    private static final String PREF_ID = "aluno_id";
    private static final String PREF_EMAIL = "aluno_email";
    private static final String PREF_NOME = "aluno_nome";
    private static final String PREF_CURSO = "aluno_curso";

    /**
     * Tenta ler as informaçoes do aluno.
     *
     * @param context
     * @return Aluno cadastrado. Caso nao esteja cadastrado, retorna null
     * @throws AlunoNaoAutenticado
     */
    public static Aluno obterAluno(Context context) throws AlunoNaoAutenticado {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String id = sharedPreferences.getString(PREF_ID, null);

        if (id == null)
            throw new AlunoNaoAutenticado();

        return new Aluno(
                id,
                sharedPreferences.getString(PREF_NOME, null),
                sharedPreferences.getString(PREF_EMAIL, null),
                sharedPreferences.getString(PREF_CURSO, null)
        );
    }

    /**
     * Registra informaçoes do aluno
     *
     * @param context
     * @param aluno   Aluno autenticado
     */
    public static void registrarLogin(Context context, Aluno aluno) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(PREF_ID, aluno.getId())
                .putString(PREF_EMAIL, aluno.getEmail())
                .putString(PREF_NOME, aluno.getNome())
                .putString(PREF_CURSO, aluno.getCurso())
                .commit();
    }

    /**
     * Limpa as informaçoes do aluno
     *
     * @param context
     */
    public static void registrarLogout(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .remove(PREF_ID)
                .remove(PREF_EMAIL)
                .remove(PREF_NOME)
                .remove(PREF_CURSO)
                .commit();
    }

    /**
     * Indica que a operacao nao pode ser efetua porque nao ha um aluno autenticado
     */
    public static class AlunoNaoAutenticado extends Exception {
        public AlunoNaoAutenticado() {
            super("O aluno não está autenticado.");
        }
    }
}

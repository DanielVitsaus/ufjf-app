package br.ufjf.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import br.ufjf.app.model.Estudante;

/**
 * Recupera e salva informações do estudante nas preferências do app
 * Created by Jorge Augusto da Silva Moreira on 12/06/2015.
 */
public class AutenticacaoHelper {
    private static final String PREF_ID = "estudante_id";
    private static final String PREF_EMAIL = "estudante_email";
    private static final String PREF_NOME = "estudante_nome";
    private static final String PREF_CURSO = "estudante_curso";

    public static Estudante getStudent(Context context) throws StudentNaoAutenticado {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String id = sharedPreferences.getString(PREF_ID, null);

        if (id == null)
            throw new StudentNaoAutenticado();

        return new Estudante(
                id,
                sharedPreferences.getString(PREF_NOME, null),
                sharedPreferences.getString(PREF_EMAIL, null),
                sharedPreferences.getString(PREF_CURSO, null)
        );
    }

    public static void registerLogin(Context context, Estudante estudante) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(PREF_ID, estudante.getId())
                .putString(PREF_EMAIL, estudante.getEmail())
                .putString(PREF_NOME, estudante.getNome())
                .putString(PREF_CURSO, estudante.getCurso())
                .commit();
    }

    public static void registerLogout(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .remove(PREF_ID)
                .remove(PREF_EMAIL)
                .remove(PREF_NOME)
                .remove(PREF_CURSO)
                .commit();
    }

    public static class StudentNaoAutenticado extends Exception {
        public StudentNaoAutenticado() {
            super("O estudante não está autenticado.");
        }
    }
}

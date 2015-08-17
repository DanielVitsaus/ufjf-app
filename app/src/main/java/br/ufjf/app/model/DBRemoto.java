package br.ufjf.app.model;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class DBRemoto {
    public interface TipoPergunta {
        int TEXTO = 0;
        int OPCAO = 1;
        int ESCALA = 2;
    }

    public interface VisibilidadeQuestionario {
        int CLASSE = 0;
        int ASSUNTO = 1;
        int CURSO = 2;
        int DEPARTAMENTO = 3;
        int PUBLICO = 4;
    }

    public interface Estudante {
        String ID = "_id";
        String NOME = "name";
        String EMAIL = "email";
        String SENHA = "password";
        String CURSO = "course";
        String RESPOSTAS = "survey_answers";
    }

    public interface Questionario {
        String ID = "_id";
        String TITULO = "title";
        String DESCRICAO = "description";
        String PERGUNTAS = "questions";
        String VISIBILIDADE = "visibility";

        interface Pergunta {
            String TITULO = "title";
            String TIPO = "type";

            String UMA_LINHA = "single_line";

            String OPCOES = "options";

            String OPCAO_UNICA = "single_choice";

            String MIN = "min";
            String MAX = "max";
        }

        interface Visibilidade {
            String TIPO = "type_";
        }
    }

    public interface Resposta {
        String QUESTIONARIO = "survey";
        String ESTUDANTE = "student";
        String ITENS = "items";

        interface Item {
            String TIPO = "type";
            String TEXTO = "text";
            String VALOR = "value";
            String OPCOES = "choices";
        }
    }

    public interface Data {
        String MES = "month";
        String DIA_INICIAL = "day_start";
        String DAY_TERMINO = "day_end";
        String TITULO = "title";
    }
}

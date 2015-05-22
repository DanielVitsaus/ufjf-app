package br.ufjf.app.model;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class ServerDB {
    public interface QuestionTypes {
        int SIMPLE = 0;
        int ALTERNATIVE_UNIQUE = 1;
        int ALTERNATIVE_COMPOSITE = 2;
        int RATE = 3;
        int VARIATION = 4;
    }

    public interface Survey {
        String TITLE = "title";
        String DESCRIPTION = "description";
        String QUESTIONS = "questions";

        interface Question {
            String TITLE = "title";
            String TYPE = "type";

            String ANSWER = "answer";

            String SINGLE_LINE = "single_line";

            String OPTIONS = "alternatives";

            String MIN = "min";
            String MAX = "max";

            String RATE = "rate";
        }
    }
}

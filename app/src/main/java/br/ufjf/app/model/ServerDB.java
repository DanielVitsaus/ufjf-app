package br.ufjf.app.model;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class ServerDB {
    public interface QuestionTypes {
        int SIMPLE = 0;
        int CHOICE = 1;
        int SCALE = 2;
    }

    public interface Survey {
        String ID = "_id";
        String TITLE = "title";
        String DESCRIPTION = "description";
        String QUESTIONS = "questions";

        interface Question {
            String TITLE = "title";
            String TYPE = "type";

            String SINGLE_LINE = "single_line";

            String OPTIONS = "options";

            String SINGLE_CHOICE = "single_choice";

            String MIN = "min";
            String MAX = "max";
        }
    }
}

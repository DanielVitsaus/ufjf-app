package br.ufjf.app.ui.question;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import br.ufjf.app.model.survey.Question;
import br.ufjf.app.model.survey.ScaleQuestion;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class ScaleQuestionFragment extends QuestionFragment {
    private ScaleQuestion mQuestion;
    private Spinner mSpinner;

    public static ScaleQuestionFragment newInstance(int questionIndex) {
        ScaleQuestionFragment fragment = new ScaleQuestionFragment();
        setupNewInstance(fragment, questionIndex);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = (ScaleQuestion) mListener.getQuestion(getQuestionIndex());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mSpinner = (Spinner) view.findViewById(R.id.question_scale);

        Integer[] values = new Integer[mQuestion.getMax() - mQuestion.getMin()];
        for (int i = 0; i < values.length; i++)
            values[i] = i + mQuestion.getMin();

        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(mSpinner.getContext(), android.R.layout.simple_spinner_dropdown_item, values);
        mSpinner.setAdapter(spinnerAdapter);

        return view;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_q_scale;
    }

    @Override
    protected Question getQuestion() {
        return mQuestion;
    }

    @Override
    protected void reportAnswer() {
        ((Listener) mListener).registerAnswer(mSpinner.getSelectedItemPosition(), getQuestionIndex());
    }

    public interface Listener extends QuestionFragment.Listener {
        void registerAnswer(int answer, int questionIndex);
    }
}

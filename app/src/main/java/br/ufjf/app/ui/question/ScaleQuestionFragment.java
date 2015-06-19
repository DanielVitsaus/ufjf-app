package br.ufjf.app.ui.question;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import br.ufjf.app.model.survey.Answer;
import br.ufjf.app.model.survey.Question;
import br.ufjf.app.model.survey.ScaleQuestion;
import br.ufjf.app.model.survey.ValueAnswer;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class ScaleQuestionFragment extends QuestionFragment {
    private ScaleQuestion mQuestion;
    private ValueAnswer mAnswer;
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
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mAnswer == null)
                    mAnswer = new ValueAnswer(i);
                else
                    mAnswer.setAnswer(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
    protected Answer getAnswer() {
        return mAnswer;
    }

    @Override
    protected void updateUI(Answer answer) {
        mSpinner.setSelection(((ValueAnswer) answer).getAnswer());
    }
}

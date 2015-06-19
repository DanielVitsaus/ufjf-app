package br.ufjf.app.ui.question;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import br.ufjf.app.model.survey.Answer;
import br.ufjf.app.model.survey.ChoiceQuestion;
import br.ufjf.app.model.survey.Question;
import br.ufjf.app.model.survey.ValueAnswer;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class SingleChoiceQuestionFragment extends QuestionFragment {
    private ChoiceQuestion mQuestion;
    private ValueAnswer mAnswer;
    private RadioGroup mRadioGroup;

    public static SingleChoiceQuestionFragment newInstance(int questionIndex) {
        SingleChoiceQuestionFragment fragment = new SingleChoiceQuestionFragment();
        setupNewInstance(fragment, questionIndex);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = (ChoiceQuestion) mListener.getQuestion(getQuestionIndex());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mRadioGroup = (RadioGroup) view.findViewById(R.id.choices);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                //todo salvar posição, não id do View
                if (mAnswer == null)
                    mAnswer = new ValueAnswer(id);
                else
                    mAnswer.setAnswer(id);
            }
        });

        for (String option : mQuestion.getOptions()) {
            RadioButton radioButton = new RadioButton(mRadioGroup.getContext());
            radioButton.setText(option);
            mRadioGroup.addView(radioButton);
        }

        return view;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_q_single_choice;
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
        int index = (((ValueAnswer) answer).getAnswer());
        RadioButton radioButton = (RadioButton) mRadioGroup.getChildAt(index);
        radioButton.setChecked(true);
    }
}

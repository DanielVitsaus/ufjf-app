package br.ufjf.app.ui.question;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import br.ufjf.app.model.survey.Question;
import br.ufjf.app.model.survey.ScaleQuestion;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class ScaleQuestionFragment extends QuestionFragment {
    private ScaleQuestion mQuestion;

    private TextView mLabelTextView;

    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean user) {
            mLabelTextView.setText(getString(R.string.question_scale_label, progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public static ScaleQuestionFragment newInstance(int questionIndex) {
        ScaleQuestionFragment fragment = new ScaleQuestionFragment();
        setupNewInstance(fragment, questionIndex);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mQuestion = (ScaleQuestion) mListener.getQuestion(getQuestionIndex());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        ((SeekBar) view.findViewById(R.id.question_scale)).setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mLabelTextView = (TextView) view.findViewById(R.id.question_label);
        mLabelTextView.setText(getString(R.string.question_scale_label, mQuestion.getMin()));

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
}

package br.ufjf.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 17/06/2015.
 */
public class SurveySubmitFragment extends Fragment {
    private Listener mListener;
    private TextView mMessageView;
    private Button mSubmitButton;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (Listener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_submit, container, false);

        mMessageView = (TextView) view.findViewById(R.id.survey_submit_msg);
        mSubmitButton = (Button) view.findViewById(R.id.survey_submit_btn);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.submitAnswers();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mListener.isReadyToSubmit()){
            mMessageView.setText(R.string.all_questions_answered);
            mSubmitButton.setEnabled(true);
        } else {
            mMessageView.setText(R.string.not_all_questions_answered);
            mSubmitButton.setEnabled(false);
        }
    }

    public interface Listener {
        boolean isReadyToSubmit();
        void submitAnswers();
    }
}

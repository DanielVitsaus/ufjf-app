package br.ufjf.app.ui.question;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufjf.app.model.survey.ChoiceQuestion;
import br.ufjf.app.model.survey.Question;
import br.ufjf.app.ui.adapter.ChoicesAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class ChoiceQuestionFragment extends QuestionFragment {
    ChoiceQuestion mQuestion;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    public static ChoiceQuestionFragment newInstance(int questionIndex){
        ChoiceQuestionFragment fragment = new ChoiceQuestionFragment();
        setupNewInstance(fragment, questionIndex);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mQuestion = (ChoiceQuestion) mListener.getQuestion(getQuestionIndex());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_choices);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new ChoicesAdapter(mQuestion.isSingleChoice(), mQuestion.getOptions()));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_q_choice;
    }

    @Override
    protected Question getQuestion() {
        return mQuestion;
    }
}

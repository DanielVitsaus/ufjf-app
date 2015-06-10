package br.ufjf.app.ui.question;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
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
    private ChoiceQuestion mQuestion;
    private RecyclerView mRecyclerView;
    private ChoicesAdapter mAdapter;

    public static ChoiceQuestionFragment newInstance(int questionIndex) {
        ChoiceQuestionFragment fragment = new ChoiceQuestionFragment();
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_choices);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new ChoicesAdapter(mQuestion.isSingleChoice(), mQuestion.getOptions());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_q_choice;
    }

    @Override
    protected Question getQuestion() {
        return mQuestion;
    }

    @Override
    protected void reportAnswer() {
        ((Listener) mListener).registerAnswer(mAdapter.getSelected(), getQuestionIndex());
    }

    public interface Listener extends QuestionFragment.Listener {
        void registerAnswer(SparseBooleanArray choices, int questionIndex);
    }
}

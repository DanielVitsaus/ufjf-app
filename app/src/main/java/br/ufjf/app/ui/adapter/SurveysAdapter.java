package br.ufjf.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Set;
import java.util.TreeSet;

import br.ufjf.app.model.ServerDB;
import br.ufjf.app.model.survey.Survey;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class SurveysAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Survey[] mSurveys;
    private final Set<Integer> mHeadersPositions;
    private final OnSurveyClickListener mListener;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_SURVEY = 1;

    public SurveysAdapter(Survey[] surveys, OnSurveyClickListener listener) {
        mSurveys = surveys;
        mListener = listener;
        mHeadersPositions = new TreeSet<>();
        mHeadersPositions.add(0);
        for (int i = 1; i < mSurveys.length; i++)
            if (mSurveys[i - 1].getVisibility() != mSurveys[i].getVisibility())
                mHeadersPositions.add(i + mHeadersPositions.size());
    }

    public interface OnSurveyClickListener {
        void onSurveyClick(Survey survey);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER)
            return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header, parent, false)) {
            };
        else
            return new SurveyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_survey, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SurveyHolder) {
            int surveyIndex = getSurveyIndex(position);
            SurveyHolder surveyHolder = (SurveyHolder) holder;
            surveyHolder.title.setText(mSurveys[surveyIndex].getTitle());
            surveyHolder.description.setText(mSurveys[surveyIndex].getDescription());
        } else {
            int visibility = mSurveys[getSurveyIndex(position + 1)].getVisibility();
            String header;
            switch (visibility) {
                case ServerDB.SurveyVisibility.CLASS:
                    header = "Turma";
                    break;
                case ServerDB.SurveyVisibility.SUBJECT:
                    header = "Disciplina";
                    break;
                case ServerDB.SurveyVisibility.COURSE:
                    header = "Curso";
                    break;
                case ServerDB.SurveyVisibility.DEPARTMENT:
                    header = "Departamento";
                    break;
                default:
                    header = "Público";
            }
            ((TextView) holder.itemView).setText(header);
        }
    }

    private int getSurveyIndex(int position) {
        int index = position;
        for (Integer headerPosition : mHeadersPositions)
            if (headerPosition >= position)
                break;
            else index--;
        return index;
    }

    @Override
    public int getItemCount() {
        if (mSurveys != null && mSurveys.length > 0)
            return mHeadersPositions.size() + mSurveys.length;
        else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mHeadersPositions.contains(position) ? TYPE_HEADER : TYPE_SURVEY;
    }

    protected class SurveyHolder extends RecyclerView.ViewHolder {
        TextView title, description;

        public SurveyHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.survey_title);
            description = (TextView) itemView.findViewById(R.id.survey_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onSurveyClick(mSurveys[getSurveyIndex(getPosition())]);
                }
            });
        }
    }
}

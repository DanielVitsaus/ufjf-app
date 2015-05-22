package br.ufjf.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufjf.app.model.survey.Survey;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class SurveysAdapter extends RecyclerView.Adapter<SurveysAdapter.SurveyHolder> {
    private final Survey[] mSurveys;
    private final OnSurveyClickListener mListener;

    public SurveysAdapter(Survey[] surveys, OnSurveyClickListener listener){
        mSurveys = surveys;
        mListener = listener;
    }

    public interface OnSurveyClickListener {
        void onSurveyClick(Survey survey);
    }

    @Override
    public SurveysAdapter.SurveyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SurveyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_survey, parent, false));
    }

    @Override
    public void onBindViewHolder(SurveysAdapter.SurveyHolder holder, int position) {
        holder.title.setText(mSurveys[position].getTitle());
        holder.description.setText(mSurveys[position].getDescription());
    }

    @Override
    public int getItemCount() {
        return mSurveys.length;
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
                    mListener.onSurveyClick(mSurveys[getPosition()]);
                }
            });
        }
    }
}

package br.ufjf.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class ChoicesAdapter extends RecyclerView.Adapter<ChoicesAdapter.ChoiceHolder> {

    private final boolean mSingleChoice;
    private final String[] mOptions;
    private final OnSelectionChangedListener mListener;
    private boolean[] mSelected;

    public ChoicesAdapter(boolean singleChoice, String[] options, OnSelectionChangedListener listener) {
        mSingleChoice = singleChoice;
        mOptions = options;
        mListener = listener;
        mSelected = new boolean[mOptions.length];
    }

    public void notifySelectionChanged(boolean[] selected) {
        mSelected = selected;
        notifyDataSetChanged();
    }

    @Override
    public ChoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if (mSingleChoice)
            layout = R.layout.list_item_single_choice;
        else
            layout = R.layout.list_item_multiple_choice;

        return new ChoiceHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public int getItemCount() {
        return mOptions.length;
    }

    @Override
    public void onBindViewHolder(ChoiceHolder holder, int position) {
        holder.choice.setText(mOptions[position]);
        holder.choice.setChecked(mSelected[position]);
    }

    public interface OnSelectionChangedListener {
        void onSelectionChanged(boolean[] selected);
    }

    protected class ChoiceHolder extends RecyclerView.ViewHolder {
        CompoundButton choice;

        public ChoiceHolder(View itemView) {
            super(itemView);
            choice = (CompoundButton) itemView.findViewById(R.id.choice);

            choice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    mSelected[getAdapterPosition()] = checked;
                    mListener.onSelectionChanged(mSelected);
                }
            });
        }
    }

}

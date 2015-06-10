package br.ufjf.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class ChoicesAdapter extends RecyclerView.Adapter<ChoicesAdapter.ChoiceHolder> {

    private static final int TYPE_SELECTED = 0;
    private static final int TYPE_UNSELECTED = 1;
    private final boolean mSingleChoice;
    private final String[] mOptions;
    private int mSelectedOption;
    private SparseBooleanArray mSelected;

    public ChoicesAdapter(boolean singleChoice, String[] options) {
        mSingleChoice = singleChoice;
        mOptions = options;
        mSelected = new SparseBooleanArray();
    }

    public SparseBooleanArray getSelected() {
        return mSelected;
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
    public int getItemViewType(int position) {
        if (mSingleChoice)
            return mSelectedOption == position ? TYPE_SELECTED : TYPE_UNSELECTED;
        else return -1;
    }

    @Override
    public void onBindViewHolder(ChoiceHolder holder, int position) {
        holder.choice.setText(mOptions[position]);
        holder.position = position;

        if (mSingleChoice && getItemViewType(position) == TYPE_UNSELECTED)
            holder.choice.setChecked(false);
    }

    protected class ChoiceHolder extends RecyclerView.ViewHolder {
        CompoundButton choice;
        int position;

        public ChoiceHolder(View itemView) {
            super(itemView);
            choice = (CompoundButton) itemView.findViewById(R.id.choice);

            choice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    mSelected.put(getAdapterPosition(), checked);
                    if (checked)
                        if (mSingleChoice) {
                            int oldSelectedOption = mSelectedOption;
                            mSelectedOption = position;
                            try {
                                notifyItemChanged(oldSelectedOption);
                            } catch (IllegalStateException ignored) {

                            }
                        }
                }
            });
        }
    }

}

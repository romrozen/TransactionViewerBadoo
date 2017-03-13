package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.badoo.roman.badootransactionviewer.R;

import java.util.ArrayList;
import java.util.Locale;

import model.TransactionMeta;

/**
 * Created by Roman on 13-Mar-17.
 */

public class TransactionsRecyclerViewAdapter extends RecyclerView.Adapter<TransactionsRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<TransactionMeta> mValues;

    public TransactionsRecyclerViewAdapter(ArrayList<TransactionMeta> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mainTV.setText(String.format(Locale.getDefault(),"%s(%s)",mValues.get(position).getOriginalAmount(),mValues.get(position).getCurrency()));
        holder.secondaryTV.setText(String.format(Locale.getDefault(),"%.2f",mValues.get(position).getConvertedAmount()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mainTV;
        TextView secondaryTV;
        TransactionMeta mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mainTV = (TextView) view.findViewById(R.id.main_tv);
            secondaryTV = (TextView) view.findViewById(R.id.secondary_tv);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + secondaryTV.getText() + "'";
        }
    }
}

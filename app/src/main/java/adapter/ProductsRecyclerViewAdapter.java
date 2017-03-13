package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.badoo.roman.badootransactionviewer.R;

import java.util.ArrayList;
import java.util.Locale;

import fragment.ProductsFragment;
import model.SkuOverview;

/**
 * Created by Roman on 13-Mar-17.
 */

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<SkuOverview> mValues;
    private final ProductsFragment.OnListFragmentInteractionListener mListener;

    public ProductsRecyclerViewAdapter(ArrayList<SkuOverview> items, ProductsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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
        holder.mainTV.setText(String.format(Locale.getDefault(),"%s",mValues.get(position).getSku()));
        holder.secondaryTV.setText(String.format(Locale.getDefault(),"%d transactions",mValues.get(position).getNumberOfTransactions()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onProductSelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mainTV;
        final TextView secondaryTV;
        SkuOverview mItem;

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

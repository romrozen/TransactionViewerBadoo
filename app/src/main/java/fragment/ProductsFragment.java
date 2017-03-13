package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badoo.roman.badootransactionviewer.R;

import java.util.ArrayList;

import adapter.ProductsRecyclerViewAdapter;
import model.SkuOverview;

/**
 * Created by Roman on 13-Mar-17.
 */

public class ProductsFragment extends Fragment {

    private static final String ARG_PRODUCTS = "arg_products";
    private OnListFragmentInteractionListener mListener;
    private ArrayList<SkuOverview> skuOverviewArrayList;

    public ProductsFragment() {
    }

    public static ProductsFragment newInstance(ArrayList<SkuOverview> skuOverviewArrayList) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PRODUCTS, skuOverviewArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            skuOverviewArrayList = getArguments().getParcelableArrayList(ARG_PRODUCTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(new ProductsRecyclerViewAdapter(skuOverviewArrayList, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onProductSelected(SkuOverview item);
    }
}


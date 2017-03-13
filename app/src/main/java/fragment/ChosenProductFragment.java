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

import adapter.TransactionsRecyclerViewAdapter;
import model.TransactionMeta;

/**
 * Created by Roman on 13-Mar-17.
 */

public class ChosenProductFragment extends Fragment {

    private static final String ARG_PRODUCT = "arg_product";
    private ArrayList<TransactionMeta> productMetaArrayList;
    private onFragmentDetachedListener mListener;

    public ChosenProductFragment() {
    }

    public static ChosenProductFragment newInstance(ArrayList<TransactionMeta> productMetaArrayList) {
        ChosenProductFragment fragment = new ChosenProductFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PRODUCT, productMetaArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            productMetaArrayList = getArguments().getParcelableArrayList(ARG_PRODUCT);
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

            recyclerView.setAdapter(new TransactionsRecyclerViewAdapter(productMetaArrayList));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentDetachedListener) {
            mListener = (onFragmentDetachedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onFragmentDetachedListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.onFragmentDetach();
        mListener = null;
    }

    public interface onFragmentDetachedListener {
        void onFragmentDetach();
    }
}

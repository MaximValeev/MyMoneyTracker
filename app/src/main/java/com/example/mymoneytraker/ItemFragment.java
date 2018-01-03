package com.example.mymoneytraker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ItemFragment extends Fragment {

    private static final String TAG = "ItemFragment";

    private static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_EXPENSES = 0;
    public static final int TYPE_INCOME = 1;

    private static final String KEY_TYPE = "TYPE";

    private int type = TYPE_EXPENSES;

    public static ItemFragment createItemFragment(int type){
        ItemFragment fragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ItemFragment.KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(new ItemsAdapter(getContext()));

        type = getArguments().getInt(KEY_TYPE, TYPE_UNKNOWN);

        if(type == TYPE_UNKNOWN){
            throw new IllegalStateException("Unknown type");
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: ");
    }
}

package com.example.mymoneytraker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

//import com.example.mymoneytraker.api.Api;

public class ItemsFragment extends Fragment {

    private static final String TAG = "ItemFragment";

    private static final int LOADER_ITEMS = 0;
    private static final int LOADER_ADD = 1;
    private static final int LOADER_REMOVE = 2;

    private static final String KEY_TYPE = "TYPE";
    private String type = Item.TYPE_UNKNOWN;
    private static final String ACTION_MODE_KEY_STATE = "actionModeState";
    private static final String ACTION_MODE_SELECTED = "some selected";

    private DatabaseReference myRef;
    private String uId;

    private ItemsAdapter adapter;
//    private Api api;

    ActionMode actionMode;
    FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefresh;

    public static ItemsFragment createItemFragment(String type){
        ItemsFragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ItemsFragment.KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if(mAuth.getCurrentUser() != null){
            uId = mAuth.getCurrentUser().getUid();
        }
        myRef = database.getReference().child("users").child(uId);

        type = getArguments().getString(KEY_TYPE, Item.TYPE_UNKNOWN);
        if(type.equals(Item.TYPE_UNKNOWN)){
            throw new IllegalStateException("Unknown type");
        }

        adapter = new ItemsAdapter();
//        api = ((App) getActivity().getApplication()).getApi();

    }

    //TODO: actionMode when fragment change
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(actionMode != null){
            outState.putBoolean(ACTION_MODE_KEY_STATE, true);
            outState.putIntegerArrayList(ACTION_MODE_SELECTED, (ArrayList<Integer>)adapter.getSelectedItems());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.getBoolean(ACTION_MODE_KEY_STATE)) {
            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);

            ArrayList<Integer> list = savedInstanceState.getIntegerArrayList(ACTION_MODE_SELECTED);
            if(list != null){
                for(int i : list){
                    adapter.toggleSelection(i);
                }
            }

        }
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
        recycler.setAdapter(adapter);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

        adapter.setListener(new ItemsAdapterListener() {
            @Override
            public void onItemClick(Item item, int position) {
                Log.d(TAG, "onItemClick: name = " + item.name + "position = " + position);
                if (isInActionMode()) {
                    toggleSelection(position);
                }
            }

            @Override
            public void onItemLongClick(Item item, int position) {
                Log.d(TAG, "onItemLongClick: name = " + item.name + "position = " + position);
                if(isInActionMode()){
                    return;
                }

                actionMode = ((AppCompatActivity)getActivity()).startSupportActionMode(actionModeCallback);
                toggleSelection(position);
            }

            private void toggleSelection(int position){
                adapter.toggleSelection(position);
                actionMode.setTitle(getString(R.string.selected_title_action_mod) + adapter.getSelectedItemsCount());
            }

            boolean isInActionMode(){
                return actionMode != null;
            }
        });

        fab = view.findViewById(R.id.fab_add);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getActivity(), AddActivity.class);
                intent.putExtra(AddActivity.EXTRA_TYPE, type);
                startActivityForResult(intent, AddActivity.RC_ITEM_ADD);
            }
        });

        loadItems();
    }

    private void loadItems(){
        Query myQuery = myRef.orderByChild("type").equalTo(type);
        myQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Item> itemsList = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String key = snapshot.getKey();
                    Item item = snapshot.getValue(Item.class);
                    if(item != null)item.setId(key);
                    itemsList.add(item);
                }
                adapter.setItems(itemsList);
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void addItem(final Item item){
        Log.d(TAG, "addItem: "+item.name + " " + item.price);
        myRef.push().setValue(item);
    }

    private void removeItem(List<Item> itemsList) {
        for(Item item: itemsList) myRef.child(item.id).removeValue();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getFragmentManager().popBackStack();
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AddActivity.RC_ITEM_ADD && resultCode == RESULT_OK){
            Item item = (Item) data.getSerializableExtra(AddActivity.RESULT_ITEM);
            addItem(item);
            Toast.makeText(getContext(), item.name + " " + item.price + " " + item.type, Toast.LENGTH_SHORT).show();
        }
    }

    private void removeSelectedItems(){
        List<Item> items = new ArrayList<>();
        items.clear();
        for (int i = adapter.getSelectedItems().size() -1; i >= 0; i--){
           items.add(adapter.remove(adapter.getSelectedItems().get(i)));
        }
        removeItem(items);
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            actionMode = mode;
            actionMode.getMenuInflater().inflate(R.menu.items_menu, menu);
            fab.setVisibility(View.GONE);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_remove:
                    showDialog();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            fab.setVisibility(View.VISIBLE);
            adapter.clearSelections();
        }
    };

    private void showDialog(){
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.setDialogListener(new ConfirmationDialogListener() {
            @Override
            public void onPositiveClick() {
                removeSelectedItems();
                actionMode.finish();
            }

            @Override
            public void onDismissClick() {
                actionMode.finish();
            }
        });
        dialog.show(getFragmentManager(), "Confirmation");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(actionMode != null){
            actionMode.finish();
        }
    }

    private void showError(String error){
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
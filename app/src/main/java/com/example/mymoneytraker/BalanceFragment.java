package com.example.mymoneytraker;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class BalanceFragment extends Fragment {

    private TextView balance;
    private TextView income;
    private TextView expense;
    private DiagramView diagram;
    private DatabaseReference mRef;

    public static BalanceFragment createFragment(){
        return new BalanceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("users").child(mAuth.getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        diagram = view.findViewById(R.id.diagram);
        balance = view.findViewById(R.id.balance);
        income = view.findViewById(R.id.income);
        expense = view.findViewById(R.id.expense);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    private void updateData() {
        Query mQuery = mRef;
        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int totalExpense = 0;
                int totalIncome = 0;
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Item item = snapshot.getValue(Item.class);
                    if(item!= null && (item.type).equals("income")){
                        totalIncome += item.price;
                    } else if (item != null && (item.type).equals("expense")){
                        totalExpense += item.price;
                    }
                }
                    if (isAdded()){
                    balance.setText(getString(R.string.price, totalIncome - totalExpense));
                    income.setText(getString(R.string.price, totalIncome));
                    expense.setText(getString(R.string.price, totalExpense));
                    diagram.update(totalExpense, totalIncome);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

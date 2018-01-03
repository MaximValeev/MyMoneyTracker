package com.example.mymoneytraker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private static final String TAG = "ItemsAdapter";

    private Context context;
    private List<Item> items = new ArrayList<>();

    ItemsAdapter(Context context) {
        this.context = context;
        items.add(new Item("Moloko", 35));
        items.add(new Item("game", 3500));
        items.add(new Item("way", 3500));
        items.add(new Item("goods", 10000));
        items.add(new Item("Moloko", 35));
        items.add(new Item("game", 3500));
        items.add(new Item("way", 3500));
        items.add(new Item("goods", 10000));
        items.add(new Item("Moloko", 35));
        items.add(new Item("game", 3500));
        items.add(new Item("way", 3500));
        items.add(new Item("goods", 10000));
        items.add(new Item("Moloko", 35));
        items.add(new Item("game", 3500));
        items.add(new Item("way", 3500));
        items.add(new Item("goods", 10000));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        Item item = items.get(position);
        holder.bind(item, context);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView price;

        ItemViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.itemPrice);

        }

        void bind(Item item, Context context){
            Spannable priceText = new SpannableString(String.valueOf(item.getPrice()) + context.getResources().getString(R.string.currencySymbol));
            priceText.setSpan(new RelativeSizeSpan(0.75f), priceText.length()-1, priceText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            price.setText(priceText);
            name.setText(item.getName());
        }
    }
}

package com.example.mymoneytraker;

import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private static final String TAG = "ItemsAdapter";

    private List<Item> items = new ArrayList<>();
    private ItemsAdapterListener listener = null;

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setListener(ItemsAdapterListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item, position, listener);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView price;

        ItemViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.itemPrice);

        }

        void bind(final Item item, final int position, final ItemsAdapterListener listener){
            Spannable priceText = new SpannableString(String.valueOf(item.price) + itemView.getResources().getString(R.string.currencySymbol));
            priceText.setSpan(new RelativeSizeSpan(0.75f), priceText.length()-1, priceText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            price.setText(priceText);
            name.setText(item.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(item, position);
                    return true;
                }
            });
        }
    }
}

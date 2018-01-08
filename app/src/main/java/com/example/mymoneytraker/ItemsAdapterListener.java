package com.example.mymoneytraker;

public interface ItemsAdapterListener {

    void onItemClick(Item item, int position);

    void onItemLongClick(Item item, int position);
}

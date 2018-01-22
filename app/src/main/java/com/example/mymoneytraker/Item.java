package com.example.mymoneytraker;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Item implements Serializable {
    static final String TYPE_UNKNOWN = "unknown";
    static final String TYPE_EXPENSES = "expense";
    static final String TYPE_INCOME = "income";

    public String id;
    public int price;
    public String name, type;

    Item(String name, int price, String type) {
        this.price = price;
        this.name = name;
        this.type = type;
    }

    public Item() {
    }
    //    int getPrice() {
//        return price;
//    }
//
//    void setPrice(int price) {
//        this.price = price;
//    }
//
//    String getName() {
//        return name;
//    }
//
//    void setName(String name) {
//        this.name = name;
//    }
    void setId(String id){
        this.id = id;
    }
}

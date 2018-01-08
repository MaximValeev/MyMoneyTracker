package com.example.mymoneytraker;

import java.io.Serializable;

public class Item implements Serializable {
    static final String TYPE_UNKNOWN = "unknown";
    static final String TYPE_EXPENSES = "expense";
    static final String TYPE_INCOME = "income";

    public int id = -1;
    int price;
    String name, type;

    Item(String name, int price, String type) {
        this.price = price;
        this.name = name;
        this.type = type;
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
}

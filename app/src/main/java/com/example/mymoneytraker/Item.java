package com.example.mymoneytraker;

class Item {

    private int price;
    private String name;

    Item(String name, int price) {
        this.price = price;
        this.name = name;
    }

    int getPrice() {
        return price;
    }

    void setPrice(int price) {
        this.price = price;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}

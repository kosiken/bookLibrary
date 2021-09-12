package com.allisonkosy;

public class Book {
    private int stock;
    private final String name;

    private final String excerpt;


    public Book(String name, String excerpt) {
        this.name = name;
        this.excerpt = excerpt;
        stock = 0;
    }

    void add() {
        stock++;
    }

    void borrow() {
        stock--;
    }

    void setStock(int s) {
        stock =s;
    }

    public int getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    boolean isAvailable () {return  stock > 0;}
}

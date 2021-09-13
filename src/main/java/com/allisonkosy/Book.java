package com.allisonkosy;

public class Book {
    private int stock; // amount of copies of book available
    private final String name; // name of the book

    private final String excerpt; // excerpt of the book


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

    /**
     * Used to check if the book is available
     * @return true if there it is
     */
    boolean isAvailable () {return  stock > 0;}
}

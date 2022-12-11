package com.example.testactivity;

public class Book {

    byte[] bookIm;
    String bookName;
    String authorName;
    int price;
    int quantity;

    Book(byte[] bookIm, String bookName, String authorName, int price){
        this.bookIm = bookIm;
        this.bookName = bookName;
        this.authorName = authorName;
        this.price = price;
    }

    Book(byte[] bookIm, String bookName, String authorName, int price, int quantity){
        this.bookIm = bookIm;
        this.bookName = bookName;
        this.authorName = authorName;
        this.price = price;
        this.quantity= quantity;
    }

    public byte[] getBookIm() {
        return bookIm;
    }

    public void setBookIm(byte[] bookIm) {
        this.bookIm = bookIm;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

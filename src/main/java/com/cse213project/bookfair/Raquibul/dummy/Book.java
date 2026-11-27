package com.cse213project.bookfair.Raquibul.dummy;

import java.io.Serializable;

// Dummy model class for User 5 (Book Inventory Officer - Ankur Das)
public class Book implements Serializable {
    private String isbn;
    private String title;
    private String author;
    private String category;
    private String publisher;
    private double price;
    private boolean visibleToPublic;

    public Book() {
        this.isbn = "";
        this.title = "";
        this.author = "";
        this.category = "";
        this.publisher = "";
        this.price = 0.0;
        this.visibleToPublic = true;
    }

    public Book(String isbn, String title, String author, String category, String publisher, double price, boolean visibleToPublic) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
        this.price = price;
        this.visibleToPublic = visibleToPublic;
    }

    public String getIsbn() { return this.isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return this.author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return this.category; }
    public void setCategory(String category) { this.category = category; }

    public String getPublisher() { return this.publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public double getPrice() { return this.price; }
    public void setPrice(double price) { this.price = price; }

    public boolean getVisibleToPublic() { return this.visibleToPublic; }
    public void setVisibleToPublic(boolean visibleToPublic) { this.visibleToPublic = visibleToPublic; }

    @Override
    public String toString() {
        return "Book [ISBN=" + this.isbn + ", Title=" + this.title + ", Author=" + this.author
                + ", Category=" + this.category + ", Publisher=" + this.publisher + ", Price=" + this.price + "]";
    }
}

package application.bookstore.models;

import java.io.*;


public  class BookSold implements Serializable {
    @Serial
    //version control for serialization to be properly serialized and deserliazed
    private static final long serialVersionUID = 1234567L;
    //variables
    private int quantity;
    private String bookISBN;
    private String title;
    private float unitPrice;
    private Author author;
    private final transient Book book;

    //constructor to initialize a object based on info of the book being sold b & quantity sold
    public BookSold( Book b,int quantity) {
        book=b;
        setQuantity(quantity);
        setBookISBN(b.getIsbn());
        setTitle(b.getTitle());
        setUnitPrice(b.getSellingPrice());
        setAuthor(b.getAuthor());
    }

//override object class to provide string representation of the object
    @Override
    public String toString()
    {
        //creating a formatted string to control how the string is presented
        //I just learned this :
        //%-6s :  Represents a string left-justified in a field of width 6
        //%-20s : Represents a string left-justified in a field of width 20
        // -6.2f: Represents a floating-point number left-justified in a field of width 6 with 2 decimal places.
        //placeholder
        return String.format("%-6s %-20s Unit: %-6.2f Total: %.2f",quantity+"pcs", title, unitPrice, getTotalPrice());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getTotalPrice() {
        return quantity*unitPrice;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }
}
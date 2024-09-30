package application.bookstore.models;

import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;

import application.bookstore.models.Author;
import application.bookstore.models.BaseModel;

//extend Bases model and implement serializable for byte stream conversion
public class Book extends BaseModel  implements Serializable{
    private String isbn;
    private String title;
    private float purchasedPrice;
    private float sellingPrice;
    private Author author;
    private int  stock;
    private Category category;
    private String supplier;
    @Serial
    private static final long serialVersionUID = 1234567L;//used for deserlization to verify that loaded class matches with serialized one

    //getters and setters
    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

//constants for file path to binary file books.ser and an array list to store book objects
    public static final String FILE_PATH = "data/books.ser";
    private static final File DATA_FILE = new File(FILE_PATH);
    private static final ArrayList<Book> books = new ArrayList<>();

    //default consturctor
    public Book(){}

//parameteried constructor to provide values
    public Book(String isbn, String title, float purchasedPrice, float sellingPrice, Author author, int stock,
                Category category, String supplier) {
        super();
        this.isbn = isbn;
        this.title = title;
        this.purchasedPrice = purchasedPrice;
        this.sellingPrice = sellingPrice;
        this.author = author;
        this.stock = stock;
        this.category = category;
        this.supplier = supplier;
    }
//methods
    public float getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(float purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

//return book details
    public String getFullResult() {
        return getIsbn()+ " " + getTitle() + " " + getPurchasedPrice();
    }

    //override method to save the current book to file
    @Override
    public boolean saveInFile() {
        boolean saved = super.save(DATA_FILE);
        if (saved)
            books.add(this);
        return saved;
    }

    //check book info validity
    public boolean isValid() {
        if (!isbn.matches("^\\d+$")) {
            return false;
        }
        if (sellingPrice < 0 || purchasedPrice < 0 || stock<0 )
            return false;
        if (!title.matches(".*\\w+.*") || !supplier.matches(".*\\w+.*"))
            return false;
        return true;
    }

// check if book already exists by comparing isbn
    public boolean existsInList(){
        for (Book b: books) {
            if (b.getIsbn().equals(this.getIsbn()))
                return true;
        }
        return false;
    }
//update the data file with current list of books
    @Override
    public boolean updateFile(){
        try {
            overwriteCurrentListToFile(DATA_FILE, books);
        }
        catch (Exception e)//throw exception and print trace stack if any exception ocurs
        {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }

//delete a book from file
    @Override
    public boolean deleteFromFile() {
        books.remove(this);
        try {
            overwriteCurrentListToFile(DATA_FILE, books);
            System.out.println("Book deleted successfully from file.");
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

// returns a array list of books
    public static ArrayList<Book> getBooks() {
        if (books.size() == 0) {//if book list is empty
            try {
                //create new object input stream to read object from file
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
                while (true) { //read books until the end of file
                    Book temp = (Book) inputStream.readObject(); // read a object from file and stores it as temp
                    if (temp != null) // check if the object is not null (the end of file)
                        books.add(temp);//add it to the list of books
                    else
                        break;
                }
                inputStream.close();
                //end of file exception
            } catch (EOFException eofException) {
                System.out.println("End of book file reached!");
            }
            catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return books;
    }

//method that takes data file and array list as parameter
    public static<Book extends BaseModel> void overwriteCurrentListToFile(File DATA_FILE, ArrayList<Book> bookList) throws IOException {
       //create file output stream to overwrite file not append
        FileOutputStream fileOutputStream = new FileOutputStream(DATA_FILE, false);
        //object output stream to write serialized objects to the file
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        //if book list is empty
        if (bookList.size() == 0)
        {
        }
        else//if not
        {
            for (Book book : bookList)//iterate through each book in booklist and write it to the file
                outputStream.writeObject(book); // using writeObject
        }
    }

//search the list and return books that meet specifications
    public static ArrayList<Book> getSearchResults(String searchText)
    {
        //initialize array list to store the books matching
        ArrayList<Book> searchResults = new ArrayList<>();
        //iterate through each object in Books
        for(Book book: getBooks())
            //check if the current book matches the search criteria
            if (book.getTitle().toLowerCase().matches(".*"+searchText.toLowerCase()+".*") ||
                    book.getAuthor().getFullName().toLowerCase().matches(".*"+searchText.toLowerCase()+".*")||
                    book.getIsbn().matches(".*"+searchText+".*") )
                searchResults.add(book);//if it does , add the book
        return searchResults;
    }






}
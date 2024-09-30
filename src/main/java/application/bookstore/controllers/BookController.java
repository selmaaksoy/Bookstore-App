package application.bookstore.controllers;

import java.util.ArrayList;
import java.util.List;

import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.models.Category;
import application.bookstore.views.BookView;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;

//create class
public class BookController
{
    //declare a varibale of BookView
    private final BookView bookView;
//constructor takes BookView object
    public BookController(BookView bookView)
    {
        //assign bookview to private instance bookview
        this.bookView = bookView;
        //set listeners for  these actions
        setSaveListener();
        setEditListener();
        setDeleteListener();
        setComboBoxListener();
        setSearchListener();
    }

    //listener for search functionality
    private void setSearchListener()
    {
        //set action event handler for clear button
        bookView.getSearchView().getClearBtn().setOnAction(e ->
        {
            //when clear button is clicked search field is set to empty string
            bookView.getSearchView().getSearchField().setText("");
            //update the table view with full list of books
            bookView.getTableView().setItems(FXCollections.observableArrayList(Book.getBooks()));
        });
        //action event for search button
        bookView.getSearchView().getSearchBtn().setOnAction(e ->
        {
            //get the ext from search field
            String searchText = bookView.getSearchView().getSearchField().getText();
            //get the list of books that match search field text
            ArrayList<Book> searchResults = Book.getSearchResults(searchText);
            //update table view
            bookView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }

    private void setComboBoxListener()
    {
        //event listener when mouse is clicked for author
        bookView.getAuthorsComboBox().setOnMouseClicked(e->
        {
            //add the options (list of authors)
            bookView.getAuthorsComboBox().getItems().setAll(Author.getAuthors());
            //if the ist is not empty , set the default selected author to the first author in the list
            if (!Author.getAuthors().isEmpty())
                bookView.getAuthorsComboBox().setValue(Author.getAuthors().get(0));
        });
        //mouse click event for category
        bookView.getCategoryComboBox().setOnMouseClicked(e->
        {
            bookView.getCategoryComboBox().getItems().setAll(Category.values());
  
        });

    }

    //
    private void setSaveListener()
    {
        //event listener for save button
        bookView.getSaveBtn().setOnAction(e ->
        {
            //retrieve data
            String isbn = bookView.getIsbnField().getText();
            String title = bookView.getTitleField().getText();

            float purchasedPrice = Float.parseFloat(bookView.getPurchasedPriceField().getText());
            float sellingPrice = Float.parseFloat(bookView.getSellingPriceField().getText());
            Author author = bookView.getAuthorsComboBox().getValue();
            Category category = bookView.getCategoryComboBox().getValue();
            String supplier = bookView.getSupplierField().getText();
            int stock = Integer.parseInt(bookView.getStockField().getText());
            //create a new book object with the data
            Book book = new Book(isbn ,title,purchasedPrice,sellingPrice, author, stock, category, supplier);

//check if the book already exists
            if (!book.existsInList())
            {
                //if not
                if (book.saveInFile())
                {
                    //try to add the book to saveInFile , add to table view , and print message
                    bookView.getTableView().getItems().add(book);
                    bookView.getResultLabel().setText("Book created successfully");
                    bookView.getResultLabel().setTextFill(Color.GREEN);
                    resetFields();
                } else
                {//if it fails , print a message
                    bookView.getResultLabel().setText("Book creation failed");
                    bookView.getResultLabel().setTextFill(Color.RED);
                }
            } else { // if book with same ISBN exists print message
                bookView.getResultLabel().setText("ISBN must be unique");
                bookView.getResultLabel().setTextFill(Color.RED);
            }
        });
    }

    private void setDeleteListener(){
        bookView.getDeleteBtn().setOnAction(e->{
            List<Book> itemsToDelete = List.copyOf(bookView.getTableView().getSelectionModel().getSelectedItems());
            for (Book b: itemsToDelete) {
                if (b.deleteFromFile()) {
                    bookView.getTableView().getItems().remove(b);
                    bookView.getResultLabel().setText("Book removed successfully");
                    bookView.getResultLabel().setTextFill(Color.GREEN);
                } else {
                    bookView.getResultLabel().setText("Book deletion failed");
                    bookView.getResultLabel().setTextFill(Color.RED);
                    break;
                }
            }
        });
    }

    private void setEditListener()
    {
        //action handler for edit action for isbn
        bookView.getIsbnCol().setOnEditCommit(e ->
        {
            //get the object being edited and old isbn value
            Book bookToEdit = e.getRowValue();
            //change old value to the new value from edit cell
            String oldVal=bookToEdit.getIsbn();
            bookToEdit.setIsbn(e.getNewValue());
            //check if isbn is valid using is valid mehthod from bookclass
            if (bookToEdit.isValid()){
                bookToEdit.updateFile();
            }
            else {
                //if not set isbn to old value
                bookToEdit.setIsbn(oldVal);
                //update table view
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                //error
                bookView.getResultLabel().setTextFill(Color.DARKRED);
            }
        });

        //same for title , change the old value to new one
        bookView.getTitleCol().setOnEditCommit(e ->
        {
            Book bookToEdit = e.getRowValue();
            String oldVal=bookToEdit.getTitle();
            bookToEdit.setTitle(e.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateFile();
            }
            else {
                //if ttile provided isnot valid print message
                System.out.println("You cannot edit "+e.getNewValue());
                bookToEdit.setTitle(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("No change done");
                bookView.getResultLabel().setTextFill(Color.RED);
            }
        });
// edit option for purchased price
        bookView.getPurchasedPriceCol().setOnEditCommit(e -> {
            Book bookToEdit = e.getRowValue();
            float oldVal=bookToEdit.getPurchasedPrice();
            bookToEdit.setPurchasedPrice(e.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateFile();
            }
            else {
       
                bookToEdit.setPurchasedPrice(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("Invalid  book data");
                bookView.getResultLabel().setTextFill(Color.RED);
            }
        });
// edit for selling price
        bookView.getSellingPriceCol().setOnEditCommit(e -> {
            Book bookToEdit = e.getRowValue();
            float oldVal=bookToEdit.getSellingPrice();
            bookToEdit.setSellingPrice(e.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateFile();
            }
            else {
                bookToEdit.setSellingPrice(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("Try again");
                bookView.getResultLabel().setTextFill(Color.RED);
            }
        });
        //for stock
        bookView.getStockCol().setOnEditCommit(e -> {
            Book bookToEdit = e.getRowValue();
            int oldVal=bookToEdit.getStock();
            bookToEdit.setStock(e.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateFile();
            }
            else {
                bookToEdit.setStock(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("Try again");
                bookView.getResultLabel().setTextFill(Color.RED);
            }
        });

        //for supplier
        bookView.getSupplierCol().setOnEditCommit(e -> {
            Book bookToEdit = e.getRowValue();
            String oldVal=bookToEdit.getTitle();
            bookToEdit.setSupplier(e.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateFile();
            }
            else {
                System.out.println("You cannot edit "+e.getNewValue());
                bookToEdit.setTitle(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("No edition done");
                bookView.getResultLabel().setTextFill(Color.RED);
            }
        });



    }

    //to clear the input field in view
    private void resetFields()
    {
        //sets the isbn input field to empty string
        bookView.getIsbnField().setText("");
        bookView.getTitleField().setText("");
        bookView.getPurchasedPriceField().setText("");
        bookView.getSellingPriceField().setText("");
    }
}

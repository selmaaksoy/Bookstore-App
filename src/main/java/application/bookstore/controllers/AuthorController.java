package application.bookstore.controllers;

import application.bookstore.auxiliaries.FileHandler;

import application.bookstore.models.Author;
import application.bookstore.views.AuthorView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AuthorController {
    private AuthorView authorView;
    public AuthorController(AuthorView authorView) {
        this.authorView = authorView;
        setSaveListener();
        setDeleteListener();
        setEditListener();
        setSearchListener();
    }

    private void setEditListener() {
        authorView.getFirstNameCol().setOnEditCommit(e -> {
            Author authorToEdit = e.getRowValue();
            String oldVal=authorToEdit.getFirstName();
            authorToEdit.setFirstName(e.getNewValue());
            if (authorToEdit.isValid()){
                authorToEdit.updateFile();
            }
            else {
                System.out.println("Unable to edit the author "+e.getNewValue());
                authorToEdit.setFirstName(oldVal);
                authorView.getTableView().getItems().set(authorView.getTableView().getItems().indexOf(authorToEdit), authorToEdit);
                authorView.getResultLabel().setText("Invalid edit :(");
                authorView.getResultLabel().setTextFill(Color.RED);
            }
        });

        authorView.getLastNameCol().setOnEditCommit(e -> {
            Author authorToEdit = e.getRowValue();
            String oldVal=authorToEdit.getLastName();
            authorToEdit.setLastName(e.getNewValue());
            if (authorToEdit.isValid()){
                authorToEdit.updateFile();
            }
            else {
                System.out.println("Author cannot be edited "+e.getNewValue());
                authorToEdit.setLastName(oldVal);
                authorView.getTableView().getItems().set(authorView.getTableView().getItems().indexOf(authorToEdit), authorToEdit);
                authorView.getResultLabel().setText("Edit did not succed");
                authorView.getResultLabel().setTextFill(Color.RED);
            }
        });
    }

    private void setSearchListener() {
        authorView.getSearchView().getClearBtn().setOnAction(e -> {
            authorView.getSearchView().getSearchField().setText("");
            authorView.getTableView().setItems(FXCollections.observableArrayList(Author.getAuthors()));
        });
        authorView.getSearchView().getSearchBtn().setOnAction(e -> {
            String searchText = authorView.getSearchView().getSearchField().getText();
            ArrayList<Author> searchResults = Author.getSearchResults(searchText);
            authorView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }

    private void setDeleteListener() {
        authorView.getDeleteBtn().setOnAction(e->{
            List<Author> itemsToDelete = List.copyOf(authorView.getTableView().getSelectionModel().getSelectedItems());
            for (Author a: itemsToDelete) {
                if (a.deleteFromFile()) {
                    authorView.getTableView().getItems().remove(a);
                    System.out.println("The author: "+a.getFullName()+" is deleted from file");
                    authorView.getResultLabel().setText("Author's book is also deleted");
                    authorView.getResultLabel().setTextFill(Color.GREEN);
                } else {
                    System.out.println("You cannot delete this book: ");
                    authorView.getResultLabel().setText("You cannot delete");
                    authorView.getResultLabel().setTextFill(Color.RED);
                    break;
                }
            }
        });
    }

    private void setSaveListener() {
        authorView.getSaveBtn().setOnAction(e -> {
            String firstName = authorView.getFirstNameField().getText();
            String lastName = authorView.getLastNameField().getText();
            Author author = new Author(firstName, lastName);
            if (!author.existsInList()) {
                if (author.saveInFile()) {
                    authorView.getTableView().getItems().add(author);
                    authorView.getResultLabel().setText("Author created");
                    authorView.getResultLabel().setTextFill(Color.GREEN);
                    authorView.getFirstNameField().setText("");
                    authorView.getLastNameField().setText("");
                } else {
                    authorView.getResultLabel().setText("No result in table");
                    authorView.getResultLabel().setTextFill(Color.RED);
                }
            }
            else {
                authorView.getResultLabel().setText("This author is found in list");
                authorView.getResultLabel().setTextFill(Color.RED);
            }
        });
    }
}

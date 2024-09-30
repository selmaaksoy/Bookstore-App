package application.bookstore.controllers;

import application.bookstore.models.Role;
import application.bookstore.models.User;
import application.bookstore.views.UserView;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private UserView usersView;
    public UserController(UserView usersView) {
        this.usersView = usersView;
        setSaveListener();
        setDeleteListener();
        setEditListener();
        setSearchListener();
    }

    private void setEditListener() {
        usersView.getUsernameCol().setOnEditCommit(e -> {
            User userToEdit = e.getRowValue();
            String oldVal=userToEdit.getUsername();
            userToEdit.setUsername(e.getNewValue());
            if (userToEdit.isValid()){
                userToEdit.updateFile();
            }
            else {
                System.out.println("Edit value invalid! "+e.getNewValue());
                userToEdit.setUsername(oldVal);
                usersView.getTableView().getItems().set(usersView.getTableView().getItems().indexOf(userToEdit), userToEdit);
                usersView.getResultLabel().setText("Edit value invalid!");
                usersView.getResultLabel().setTextFill(Color.RED);
            }
        });

        usersView.getPasswordCol().setOnEditCommit(e -> {
            User userToEdit = e.getRowValue();
            String oldVal=userToEdit.getPassword();
            userToEdit.setPassword(e.getNewValue());
            if (userToEdit.isValid()){
                userToEdit.updateFile();
            }
            else {
                System.out.println("The data is not correct!"+e.getNewValue());
                userToEdit.setPassword(oldVal);
                usersView.getTableView().getItems().set(usersView.getTableView().getItems().indexOf(userToEdit), userToEdit);
                usersView.getResultLabel().setText("Check your data!!!");
                usersView.getResultLabel().setTextFill(Color.RED);
            }
        });

        usersView.getRoleCol().setOnEditCommit(e -> {
            User userToEdit = e.getRowValue();
            Role oldVal=userToEdit.getRole();
            userToEdit.setRole(e.getNewValue());
            if (userToEdit.isValid()){
                userToEdit.updateFile();
            }
            else {
                System.out.println("Invalid input :("+e.getNewValue());
                userToEdit.setRole(oldVal);
                usersView.getTableView().getItems().set(usersView.getTableView().getItems().indexOf(userToEdit), userToEdit);
                usersView.getResultLabel().setText("Value invalid!");
                usersView.getResultLabel().setTextFill(Color.RED);
            }
        });
    }

    private void setSearchListener() {
        usersView.getSearchView().getClearBtn().setOnAction(e -> {
            usersView.getSearchView().getSearchField().setText("");
            usersView.getTableView().setItems(FXCollections.observableArrayList(User.getUsers()));
        });
        usersView.getSearchView().getSearchBtn().setOnAction(e -> {
            String searchText = usersView.getSearchView().getSearchField().getText();
            ArrayList<User> searchResults = User.getSearchResults(searchText);
            usersView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }

    private void setDeleteListener() {
        usersView.getDeleteBtn().setOnAction(e->{
            List<User> itemsToDelete = List.copyOf(usersView.getTableView().getSelectionModel().getSelectedItems());
            for (User a: itemsToDelete) {
                if (a.deleteFromFile()) {
                    usersView.getTableView().getItems().remove(a);
                } else {
                    break;
                }
            }
        });
    }

    private void setSaveListener() {
        usersView.getSaveBtn().setOnAction(e -> {
            String username = usersView.getUserNameField().getText();
            String password = usersView.getPasswordField().getText();
            Role role = usersView.getRoleComboBox().getValue();
            User user = new User(username, password, role);
            if (!user.ifusernameExists()) {
                if (user.saveInFile()) {
                    usersView.getTableView().getItems().add(user);
                    usersView.getResultLabel().setText("User is added");
                    usersView.getResultLabel().setTextFill(Color.GREEN);
                    usersView.getUsernameCol().setText("");
                    usersView.getPasswordField().setText("");
                } else {
                    usersView.getResultLabel().setText("User is not added");
                    usersView.getResultLabel().setTextFill(Color.RED);
                }
            }
            else {
                usersView.getResultLabel().setText("This username is found in bookstore system.");
                usersView.getResultLabel().setTextFill(Color.RED);
            }
        });
    }

}


package application.bookstore.views;

import application.bookstore.controllers.UserController;
import application.bookstore.models.Author;
import application.bookstore.models.Role;
import application.bookstore.models.User;
import application.bookstore.ui.CreateButton;
import application.bookstore.ui.DeleteButton;
import application.bookstore.ui.EditButton;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserView extends View{
    private final BorderPane borderPane = new BorderPane();
    private final TableView<User> tableView = new TableView<>();
    private final HBox formPane = new HBox();
    private final TextField userNameField = new TextField();
    private final TextField passwordField = new TextField();
    private final ComboBox<Role> roleComboBox = new ComboBox<>();
    private final Button saveBtn = new CreateButton();
    private final Button deleteBtn = new DeleteButton();
    private final TableColumn<User, String> usernameCol = new TableColumn<>("Username");
    private final TableColumn<User, String> passwordCol = new TableColumn<>("Password");
    private final TableColumn<User, Role> roleCol = new TableColumn<>("Role");
    private final Label resultLabel = new Label("");
    private final SearchView searchView = new SearchView("Search for a user");

    public ComboBox<Role> getRoleComboBox() {
        return roleComboBox;
    }

    public TextField getUserNameField() {
        return userNameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TableColumn<User, String> getPasswordCol() {
        return passwordCol;
    }

    public TableColumn<User, Role> getRoleCol() {
        return roleCol;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public TableColumn<User, String> getUsernameCol() {
        return usernameCol;
    }


    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public TableView<User> getTableView() {
        return tableView;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public UserView() {
        setTableView();
        setForm();
        new UserController(this);
    }

    @Override
    public Parent getView() {
        borderPane.setLeft(tableView);
        tableView.setMinWidth(1000);
        VBox vBox1 = new VBox();

        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(5);
        vBox1.getChildren().addAll(formPane, resultLabel);
        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.TOP_CENTER);
        vBox2.setSpacing(5);
        searchView.getSearchPane().setAlignment(Pos.TOP_LEFT);
        vBox2.getChildren().add(searchView.getSearchPane());
        borderPane.setRight(vBox1);
        borderPane.setTop(vBox2);
        return borderPane;
    }

    private void setForm() {

        formPane.setPadding(new Insets(20));
        formPane.setSpacing(20);
        HBox hb1 = new HBox();
        HBox hb2 = new HBox();
        VBox v = new VBox();
        Label usernameL = new Label("username:               ");
        Label passL = new Label("password:              ");
        Label roleLabel = new Label("Role               ");
        roleComboBox.getItems().setAll(Role.ADMIN, Role.MANAGER, Role.LIBRARIAN);
        roleComboBox.setValue(Role.MANAGER);
        HBox hb5 = new HBox();
        HBox hb3 = new HBox();
        hb1.getChildren().addAll(usernameL,userNameField);
        hb1.setSpacing(20);
        hb2.getChildren().addAll(passL, passwordField);
        hb2.setSpacing(20);
        hb3.getChildren().addAll(roleLabel, roleComboBox);
        hb3.setSpacing(45);
        v.getChildren().addAll(hb1,hb2,hb3);
        v.setSpacing(10);
        v.setPadding(new Insets(40,80,0,40));
        hb5.getChildren().addAll(saveBtn, deleteBtn);
        hb5.setSpacing(20);
        hb5.setPadding(new Insets(30,0,80,40));
        v.getChildren().add(hb5);
        formPane.getChildren().add(v);
        formPane.setAlignment(Pos.CENTER_LEFT);
        formPane.setStyle("-fx-background-color: FFF0F5");


    }

    private void setTableView() {

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(true);
        tableView.setItems(FXCollections.observableArrayList(User.getUsers()));
        usernameCol.setCellValueFactory(
                new PropertyValueFactory<>("username")
        );
        usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordCol.setCellValueFactory(
                new PropertyValueFactory<>("password")
        );
        passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());

        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleCol.setCellFactory(ComboBoxTableCell.forTableColumn(Role.values()));
        tableView.getColumns().addAll(usernameCol, passwordCol, roleCol);
    }
}
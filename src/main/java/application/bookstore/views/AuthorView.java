package application.bookstore.views;

import application.bookstore.controllers.AuthorController;
import application.bookstore.models.Author;
import application.bookstore.ui.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class AuthorView extends View{
	private final BorderPane borderPane = new BorderPane();
    private final TableView<Author> tableView = new TableView<>();
    private final HBox formPane = new HBox();
    private final TextField firstNameField = new TextField();
    private final TextField lastNameField = new TextField();
    private final Button saveBtn = new CreateButton();
    private final Button deleteBtn = new DeleteButton();
    private final TableColumn<Author, String> firstNameCol = new TableColumn<>("First name");
    private final TableColumn<Author, String> lastNameCol = new TableColumn<>("Last name");
    private final Label resultLabel = new Label("");
    private final SearchView searchView = new SearchView("Search for an author");
	private TableView<Author> authorsComboBox;

    public SearchView getSearchView() {
        return searchView;
    }

    public TableColumn<Author, String> getFirstNameCol() {
        return firstNameCol;
    }

    public TableColumn<Author, String> getLastNameCol() {
        return lastNameCol;
    }


    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public TableView<Author> getTableView() {
        return tableView;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public AuthorView() {
        setTableView();
        setForm();
        new AuthorController(this);
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
        Label firstNameLabel =           new Label("Name:               ");
        Label lastNameLabel =          new Label("Surname:              ");
        HBox hb5 = new HBox();
        hb1.getChildren().addAll(firstNameLabel,firstNameField);
        hb1.setSpacing(20);
        hb2.getChildren().addAll(lastNameLabel,lastNameField);
        hb2.setSpacing(15);
        v.getChildren().addAll(hb1,hb2);
        v.setSpacing(10);
        v.setPadding(new Insets(40,80,0,40));
        hb5.getChildren().addAll(saveBtn, deleteBtn);
        hb5.setSpacing(20);
        hb5.setPadding(new Insets(30,0,80,40));
        v.getChildren().add(hb5);
        formPane.getChildren().add(v);
        formPane.setAlignment(Pos.CENTER_LEFT);
    }


    private void setTableView() {
        // select multiple records in order to delete them
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(true);
        tableView.setItems(FXCollections.observableArrayList(Author.getAuthors()));
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        tableView.getColumns().addAll(firstNameCol, lastNameCol);

    }
}
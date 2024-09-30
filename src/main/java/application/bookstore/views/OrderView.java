package application.bookstore.views;

import application.bookstore.controllers.BookController;
import application.bookstore.controllers.OrderController;
import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.models.Category;
import application.bookstore.models.Order;
import application.bookstore.ui.CreateButton;
import application.bookstore.ui.DeleteButton;
import application.bookstore.ui.EditButton;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class OrderView extends View
{
	

	private final BorderPane borderPane = new BorderPane();
    private final TableView<Order> tableView = new TableView<>();
    private final VBox formPane = new VBox();
    private final TextField clientNameField = new TextField();
    private final TextField priceField = new TextField();
    private final TextField quantityField = new TextField();
    private final TextField  noOfTypesField= new TextField();
    private final ComboBox<String> isbnComboBox = new ComboBox<>();
    private final Button saveBtn = new CreateButton();
    private final Button deleteBtn = new DeleteButton();
    private final Button billBtn = new Button("Bill");
    public Button getBillBtn() {
		return billBtn;
	}


	private final TableColumn<Order, Integer> quantityCol = new TableColumn<>("Quantity");
    private final TableColumn<Order, String> clientNameCol = new TableColumn<>("Client Name");
    private final TableColumn<Order, String> isbnCol = new TableColumn<>("ISBN");
    private final TableColumn<Order, Float> priceCol = new TableColumn<>("Price");
    private final TableColumn<Order, Float> totalCol = new TableColumn<>("Total");
    private final Label resultLabel = new Label("");
    private final SearchView searchView = new SearchView("Search for a book");
    
    
    

    
    public TableView<Order> getTableView() {
		return tableView;
	}


	public VBox getFormPane() {
		return formPane;
	}


	public TextField getClientNameField() {
		return clientNameField;
	}



	public TextField getQuantityField() {
		return quantityField;
	}


	public ComboBox<String> getIsbnComboBox() {
		return isbnComboBox;
	}


	public TableColumn<Order, Integer> getQuantityCol() {
		return quantityCol;
	}


	public TableColumn<Order, String> getClientNameCol() {
		return clientNameCol;
	}


	public TableColumn<Order, Float> getPriceCol() {
		return priceCol;
	}


	public TextField getNoOfTypesField() {
		return noOfTypesField;
	}


	public TableColumn<Order, Float> getTotalCol() {
		return totalCol;
	}


	public Button getSaveBtn() {
        return saveBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public TableColumn<Order, String> getIsbnCol() {
        return isbnCol;
    }


    public Label getResultLabel() {
        return resultLabel;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public OrderView() {
        setTableView();
        setForm();
        new OrderController(this);
    }


    private void setForm() {
    	 
    	HBox hb1 = new HBox();
    	HBox hb2 = new HBox();
    	HBox hb3 = new HBox();
    	HBox hb4 = new HBox();
    	HBox hb5 = new HBox();
    	HBox hb6 = new HBox();
    	VBox v = new VBox();
        Label isbnLabel =           new Label("ISBN:               ");
        Label noOfTypesLabel =           new Label("Types of books:               ");
        Label quantityLabel =          new Label("Quantity:              ");
        Label priceLabel = new Label("Price:");
        Label clientNameLabel = new Label("Client Name:");

        for(Book b: Book.getBooks()) {
        	 isbnComboBox.getItems().add(b.getIsbn());
        }
  
        
   
        hb1.getChildren().addAll(isbnLabel,isbnComboBox);
        hb1.setSpacing(20);
        hb2.getChildren().addAll(quantityLabel,quantityField);
        hb2.setSpacing(20);
        hb4.getChildren().addAll(priceLabel,priceField);
        hb4.setSpacing(20);
        hb3.getChildren().addAll(clientNameLabel,clientNameField);
        hb3.setSpacing(20);
        v.getChildren().addAll(hb1,hb2,hb4,hb3);
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
  
        tableView.setItems(FXCollections.observableArrayList(Order.getOrders()));

        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn")
        );
        
        quantityCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity")
        );
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        
        priceCol.setCellValueFactory(
                new PropertyValueFactory<>("price")
        );
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

      
        clientNameCol.setCellValueFactory(
                new PropertyValueFactory<>("clientName")
        );
        clientNameCol.setCellFactory(TextFieldTableCell.forTableColumn());



        tableView.getColumns().addAll(isbnCol, clientNameCol,quantityCol,priceCol);
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


	public TextField getPriceField() {
		return priceField;
	}

    
}

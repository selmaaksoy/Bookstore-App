package application.bookstore.views;

import application.bookstore.controllers.BookController;

import application.bookstore.controllers.OrderController;
import application.bookstore.models.*;
import application.bookstore.ui.CreateButton;
import application.bookstore.ui.DeleteButton;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;


public class BookView extends View{

    //getter for stock column
    public TableColumn<Book, Integer> getStockCol() {
		return stockCol;
	}

//set the stock column to provide values
	public void setStockCol(TableColumn<Book, Integer> stockCol) {
		this.stockCol = stockCol;
	}

//gettter for text field : stock
	public TextField getStockField() {
		return stockField;
	}

	public TextField getSupplierField() {
		return supplierField;
	}

    //layout to divide content into regions
	private final BorderPane borderPane = new BorderPane();
    private final TableView<Book> tableView = new TableView<>();// to display list of components
    private final VBox formPane = new VBox();//vertical box
    private final TextField isbnField = new TextField();//text field for isbn
    private final TextField titleField = new TextField();//text field for title
    private final TextField purchasedPriceField = new TextField();//text field for purchased price
    private final TextField sellingPriceField = new TextField();
    private final TextField stockField = new TextField();
    private final TextField supplierField = new TextField();
    private final ComboBox<Author> authorsComboBox = new ComboBox<>();//combo box for authors
    private final ComboBox<Category> categoryComboBox = new ComboBox<>();//combo box for categories
    private final Button saveBtn = new CreateButton();//save info button
    private final Button deleteBtn = new DeleteButton();//delete button
    private final TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");//table column for display and editing
    private final TableColumn<Book, String> titleCol = new TableColumn<>("Title");
    private final TableColumn<Book, String> authorCol = new TableColumn<>("Author");
    private final TableColumn<Book, Category> categoryCol = new TableColumn<>("Category");
    
    public ComboBox<Category> getCategoryComboBox() {
		return categoryComboBox;
	}
//columns for supplier ,purchased , selling price
	private final TableColumn<Book, String> supplierCol = new TableColumn<>("Supplier");
	private final TableColumn<Book, Float> purchasedPriceCol = new TableColumn<>("Purchased Price");
    private final TableColumn<Book, Float> sellingPriceCol = new TableColumn<>("Selling Price");
    // can be used to display messages or results related to book operations on the user interface
    private final Label resultLabel = new Label("");
    //instance of SearchView class , to search for books
    private final SearchView searchView = new SearchView("Search for a book");
//table column for stock
	private TableColumn<Book, Integer> stockCol = new TableColumn<>("Stock");
    
    
//getters for UI components
    public TableView<Book> getTableView() {
        return tableView;
    }
    
    
    
    public TableColumn<Book, Category> getCategoryCol() {
		return categoryCol;
	}


	public TableColumn<Book, String> getSupplierCol() {
		return supplierCol;
	}

   
    public TextField getIsbnField() {
        return isbnField;
    }

    public TextField getTitleField() {
        return titleField;
    }

    public TextField getPurchasedPriceField() {
        return purchasedPriceField;
    }

    public TextField getSellingPriceField() {
        return sellingPriceField;
    }

    public ComboBox<Author> getAuthorsComboBox() {
        return authorsComboBox;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public TableColumn<Book, String> getIsbnCol() {
        return isbnCol;
    }

    public TableColumn<Book, String> getTitleCol() {
        return titleCol;
    }

    public TableColumn<Book, Float> getPurchasedPriceCol() {
        return purchasedPriceCol;
    }

    public TableColumn<Book, Float> getSellingPriceCol() {
        return sellingPriceCol;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    //initialize the view
    public BookView()
    {
        //set up table view
        setTableView();
        //set up user input fields , creating and configuring text fields , buttons etc
        setForm();
        //handles user interaction like saving editin etc
        new BookController(this);
    }

//setting the form
    private void setForm() {
    	 //layout panes
    	HBox hb1 = new HBox();
    	HBox hb2 = new HBox();
    	HBox hb3 = new HBox();
    	HBox hb4 = new HBox();
    	VBox v = new VBox();
        //label the corresponding label fields
        Label isbnLabel =           new Label("ISBN:               ");
        Label titleLabel =          new Label("Title:              ");
        Label purchasedPriceLabel = new Label("Purchased price:");
        Label sellingPriceLabel =   new Label("Selling price:   ");
        Label stockLabel =   new Label("Stock:   ");
        Label authorLabel = new Label("Author:");
        Label categoryLabel = new Label("Category:");
        Label supplierLabel = new Label("Supplier:");
//data from Author is added
        authorsComboBox.getItems().setAll(Author.getAuthors());
        //values from Category are added
        categoryComboBox.setItems( FXCollections.observableArrayList(Category.values()));

        HBox hb5 = new HBox();
        HBox hb6 = new HBox();
        HBox hb7 = new HBox();
        HBox hb8 = new HBox();
        HBox hb9 = new HBox();
        //creating layouts
        hb1.getChildren().addAll(isbnLabel,isbnField);
        hb1.setSpacing(20);

        hb2.getChildren().addAll(titleLabel,titleField);
        hb2.setSpacing(20);

        hb3.getChildren().addAll(purchasedPriceLabel,purchasedPriceField);
        hb3.setSpacing(20);

        hb4.getChildren().addAll(sellingPriceLabel,sellingPriceField);
        hb4.setSpacing(20);

        hb6.getChildren().addAll(authorLabel,authorsComboBox);
        hb6.setSpacing(40);

        hb7.getChildren().addAll(stockLabel,stockField);
        hb7.setSpacing(40);

        hb8.getChildren().addAll(categoryLabel,categoryComboBox);
        hb8.setSpacing(40);

		hb9.getChildren().addAll(supplierLabel,supplierField);
        hb9.setSpacing(40);

        //arange vertically
        v.getChildren().addAll(hb1,hb2,hb3,hb4,hb7,hb8,hb9,hb6);
        v.setSpacing(10);
        v.setPadding(new Insets(40,80,0,40));
        hb5.getChildren().addAll(saveBtn, deleteBtn);
        hb5.setSpacing(20);
        hb5.setPadding(new Insets(30,0,80,40));
        v.getChildren().add(hb5);
        formPane.getChildren().add(v);
        formPane.setStyle("-fx-background-color: FFF0F5");
        formPane.setAlignment(Pos.CENTER_LEFT);
    }
    
   
//setting up table view
    private void setTableView()
    {
        //allow multiple selections
    	tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(true); //users can modify cell values
  //items are the books
        tableView.setItems(FXCollections.observableArrayList(Book.getBooks()));
//bind isbn column to isbn value of Book class
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn")
        );
      //specify that text field is being used
        isbnCol.setCellFactory(TextFieldTableCell.forTableColumn());

        //bind data to title property
        titleCol.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );

        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        supplierCol.setCellValueFactory(
                new PropertyValueFactory<>("supplier")
        );
        supplierCol.setCellFactory(TextFieldTableCell.forTableColumn());

        purchasedPriceCol.setCellValueFactory(
                new PropertyValueFactory<>("purchasedPrice")
        );
        purchasedPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        sellingPriceCol.setCellValueFactory(
                new PropertyValueFactory<>("sellingPrice")
        );
        sellingPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        authorCol.setCellValueFactory(
                new PropertyValueFactory<>("author"));
        
        categoryCol.setCellValueFactory(
                new PropertyValueFactory<>("category"));
        
        stockCol.setCellValueFactory(
                new PropertyValueFactory<>("stock")
        );
        stockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        authorCol.setEditable(true);
// add all columns to table view
        tableView.getColumns().addAll(isbnCol, titleCol, supplierCol, authorCol, categoryCol, purchasedPriceCol, sellingPriceCol,stockCol);

    }

    //implement get view method in book view
    @Override
    public Parent getView() {
        //left component of border pane
        borderPane.setLeft(tableView);
        tableView.setMinWidth(1000);
        //create vbox to hold the form and result table
        VBox vBox1 = new VBox();
        //align content, set spacing
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(5);
        vBox1.getChildren().addAll(formPane, resultLabel);
        //set up search view in vbox
        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.TOP_CENTER);
        vBox2.setSpacing(5);
        searchView.getSearchPane().setAlignment(Pos.TOP_LEFT);
        vBox2.getChildren().add(searchView.getSearchPane());
        //set vbo1 to right , vbox2 to left
        borderPane.setRight(vBox1);
        borderPane.setTop(vBox2);
        //return borderpane
        return borderPane;
    }

    
}

package application.bookstore.views;

import application.bookstore.controllers.AuthorController;

import application.bookstore.controllers.StatisticController;
import application.bookstore.models.Author;
import application.bookstore.models.Category;
import application.bookstore.models.Book;
import application.bookstore.models.Order;
import application.bookstore.ui.CreateButton;
import application.bookstore.ui.DeleteButton;
import application.bookstore.ui.EditButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class StatisticView extends View{
    private final VBox vBox = new VBox();
//create vbox

    //constructor pass reference to current instance
    public StatisticView() {
        new StatisticController(this);
    }


    @Override
    public Parent getView() {
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
//retrieve books from Book class
        ArrayList<Book> books = Book.getBooks();
        //ArrayList<BookSold> totalOrders = new ArrayList<>();
        //observable list for pichart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        //get the values from category enum &store it as array
        
        Category[] catVal = Category.values();
        String[] categories = new String[8];
        
        for(int i=0; i<8;i++) {
    		categories[i] = (catVal[i]).name();
    	}
        
        //initialize array to store stock info
        int[] stock = new int[8];
        
        int index=0;

        //loop tp iterate through categoris and books to calculate stock for each category
        for(Category c: Category.values()) {
        	for(Book b : books) {
        		if ((b.getCategory()).equals(c))
        			stock[index]+=b.getStock();
        	}
        	index+=1;
        }
// add the data of category stock found to the pie chart
        for(int i=0; i<8; i++) {
        pieChartData.add(new PieChart.Data(categories[i],stock[i]));
        }
             
        		
    
     //create a pie chart using the data & set title
        final PieChart chart = new PieChart(pieChartData);

        chart.setTitle("Categories in Library");
//add chart to vbox
        vBox.getChildren().addAll(chart);

        return vBox;
    }


}


package application.bookstore.views;

import java.util.ArrayList;

import application.bookstore.controllers.StatisticsBestSellerController;
import application.bookstore.models.Book;
import application.bookstore.models.Category;
import application.bookstore.models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

public class StatisticBestSellerCategory extends View {
	  private final VBox vBox = new VBox();


	    public StatisticBestSellerCategory() {
	        new StatisticsBestSellerController(this);
	    }

	    @Override
	    public Parent getView() {
	        vBox.setSpacing(10);
	        vBox.setAlignment(Pos.CENTER);
//get list of books
	        ArrayList<Book> books = Book.getBooks();
			//create observable list for the pi cart
	        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

	        //get values of category  in  a array
	        Category[] catVal = Category.values();
	        String[] categories = new String[8];

			//add the category names n category array
	        for(int i=0; i<8;i++) {
	    		categories[i] = (catVal[i]).name();
	    	}
	        
	        //initialize array to store info
	        int[] quantity = new int[8];
	        
	        int index=0;
	        //iterate through each category and each order to calculate the number sold for each category
	        for(Category c: Category.values()) {
	        	for(Order o : Order.getOrders()) {
	        		if (c.equals(o.getIsbnCategory())) {
                        quantity[index]+=o.getQuantity();
                    }
	        	}
	        	index+=1;
	        }

			//create a pichart for each category and its ordered number
	        for(int i=0; i<8; i++) {
	        pieChartData.add(new PieChart.Data(categories[i],quantity[i]));
	        }
	             
	        		
	    
	     //create pichart withcalculated data
	        final PieChart chart = new PieChart(pieChartData);

	        chart.setTitle("BEST SELLER CATEGORIES");

	        vBox.getChildren().addAll(chart);

	        return vBox;
	    }

}

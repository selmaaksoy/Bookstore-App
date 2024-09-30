package application.bookstore.controllers;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import application.bookstore.models.Book;
import application.bookstore.models.Order;
import application.bookstore.views.BookView;
import application.bookstore.views.OrderView;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;

public class OrderController {
    private final OrderView orderView;

    public OrderController(OrderView orderView) {
        this.orderView = orderView;
        setSaveListener();
        setEditListener();
        setDeleteListener();
        setComboBoxListener();
        setSearchListener();
    }

    private void setSearchListener() {
    	orderView.getSearchView().getClearBtn().setOnAction(e -> {
    		orderView.getSearchView().getSearchField().setText("");
    		orderView.getTableView().setItems(FXCollections.observableArrayList(Order.getOrders()));
        });
    	orderView.getSearchView().getSearchBtn().setOnAction(e -> {
            String searchText = orderView.getSearchView().getSearchField().getText();
            ArrayList<Order> searchResults = Order.getSearchResults(searchText);
            orderView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }
    
  

    private void setComboBoxListener(){
    	orderView.getIsbnComboBox().setOnMouseClicked(e->{
    	  for(Book b: Book.getBooks()) {
    		orderView.getIsbnComboBox().getItems().add(b.getIsbn());
    	}
        });
    }
    
    
    private void setSaveListener() {
        orderView.getSaveBtn().setOnAction(e -> {
            String isbn = orderView.getIsbnComboBox().getValue();
            String clientName = orderView.getClientNameField().getText();
            float price = Float.parseFloat(orderView.getPriceField().getText());
            int quantity = Integer.parseInt(orderView.getQuantityField().getText());
            float total = price * quantity;
            Order order = new Order(isbn, clientName,quantity,price,total);
           if (quantity <= order.getIsbnStock()) {
              if (order.saveInFile()) {
                    orderView.getTableView().getItems().add(order);
                    order.getNewBookStock();//set the new book stock
                    orderView.getResultLabel().setText("Order created successfully");
                    orderView.getResultLabel().setTextFill(Color.GREEN);
                    order.print();
                    resetFields();
                }
            else {
                    orderView.getResultLabel().setText("Book creation failed");
                    orderView.getResultLabel().setTextFill(Color.RED);
                }
           }
           else {
        	   orderView.getResultLabel().setText("Not enought Stock");
               orderView.getResultLabel().setTextFill(Color.RED);
           }
        });
    

           
    }

    private void setDeleteListener(){
        orderView.getDeleteBtn().setOnAction(e->{
            List<Order> itemsToDelete = List.copyOf(orderView.getTableView().getSelectionModel().getSelectedItems());
            for (Order o: itemsToDelete) {
                if (o.deleteFromFile()) {
                	orderView.getTableView().getItems().remove(o);
                	orderView.getResultLabel().setText("Book removed successfully");
                	orderView.getResultLabel().setTextFill(Color.GREEN);
                } else {
                	orderView.getResultLabel().setText("Book deletion failed");
                	orderView.getResultLabel().setTextFill(Color.RED);
                    break;
                }
            }
        });
    }

    private void setEditListener() {
    	orderView.getIsbnCol().setEditable(true);

    	orderView.getClientNameCol().setOnEditCommit(e -> {
            Order orderToEdit = e.getRowValue();
            String orderVal=orderToEdit.getClientName();
            orderToEdit.setClientName(e.getNewValue());
            if (orderToEdit.isValid()){
            	orderToEdit.updateFile();
            }
            else {
                System.out.println("You cannot edit "+e.getNewValue());
                orderToEdit.setClientName(orderVal);
                orderView.getTableView().getItems().set(orderView.getTableView().getItems().indexOf(orderToEdit), orderToEdit);
                orderView.getResultLabel().setText("No edition done");
                orderView.getResultLabel().setTextFill(Color.RED);
            }
        });

    	orderView.getPriceCol().setOnEditCommit(e -> {
    		Order orderToEdit = e.getRowValue();
            float oldVal=orderToEdit.getPrice();
            orderToEdit.setPrice(e.getNewValue());
            if (orderToEdit.isValid()){
            	orderToEdit.updateFile();
            }
            else {
       
            	orderToEdit.setPrice(oldVal);
                orderView.getTableView().getItems().set(orderView.getTableView().getItems().indexOf(orderToEdit), orderToEdit);
                orderView.getResultLabel().setText("Invalid  book data");
                orderView.getResultLabel().setTextFill(Color.RED);
            }
        });
    	
    	
    	orderView.getQuantityCol().setOnEditCommit(e -> {
    		Order orderToEdit = e.getRowValue();
            float oldVal=orderToEdit.getQuantity();
            orderToEdit.setQuantity(e.getNewValue());
            if (orderToEdit.isValid()){
            	orderToEdit.updateFile();
            }
            else {
       
            	orderToEdit.setPrice(oldVal);
                orderView.getTableView().getItems().set(orderView.getTableView().getItems().indexOf(orderToEdit), orderToEdit);
                orderView.getResultLabel().setText("Invalid  book data");
                orderView.getResultLabel().setTextFill(Color.RED);
            }
        });




    }

    private void resetFields() {
       orderView.getClientNameField().setText("");
       orderView.getQuantityField().setText("");
       orderView.getPriceField().setText("");
    }
}


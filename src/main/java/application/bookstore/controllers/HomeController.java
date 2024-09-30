package application.bookstore.controllers;



import application.bookstore.views.AuthorView;
import application.bookstore.views.BookView;
import application.bookstore.views.HomeView;
import application.bookstore.views.MainView;
import application.bookstore.views.OrderView;
import application.bookstore.views.UserView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class HomeController {
    private final HomeView homeView;

    public HomeController(HomeView homeView) {
        this.homeView = homeView;
        setListener();

    }
    private Tab openTab(Tab tab){
        for(Tab t:MainView.getTabPane().getTabs()){
            if(t.getText().equals(tab.getText())){
                return t;
            }
        }
        MainView.getTabPane().getTabs().add(tab);
        return tab;
    }

    private void setListener() {

        homeView.getaList().setOnAction((e)-> {
            Tab authorTab = new Tab("Authors");
            authorTab.setContent(new AuthorView().getView());
            Tab tab=openTab(authorTab);
        });

        homeView.getbList().setOnAction((e)-> {
            Tab booksTab = new Tab("Books");
            booksTab.setContent(new BookView().getView());
            Tab tab=openTab(booksTab);
        });  

        /*homeView.getUsersBtn().setOnAction((e)-> {
            Tab userTab = new Tab("Users");
            userTab.setContent(new UserView().getView());
            Tab tab=openTab(userTab);
        }); */
        
        homeView.getbSold().setOnAction((e)-> {
            Tab orderTab = new Tab("Orders");
            orderTab.setContent(new OrderView().getView());
            Tab tab=openTab(orderTab);
        }); 

            
}
}


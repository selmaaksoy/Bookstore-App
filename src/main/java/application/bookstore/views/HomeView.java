package application.bookstore.views;


import java.util.ArrayList;

import application.bookstore.models.Role;
import application.bookstore.controllers.AuthorController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import application.bookstore.controllers.HomeController;
import application.bookstore.models.Author;
import application.bookstore.models.Role;
import application.bookstore.models.User;
import application.bookstore.ui.CreateButton;
import application.bookstore.ui.DeleteButton;
import application.bookstore.ui.EditButton;


public class HomeView extends View{
	
	
private final BorderPane borderPane = new BorderPane();
//private final Label resultLabel = new Label("");
private final TabPane tabPane = new TabPane();
private final Tab bookListT = new Tab("Book List");
public Button getbList() {
	return bList;
}


public Button getaList() {
	return aList;
}


public Button getUsersBtn() {
	return usersBtn;
}


public Button getbSold() {
	return bSold;
}


private final Button bList = new Button("Book List");
private final Button aList = new Button("Author List");
private final Button usersBtn = new Button("Users List");
private final Button bSold = new Button("Books Sold");



public HomeView() {
    new HomeController(this);
}


@Override
public Parent getView() {

	HBox buttonList = new HBox();
    
    Font font = Font.font("Courier New", FontWeight.BOLD, 25);
    bList.setFont(font);
    aList.setFont(font);
    bSold.setFont(font);
    usersBtn.setFont(font);
    buttonList.getChildren().addAll(bList,aList,bSold);
    buttonList.setSpacing(60);//changed

    
    borderPane.setTop(tabPane);
    buttonList.setPadding(new Insets(40, 100, 0, 100));//changed
    buttonList.setAlignment(Pos.CENTER);
    HBox hbox2 = new HBox();
    Image image1 = new Image("https://i.pinimg.com/564x/68/a1/f5/68a1f5e07173e07d2e8f0071d3f6f9ab.jpg", 200,200 , false, false);
    Image image2 = new Image("https://i.pinimg.com/564x/4c/f7/ad/4cf7ad3679205eca68316f1f75c7cb94.jpg", 200, 200, false, false);
    Image image3 = new Image("https://i.pinimg.com/564x/ab/8e/92/ab8e9207a83b9ea6772f4406f9e2fa71.jpg", 200, 200, false, false);
    ImageView i1= new ImageView(image1);
    ImageView i2= new ImageView(image2);
    ImageView i3= new ImageView(image3);
    hbox2.getChildren().addAll(i1,i2,i3);
    hbox2.setSpacing(100);
   /* Role currentRole = Role.ADMIN;
        if (currentRole == Role.ADMIN) {
        	hbox2.getChildren().add(i4);
        	buttonList.getChildren().add(usersBtn);
        	System.out.println("I'm USER");
        }*/
    hbox2.setAlignment(Pos.TOP_CENTER);
    hbox2.setPadding(new Insets(200, 60, 0, 90));//changed
    borderPane.setCenter(hbox2);
    borderPane.setBottom(buttonList);

    return borderPane;
}

}
package application.bookstore.views;

import application.bookstore.controllers.MainController;
import application.bookstore.models.Role;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainView extends View {
    private final MenuBar menuBar = new MenuBar();


    private final Menu mainMenu= new Menu("Home");
    /*private final MenuItem viewBooks = new MenuItem("View Books");
    private final MenuItem viewAuthors = new MenuItem("View Authors");
    private final MenuItem viewOrders = new MenuItem("View Orders");*/

    private final Label changePassMenuLabel = new Label("Change Password");
    private final Menu changePassMenu= new Menu("", changePassMenuLabel);

    private final Label logoutMenuLabel = new Label("Logout");
    private final Menu logoutMenu = new Menu("", logoutMenuLabel);

    private final Menu viewUsers   = new Menu(" Users List");
    private final MenuItem manageUsers = new MenuItem("Manage Users");


    private final Menu viewStatistics   = new Menu("Statistics");
    private final MenuItem stat1 = new MenuItem("Books Categories");
    private final MenuItem stat2 = new MenuItem("Best Sellers Categories");



    public MainView(Stage mainStage){
        new MainController(this, mainStage);
    }


    private static final TabPane tabPane = new TabPane();

    @Override
    public Parent getView() {
        BorderPane borderPane = new BorderPane();

        viewStatistics.getItems().addAll(stat1,stat2);
        menuBar.getMenus().addAll(mainMenu,changePassMenu, logoutMenu);


        Tab defaultTab = new Tab("Home");
        defaultTab.setContent(new HomeView().getView());
        
        Tab stat1Tab = new Tab("Book Categories");
        stat1Tab.setContent(new StatisticView().getView());
        
        Tab stat2Tab = new Tab("Best Seller");
        stat2Tab.setContent(new StatisticBestSellerCategory().getView());
        
        Tab soldTab = new Tab("Orders");
        soldTab.setContent(new OrderView().getView());
        
        Tab userTab = new Tab("User");
        userTab.setContent(new UserView().getView());


        Role currentRole = (getCurrentUser() != null ? getCurrentUser().getRole() : null);
        if (currentRole != null) {
            if (currentRole == Role.ADMIN) {
                menuBar.getMenus().add(viewUsers);
                viewUsers.getItems().add(manageUsers);
            }
           if (currentRole == Role.MANAGER || currentRole == Role.ADMIN) {
        	   menuBar.getMenus().add(viewStatistics);
            }
           tabPane.getTabs().add(defaultTab);
        }

        VBox topPane = new VBox();
        topPane.getChildren().addAll(menuBar, tabPane);
        borderPane.setTop(topPane);

        borderPane.setBottom(new StackPane(new Text(getCurrentUser().getUsername() + ", welcome to our bookstore")));
        return borderPane;
    }


   
    public MenuBar getMenuBar() {
		return menuBar;
	}



	public Menu getMainMenu() {
		return mainMenu;
	}




	public Label getChangePassMenuLabel() {
		return changePassMenuLabel;
	}



	public Menu getChangePassMenu() {
		return changePassMenu;
	}



	public Menu getLogoutMenu() {
		return logoutMenu;
	}



	public Menu getViewUsers() {
		return viewUsers;
	}



	public Menu getViewStatistics() {
		return viewStatistics;
	}



	public MenuItem getStat1() {
		return stat1;
	}



	public MenuItem getStat2() {
		return stat2;
	}



	public Label getLogoutMenuLabel() {
        return logoutMenuLabel;
    }

    public static TabPane getTabPane() {
        return tabPane;
    }

}

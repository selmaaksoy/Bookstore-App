package application.bookstore.controllers;
import application.bookstore.models.User;
import application.bookstore.views.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainController {
    private final MainView mainView;
    private final Stage mainStage;

    public MainController(MainView mainView, Stage mainStage) {
        this.mainView = mainView;
        this.mainStage = mainStage;
        setListener();

    }
    private Tab openTab(Tab tab){
        for(Tab t:mainView.getTabPane().getTabs()){
            if(t.getText().equals(tab.getText())){
                return t;
            }
        }
        mainView.getTabPane().getTabs().add(tab);
        return tab;
    }

    private void setListener() {

        
        mainView.getStat1().setOnAction(e->{
            Tab stats1 = new Tab("Book Categories");
            stats1.setContent(new StatisticView().getView());
            Tab tab=openTab(stats1);
        });
        
        mainView.getStat2().setOnAction(e->{
            Tab stats2 = new Tab("Best Sellers Categories");
            stats2.setContent(new StatisticBestSellerCategory().getView());
            Tab tab=openTab(stats2);
        });
        
        
        mainView.getViewUsers().setOnAction(e->{
            Tab userTab = new Tab("Users");
            userTab.setContent(new UserView().getView());
            Tab tab=openTab(userTab);
        });

        mainView.getChangePassMenuLabel().setOnMouseClicked((e)->{


            TextField oldPassField = new TextField();
            TextField newPassField = new TextField();
            TextField newPassField1 = new TextField();
            Label oldPassLabel = new Label("OLD PASSWORD: ", oldPassField);
            oldPassLabel.setContentDisplay(ContentDisplay.RIGHT);
            Label newPassLabel = new Label("NEW PASSWORD: ", newPassField);
            newPassLabel.setContentDisplay(ContentDisplay.RIGHT);
            Label newPassLabel1 = new Label("NEW PASSWORD: ", newPassField1);
            newPassLabel1.setContentDisplay(ContentDisplay.RIGHT);
            HBox hb1 = new HBox();
            hb1.setAlignment(Pos.CENTER);
            Button okBtn = new Button("OK");
            Button cancelBtn = new Button("CANCEL");
            hb1.getChildren().addAll(okBtn, cancelBtn);
            Label messgaeLabel = new Label("");
            VBox v = new VBox();
            hb1.setSpacing(10);
            v.setAlignment(Pos.CENTER);
            v.getChildren().addAll(oldPassLabel, newPassLabel, newPassLabel1, messgaeLabel, hb1);
            v.setSpacing(10);

            Scene secondScene = new Scene(v, 400, 400);

            Stage window1 = new Stage();
            window1.setTitle("Change Password");
            window1.setScene(secondScene);
            window1.initModality(Modality.WINDOW_MODAL);
            window1.initOwner(mainStage);

            cancelBtn.setOnMousePressed(ev->window1.close());
            okBtn.setOnMousePressed(ev->{
                if (oldPassField.getText().equals(mainView.getCurrentUser().getPassword())){
                    if (newPassField.getText().matches(newPassField1.getText())){
                        User user = new User(mainView.getCurrentUser().getUsername(), newPassField.getText(), mainView.getCurrentUser().getRole());
                        if (user.isValid()){
                            mainView.getCurrentUser().deleteFromFile();
                            mainView.setCurrentUser(user);
                            user.saveInFile();
                            window1.close();
                        }
                        else{
                            messgaeLabel.setText("New Password Invalid!");
                            messgaeLabel.setTextFill(Color.DARKRED);
                        }
                    }
                    else{
                        messgaeLabel.setText("New Passwords do not match!");
                        messgaeLabel.setTextFill(Color.DARKRED);
                    }
                }
                else{
                    messgaeLabel.setText("Old Password Incorrect!");
                    messgaeLabel.setTextFill(Color.DARKRED);
                }
            });

            window1.show();
        });

        mainView.getLogoutMenuLabel().setOnMouseClicked((e)->{
            LoginView loginView = new LoginView();
            new LoginController(loginView, new MainView(mainStage), mainStage);
            Scene scene = new Scene(loginView.getView(), 600, 300);
            mainStage.setScene(scene);
        });
    }

}

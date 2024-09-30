module application.bookstore {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;



    opens application.bookstore to javafx.graphics, javafx.fxml;
    opens application.bookstore.models to javafx.base;

    exports application.bookstore;
    exports application.bookstore.models;

}

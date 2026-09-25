module com.cse213project.bookfair {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cse213project.bookfair to javafx.fxml;
    exports com.cse213project.bookfair;
}
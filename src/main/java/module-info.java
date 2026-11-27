module com.cse213project.bookfair {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.cse213project.bookfair to javafx.fxml;
    opens com.cse213project.bookfair.Raquibul.controller to javafx.fxml;
    opens com.cse213project.bookfair.Raquibul.model to javafx.base;
    opens com.cse213project.bookfair.Raquibul.dummy to javafx.base;

    exports com.cse213project.bookfair;
    exports com.cse213project.bookfair.Raquibul.controller;
    exports com.cse213project.bookfair.Raquibul.model;
    exports com.cse213project.bookfair.Raquibul.dummy;
    exports com.cse213project.bookfair.Raquibul.util;
}

package com.cse213project.bookfair;

import com.cse213project.bookfair.Raquibul.util.DummyDataInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EkusheyBoimelaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DummyDataInitializer.initializeIfMissing();
        FXMLLoader fxmlLoader = new FXMLLoader(
                EkusheyBoimelaApplication.class.getResource("Raquibul/LoginView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Ekushey Boimela Management System - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

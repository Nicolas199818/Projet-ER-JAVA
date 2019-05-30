package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static AnchorPane idPage;

    @Override
    public void start(Stage stage) throws IOException {
        idPage = FXMLLoader.load(App.class.getResource("/identification_page.fxml"));
        stage.setScene(new Scene(idPage));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
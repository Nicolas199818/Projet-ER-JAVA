package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class IdentificationPage {

    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private Button connecter;

    @FXML
    public void test(ActionEvent event){
        /*C'est ici que l'on mettra le code pour checker que ça marche avec la base*/

        BorderPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}

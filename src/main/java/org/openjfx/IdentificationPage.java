package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

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
        NetworkService network = new NetworkService();
        // nicolass@gmail.com  nicolas
        if(network.signInUser(login.getText(),password.getText())==200){
            //System.out.println("sa mère la pute ça bug et je sais pas pourquoi !!!!!!");
            Node source = (Node) event.getSource();
            Window theStage = source.getScene().getWindow();
            Stage currentStage = (Stage)theStage.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        }
        else {
            System.out.println("sa mère la pute ça bug et je sais pas pourquoi !!!!!!");

        }
    }
}

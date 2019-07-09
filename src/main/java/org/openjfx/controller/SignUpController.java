package org.openjfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.openjfx.service.NetworkService;

import java.io.IOException;

public class SignUpController {
    //On récupère les éléments graphiques :
    @FXML
    private TextField lastname;
    @FXML
    private TextField firstname;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private Button seConnecter;
    @FXML
    private Label labelerror;

    @FXML
    public void seConnecter(ActionEvent event){
        BorderPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/bugs.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        NetworkService network = new NetworkService();
        if(network.signUpUser(lastname.getText(),firstname.getText(),login.getText(),password.getText())==200){
            Node source = (Node) event.getSource();
            Window theStage = source.getScene().getWindow();
            Stage currentStage = (Stage)theStage.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
        }
        else {
            //On affiche un message en rouge en bas de la page.
            labelerror.setWrapText(true);
            labelerror.setVisible(true);

        }
    }

    @FXML
    public void toSignIn(ActionEvent event){
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();
        Stage currentStage = (Stage)theStage.getScene().getWindow();
        AnchorPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/identification_page.fxml"));
            currentStage.setScene(new Scene(root));
            currentStage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

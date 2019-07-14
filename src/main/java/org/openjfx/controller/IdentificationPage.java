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


public class IdentificationPage {

    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private Button connecter;
    @FXML
    private Label labelerror;

    @FXML
    public void test(ActionEvent event){

        BorderPane root = null;

        NetworkService network = NetworkService.getInstance();
        if(network.signInUser(login.getText(),password.getText())==200){
            try {
                root = FXMLLoader.load(getClass().getResource("/main_pages.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Node source = (Node) event.getSource();
            Window theStage = source.getScene().getWindow();
            Stage currentStage = (Stage)theStage.getScene().getWindow();
            currentStage.close();

            Stage newStage = new Stage();
            newStage.setMinWidth(1400);
            newStage.setMinHeight(800);
            newStage.setScene(new Scene(root));
            newStage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
        }
        else {
            labelerror.setText("Connexion failed");
            labelerror.setWrapText(true);
            labelerror.setVisible(true);

        }
    }

    @FXML
    public void toSignUp(ActionEvent event){
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();
        Stage currentStage = (Stage)theStage.getScene().getWindow();
        AnchorPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sign_up.fxml"));
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

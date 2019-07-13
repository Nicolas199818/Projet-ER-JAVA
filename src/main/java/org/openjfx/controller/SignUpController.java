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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        NetworkService network = NetworkService.getInstance();
        if(this.isPasswordOk(password.getText())&&this.isEmailOk(login.getText())&&this.isFirstAndLastNameOk(lastname.getText(),firstname.getText()))
        {
            if (network.signUpUser(lastname.getText(), firstname.getText(), login.getText(), password.getText()) == 200) {
                try {
                    root = FXMLLoader.load(getClass().getResource("/main_pages.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Node source = (Node) event.getSource();
                Window theStage = source.getScene().getWindow();
                Stage currentStage = (Stage) theStage.getScene().getWindow();
                currentStage.close();

                Stage newStage = new Stage();
                newStage.setMinWidth(1400);
                newStage.setMinHeight(800);
                newStage.setScene(new Scene(root));
                newStage.show();
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
                currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
            } else {
                //On affiche un message en rouge en bas de la page.
                labelerror.setText("Connexion problem");
                labelerror.setWrapText(true);
                labelerror.setVisible(true);

            }
        }
        else {
            labelerror.setText(this.messageConnexion(firstname.getText(),lastname.getText(),password.getText(),login.getText()));
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

    //On fait une fonction qui permet de déterminer si un paramètre est bon ou pas
    private boolean isFirstAndLastNameOk(String lastname,String firstname){
        //On test pour la longueur des noms et des prénoms.
        if(lastname.length()<2 && lastname.length()>50){
            return false;
        }
        if(firstname.length()<2 && firstname.length()>50){
            return false;
        }
        //On test les caractères spéciaux :

        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(firstname);
        boolean nameSpecial = matcher.find();

        if (nameSpecial){
            return false;
        }
        pattern.matcher(lastname);
        boolean firstNameSpecial = matcher.find();
        if(firstNameSpecial){
            return false;
        }
        return true;
    }

    //On fait une fonction pour checker si le password est ok :
    private boolean isPasswordOk(String password){
        if(password.length()<4 && password.length()>50){
            return false;
        }
        return true;
    }

    private boolean isEmailOk(String email){
        if(!email.contains("@")){
            return false;
        }
        else{
            return true;
        }
    }

    private String messageConnexion(String firstname,String lastname,String password,String email){
        if(isFirstAndLastNameOk(firstname,lastname)){
            return "Firstname and lastname must be between 2 et 50 characters without special characters";
        }
        if(this.isEmailOk(email)){
            return "Email is not valid";
        }
        if(this.isPasswordOk(password)){
            return "Password length must be between 4 et 50 caractères";
        }
        return "Ok";
    }

}

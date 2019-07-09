package org.openjfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPages implements Initializable {
    //On référence le Pane
    @FXML
    private Pane content;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void toStatistics(ActionEvent event){
        content.getChildren().clear();
        try {
            content.getChildren().add(FXMLLoader.load(getClass().getClassLoader().getResource("statistics.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void toBug(ActionEvent event){
        content.getChildren().clear();
        try {
            content.getChildren().add(FXMLLoader.load(getClass().getClassLoader().getResource("bugs.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void toEvolution(ActionEvent event){
        content.getChildren().clear();
        try {
            content.getChildren().add(FXMLLoader.load(getClass().getClassLoader().getResource("evolution_activite.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

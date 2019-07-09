package org.openjfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.openjfx.data.Bug;
import org.openjfx.service.NetworkService;

import java.net.URL;
import java.util.ResourceBundle;

public class PopUpBugControler implements Initializable {
    @FXML
    private Label label_id;
    @FXML
    private Label label_date_creation;
    @FXML
    private Label label_content;
    @FXML
    private Label label_type;
    @FXML
    private Label label_status;
    @FXML
    private Label label_id_user;

    private TableView<Bug> table;


    //On référence les différents labels que l'on voudra modifier.

    public PopUpBugControler(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    //On catch le click sur Annuler pour fermer la pop Up
    @FXML
    public void annuler(ActionEvent event){
        System.out.println("nicolas");
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();
        Stage currentStage = (Stage)theStage.getScene().getWindow();
        currentStage.close();

    }

    @FXML
    public void resoudre(ActionEvent event){
        //Etape 1 : Faire une méthode dans le Network qui permet de réaliser la résolution d'un bug.
        //Etape 2 : L'appelé ici.
        NetworkService network = new NetworkService();
        network.resolveBug(this.getLabel_id().getText(),1);
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();
        Stage currentStage = (Stage)theStage.getScene().getWindow();
        Bug selectedItem = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(selectedItem);
        currentStage.close();
    }
    //On fait un constructeur du controller. Ce qui permet de passer les données.


    public Label getLabel_id() {
        return label_id;
    }

    public Label getLabel_date_creation() {
        return label_date_creation;
    }

    public Label getLabel_content() {
        return label_content;
    }

    public Label getLabel_type() {
        return label_type;
    }

    public Label getLabel_status() {
        return label_status;
    }

    public Label getLabel_id_user() {
        return label_id_user;
    }

    public void setTable(TableView<Bug> tableView){
        this.table = tableView;
    }
}

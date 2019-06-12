package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

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

    private String id;
    private String date_creation;
    private String content;
    private String type;
    private String status;
    private String id_user;


    //On référence les différents labels que l'on voudra modifier.

    public PopUpBugControler(){

    }

    public PopUpBugControler(String id, String date_creation, String content, String type, String status, String id_user) {
        this.id = id;
        this.date_creation = date_creation;
        this.content = content;
        this.type = type;
        this.status = status;
        this.id_user = id_user;
        System.out.println("marche pas ----------- "+this.content);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.label_id.setText(id);
        this.label_date_creation.setText(date_creation);
        this.label_content.setText(content);
        this.label_type.setText(type);
        this.label_status.setText(status);
        this.label_id_user.setText(id_user);
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
}

package org.openjfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAccueil implements Initializable {

    @FXML
    private TableView myTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("ta mère :"+myTable.getColumns().get(0));
        //On va référencer les différentes colonnes pour ensuite les remplirs
        javafx.scene.control.TableColumn idBug = (javafx.scene.control.TableColumn) myTable.getColumns().get(0);
        TableColumn dateCreation = (TableColumn) myTable.getColumns().get(1);
        TableColumn contenu = (TableColumn) myTable.getColumns().get(2);
        TableColumn type = (TableColumn) myTable.getColumns().get(3);
        TableColumn status = (TableColumn) myTable.getColumns().get(4);
        TableColumn user = (TableColumn) myTable.getColumns().get(5);


        //On initialise une liste d'observable qui correspondent aux données que l'on veut rentrer dans la table
        ObservableList<Bug> data = FXCollections.observableArrayList();
        data.add(new Bug(0,"12/02/2019","La map ne s'affiche pas bien, on me montre l'océan pacifique","Bug d'affichage","En attente de prise en compte",1));
        data.add(new Bug(1,"12/02/2019","La map ne s'affiche pas bien, on me montre l'océan pacifique","Bug d'affichage","En attente de prise en compte",1));
        data.add(new Bug(2,"12/02/2019","La map ne s'affiche pas bien, on me montre l'océan pacifique","Bug d'affichage","En attente de prise en compte",1));
        data.add(new Bug(3,"12/02/2019","La map ne s'affiche pas bien, on me montre l'océan pacifique","Bug d'affichage","En attente de prise en compte",1));
        data.add(new Bug(4,"12/02/2019","La map ne s'affiche pas bien, on me montre l'océan pacifique","Bug d'affichage","En attente de prise en compte",1));

        //On essaye d'associer les données avec les données avec les colonnes.
        idBug.setCellValueFactory(new PropertyValueFactory<Bug,String>("idBug"));
        dateCreation.setCellValueFactory(new PropertyValueFactory<Bug,String>("date"));
        contenu.setCellValueFactory(new PropertyValueFactory<Bug,String>("contenu"));
        type.setCellValueFactory(new PropertyValueFactory<Bug,String>("type"));
        status.setCellValueFactory(new PropertyValueFactory<Bug,String>("status"));
        user.setCellValueFactory(new PropertyValueFactory<Bug,String>("user"));

        //On met les données dans le table :
        myTable.setItems(data);

    }
}

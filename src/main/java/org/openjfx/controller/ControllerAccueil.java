package org.openjfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.openjfx.data.Bug;
import org.openjfx.service.NetworkService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAccueil implements Initializable {

    @FXML
    private TableView myTable;

    private TableColumn idBug;
    private TableColumn dateCreation;
    private TableColumn contenu;
    private TableColumn type;
    private TableColumn status;
    private TableColumn user;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initialiseColumn();
        NetworkService networkService = new NetworkService();
        //On initialise une liste d'observable qui correspondent aux données que l'on veut rentrer dans la table
        List<Bug> data = networkService.getListBugByStatut(0);
        this.associateColumnBugData();


        //On met les données dans le table :
        myTable.setItems(this.transformToObservable(data));

        // On rend le double clic possible. :
        myTable.setRowFactory( tv -> {
            TableRow<Bug> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Bug rowData = row.getItem();
                    //C'est le moment où l'on décide ce que l'on veut faire.
                    System.out.println(rowData);
                    Stage stage = new Stage();
                    AnchorPane idPage = null;
                    try {
                        System.out.println("Controller Accueil  ----------"+rowData.getContenu());
                        //PopUpBugControler dialogController = new PopUpBugControler(rowData.getIdBug(),rowData.getDate(),rowData.getContenu(),rowData.getType(),rowData.getStatus(),rowData.getUser());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pop_up_bug.fxml"));
                        idPage = (AnchorPane) loader.load();
                        PopUpBugControler controler = loader.getController();
                        controler.getLabel_id().setText(rowData.getIdBug());
                        controler.getLabel_date_creation().setText(rowData.getDate());
                        controler.getLabel_content().setText(rowData.getContenu());
                        controler.getLabel_type().setText(rowData.getType());
                        controler.getLabel_status().setText(rowData.getStatus());
                        controler.getLabel_id_user().setText(rowData.getUser());
                        controler.setTable(myTable);


                        //idPage = FXMLLoader.load(App.class.getResource("/pop_up_bug.fxml"));
                        stage.setScene(new Scene(idPage));
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            return row ;
        });
    }

    //On fait une fonction pour initialiser les colonnes.
    private void initialiseColumn(){
        this.idBug = (javafx.scene.control.TableColumn) myTable.getColumns().get(0);
        this.dateCreation = (TableColumn) myTable.getColumns().get(1);
        this.contenu = (TableColumn) myTable.getColumns().get(2);
        this.type = (TableColumn) myTable.getColumns().get(3);
        this.status = (TableColumn) myTable.getColumns().get(4);
        this.user = (TableColumn) myTable.getColumns().get(5);
    }

    //Associer les données des objets Bugs avec
    private void associateColumnBugData(){
        idBug.setCellValueFactory(new PropertyValueFactory<Bug,String>("idBug"));
        dateCreation.setCellValueFactory(new PropertyValueFactory<Bug,String>("date"));
        contenu.setCellValueFactory(new PropertyValueFactory<Bug,String>("contenu"));
        type.setCellValueFactory(new PropertyValueFactory<Bug,String>("type"));
        status.setCellValueFactory(new PropertyValueFactory<Bug,String>("status"));
        user.setCellValueFactory(new PropertyValueFactory<Bug,String>("user"));
    }

    //On fait une fonction qui transforme une list en une observableList.
    private ObservableList transformToObservable(List list){
        return FXCollections.observableList(list);
    }

    //On fait une fonction qui à partir d'un bug ouvrir la popUp
    public void openPopUpByBug(){
        
    }
}

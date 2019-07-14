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
        NetworkService networkService = NetworkService.getInstance();
        List<Bug> data = networkService.getListBugByStatut(0);
        this.associateColumnBugData();


        myTable.setItems(this.transformToObservable(data));
        myTable.setRowFactory( tv -> {
            TableRow<Bug> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Bug rowData = row.getItem();
                    this.openPopUpByBug(rowData);

                }
            });
            return row ;
        });
    }

    private void initialiseColumn(){
        this.idBug = (javafx.scene.control.TableColumn) myTable.getColumns().get(0);
        this.dateCreation = (TableColumn) myTable.getColumns().get(1);
        this.contenu = (TableColumn) myTable.getColumns().get(2);
        this.type = (TableColumn) myTable.getColumns().get(3);
        this.status = (TableColumn) myTable.getColumns().get(4);
        this.user = (TableColumn) myTable.getColumns().get(5);
    }

    private void associateColumnBugData(){
        idBug.setCellValueFactory(new PropertyValueFactory<Bug,String>("idBug"));
        dateCreation.setCellValueFactory(new PropertyValueFactory<Bug,String>("date"));
        contenu.setCellValueFactory(new PropertyValueFactory<Bug,String>("contenu"));
        type.setCellValueFactory(new PropertyValueFactory<Bug,String>("type"));
        status.setCellValueFactory(new PropertyValueFactory<Bug,String>("status"));
        user.setCellValueFactory(new PropertyValueFactory<Bug,String>("user"));
    }

    private ObservableList transformToObservable(List list){
        return FXCollections.observableList(list);
    }

    public void openPopUpByBug(Bug rowData){
        Stage stage = new Stage();
        AnchorPane idPage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pop_up_bug.fxml"));
            idPage = (AnchorPane) loader.load();
            PopUpBugControler controler = loader.getController();
            controler.getLabel_id().setText(rowData.getIdBug());
            controler.getLabel_date_creation().setText(rowData.getDate());
            controler.getText_content().setText(rowData.getContenu());
            controler.getLabel_type().setText(rowData.getType());
            controler.getLabel_status().setText(rowData.getStatus());
            controler.getLabel_id_user().setText(rowData.getUser());
            controler.setTable(myTable);


            stage.setScene(new Scene(idPage));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

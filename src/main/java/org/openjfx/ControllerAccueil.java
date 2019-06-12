package org.openjfx;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAccueil implements Initializable {

    @FXML
    private TableView myTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //On va référencer les différentes colonnes pour ensuite les remplirs
        javafx.scene.control.TableColumn idBug = (javafx.scene.control.TableColumn) myTable.getColumns().get(0);
        TableColumn dateCreation = (TableColumn) myTable.getColumns().get(1);
        TableColumn contenu = (TableColumn) myTable.getColumns().get(2);
        TableColumn type = (TableColumn) myTable.getColumns().get(3);
        TableColumn status = (TableColumn) myTable.getColumns().get(4);
        TableColumn user = (TableColumn) myTable.getColumns().get(5);

        NetworkService networkService = new NetworkService();
        networkService.getListBug(0);
        //On initialise une liste d'observable qui correspondent aux données que l'on veut rentrer dans la table
        ObservableList<Bug> data = networkService.getListBug(0);
        data.add(new Bug("0","12/02/2019","La map ne s'affiche pas bien, on me montre l'océan pacifique","Bug d'affichage",0,"1"));
        data.add(new Bug("1","12/02/2019","La map ne s'affiche pas bien, on me montre l'océan pacifique","Bug d'affichage",0,"1"));
        data.add(new Bug("2","12/02/2019","La map ne s'affiche pas bien, on me montre l'océan pacifique","Bug d'affichage",0,"1"));
        data.add(new Bug("3","12/02/2019","La map ne s'affiche pas bien, on me montre l'océan pacifique","Bug d'affichage",0,"1"));
        data.add(new Bug("4","12/02/2019","La map ne s'affiche pas bien, on me montre l'océan pacifique","Bug d'affichage",0,"1"));



        //On essaye d'associer les données avec les données avec les colonnes.
        idBug.setCellValueFactory(new PropertyValueFactory<Bug,String>("idBug"));
        dateCreation.setCellValueFactory(new PropertyValueFactory<Bug,String>("date"));
        contenu.setCellValueFactory(new PropertyValueFactory<Bug,String>("contenu"));
        type.setCellValueFactory(new PropertyValueFactory<Bug,String>("type"));
        status.setCellValueFactory(new PropertyValueFactory<Bug,String>("status"));
        user.setCellValueFactory(new PropertyValueFactory<Bug,String>("user"));

        //On met les données dans le table :
        myTable.setItems(data);

        // On rend le double clic possible. :
        myTable.setRowFactory( tv -> {
            TableRow<Bug> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Bug rowData = row.getItem();
                    //C'est le moment où l'on décide ce que l'on veut faire.
                    //System.out.println(rowData);
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
}

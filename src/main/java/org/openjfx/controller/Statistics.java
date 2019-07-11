package org.openjfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.openjfx.data.Company;
import org.openjfx.data.ExpenseReport;
import org.openjfx.service.NetworkService;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class Statistics implements Initializable {
    //On récupère les différents éléments avec FXML :
    @FXML
    private Label totalERCircle;
    @FXML
    private Label totalArgentCircle;
    @FXML
    private Label totalCompanyCircle;
    @FXML
    private Label totalBugCircle;
    @FXML
    private PieChart pieChart;
    @FXML
    private PieChart pieChartEr;
    @FXML
    private PieChart pieChartUser;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        NetworkService network = new NetworkService();
        network.getListExpenseReport();

        totalERCircle.setText(""+network.getListExpenseReport().size()+"\n expense report");
        totalArgentCircle.setText(""+getSommeERByList(network.getListExpenseReport())+"\n dollars");
        totalCompanyCircle.setText(""+network.getListCompany().size()+"\n companies");
        totalBugCircle.setText(""+network.getListExpenseReport().size()+"\n bugs");


        //On va Initialiser les graphiques
        pieChart.setLabelsVisible(false);
        pieChartEr.setLabelsVisible(false);
        pieChartUser.setLabelsVisible(false);
        if(getListCompanyPieChart().size()>0){
            pieChart.setData(getListCompanyPieChart());
        }
        else {
            ObservableList<PieChart.Data> emptyChart = FXCollections.observableArrayList();
            emptyChart.add(new PieChart.Data("aucune valeur",20));
            pieChart.setData(emptyChart);
        }
        if(this.getListCompaniePieChartER().size()>0){
            pieChartEr.setData(this.getListCompaniePieChartER());
        }
        else{
            ObservableList<PieChart.Data> emptyChart = FXCollections.observableArrayList();
            emptyChart.add(new PieChart.Data("aucune valeur",20));
            pieChartEr.setData(emptyChart);
        }
        if(getListCompaniePieChartUser().size()>0){
            ObservableList<PieChart.Data> emptyChart = FXCollections.observableArrayList();
            emptyChart.add(new PieChart.Data("aucune valeur",20));
            pieChartUser.setData(this.getListCompaniePieChartUser());
        }
        else {
            ObservableList<PieChart.Data> emptyChart = FXCollections.observableArrayList();
            emptyChart.add(new PieChart.Data("aucune valeur",20));
            pieChartUser.setData(emptyChart);
        }
        //pieChart.setData(getListCompanyPieChart());

        //pieChartEr.setData(this.getListCompaniePieChartER());
        //pieChartUser.setData(this.getListCompaniePieChartUser());





    }

    @FXML
    public void toBug(ActionEvent event){

        BorderPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/bugs.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();
        Stage currentStage = (Stage)theStage.getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
        currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
    }

    //On fait une fonction qui permet de récupérer la somme à partir d'une liste de notes de frais :
    private int getSommeERByList(List<ExpenseReport> listER){
        int somme = 0;
        for(ExpenseReport er:listER){
            somme += er.getPrice();
        }
        return somme;
    }

    //On fait une fonction qui permet de renvoyer une observable avec les 5 plus grosse entreprises ainsi qu'une case "Autre" en fonction de l'argent.
    public ObservableList<PieChart.Data> getListCompanyPieChart(){
        ObservableList<PieChart.Data> listData = FXCollections.observableArrayList();
        NetworkService network = new NetworkService();
        if(network.getListCompany().size()<5){
            //Pour toutes les companies que l'on a : On prend les companies et on prend la liste des notes de frais de cette companies en calculant le total
            for(Company company:network.getListCompany()){
                System.out.println(" Le diagramme ne s'affiche pas "+company.getIdCompany());
                if(network.getListExpenseReportByCompany(company.getIdCompany())!=null){
                    listData.add(new PieChart.Data(company.getName()+" : "+getSommeERByList(network.getListExpenseReportByCompany(company.getIdCompany())),getSommeERByList(network.getListExpenseReportByCompany(company.getIdCompany()))));
                }
            }
        }
        else {
            List<Company> listCompany = network.getListCompany();
            Collections.sort(listCompany, new Comparator<Company>() {
                @Override
                public int compare(Company fruit2, Company fruit1)
                {

                    return  new Integer(getSommeERByList(network.getListExpenseReportByCompany(fruit1.getIdCompany()))).compareTo(new Integer(getSommeERByList(network.getListExpenseReportByCompany(fruit2.getIdCompany()))));
                }
            });
            for(int i=0;i < 5;i++){
                listData.add(new PieChart.Data(listCompany.get(i).getName()+" : "+getSommeERByList(network.getListExpenseReportByCompany(listCompany.get(i).getIdCompany())),getSommeERByList(network.getListExpenseReportByCompany(listCompany.get(i).getIdCompany()))));
                //System.out.println("DEBUG - On entre bien dans le cas où il y a plus de 5 companies");
            }

        }
        return listData;
    }

    //On fait une fonction qui envoit un observable avec les compagnies en fonction du nombre de notes de frais :
    public ObservableList<PieChart.Data> getListCompaniePieChartER(){
        ObservableList<PieChart.Data> listData = FXCollections.observableArrayList();
        NetworkService network = new NetworkService();
        if(network.getListCompany().size()<5){
            for(Company company:network.getListCompany()){
                if(network.getListExpenseReportByCompany(company.getIdCompany())!=null){
                    listData.add(new PieChart.Data(company.getName()+" : "+network.getListExpenseReportByCompany(company.getIdCompany()).size(),network.getListExpenseReportByCompany(company.getIdCompany()).size()));
                }
            }
        }
        else {
            List<Company> listCompany = network.getListCompany();
            Collections.sort(listCompany, new Comparator<Company>() {
                @Override
                public int compare(Company fruit2, Company fruit1)
                {

                    return  new Integer(network.getListExpenseReportByCompany(fruit1.getIdCompany()).size()).compareTo(new Integer(network.getListExpenseReportByCompany(fruit2.getIdCompany()).size()));
                }
            });
            for(int i=0;i < 5;i++){
                listData.add(new PieChart.Data(listCompany.get(i).getName()+" : "+network.getListExpenseReportByCompany(listCompany.get(i).getIdCompany()).size(),network.getListExpenseReportByCompany(listCompany.get(i).getIdCompany()).size()));

                //System.out.println("DEBUG - On entre bien dans le cas où il y a plus de 5 companies");
            }
        }



        return listData;
    }


    //A partir du java, on va envoyer l'id de la company. On recevra alors les différents utilisateurs :
    public ObservableList<PieChart.Data> getListCompaniePieChartUser(){
        ObservableList<PieChart.Data> listData = FXCollections.observableArrayList();
        NetworkService network = new NetworkService();
        if(network.getListCompany().size()<5){
            for(Company company:network.getListCompany()){
                if(network.getListUserByCompany(company.getIdCompany())!=null){
                    listData.add(new PieChart.Data(company.getName()+" : "+network.getListUserByCompany(company.getIdCompany()).size(),network.getListUserByCompany(company.getIdCompany()).size()));
                }
            }
        }
        else {
            List<Company> listCompany = network.getListCompany();
            Collections.sort(listCompany, new Comparator<Company>() {
                @Override
                public int compare(Company fruit2, Company fruit1)
                {

                    return  new Integer(network.getListUserByCompany(fruit1.getIdCompany()).size()).compareTo(new Integer(network.getListUserByCompany(fruit2.getIdCompany()).size()));
                }
            });
            for(int i=0;i < listCompany.size();i++){
                listData.add(new PieChart.Data(listCompany.get(i).getName()+" : "+network.getListUserByCompany(listCompany.get(i).getIdCompany()).size(),network.getListUserByCompany(listCompany.get(i).getIdCompany()).size()));
                System.out.println("DEBUG - On entre bien dans le cas où il y a plus de 5 companies de la muerte");
            }
        }










        return listData;
    }

}

//Comment est-ce que tu vas organiser les statistiques ?
    //Les Chiffres Clés : On a le nombre de notes de frais / La total argent en jeux. / nombre total d'entreprise / Nombre total de Bugs.
    //Un diagramme de l'argent dépensés par entreprise.
    //Les différents indicateurs par entreprises.
    //Un diagramme de l'argent dépensés par domaine.
    //Un diagramme permettant de voir l'écolution des dépenses sur les derniers mois.
    //Un diagramme permettant de suivre l'évolution des bugs dans les derniers mois.
    //Un diagramme permettant de suivre l'évolution du nombre d'entreprise au ful des mois.
    //Un diagramme permettant du suivre l'évolution du nombre de notes de frais.


//On veut récupérer la liste des entreprises :
    //Faire la requete dans POSTMAN pour créer deux companies
    //On récupère le diagramme des 5 plus grandes entreprises en mettant leurs noms et entre parenthèdes la thune qu'elles rapportent ?

//PROBLEME :
//Il faut faire un utilisateur Java qui puisse prendre les données sans problème de token.
package org.openjfx;

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
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
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
    private PieChart pieChartBug;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //On fait un champs dans lequel, on met le nombre de notes de frais.
        //On récupère la liste des companies.
        //On récupère la liste des types de notes de frais.
        //On récupère la somme totale dépensé.
        //On récupère la somme par entreprise et par domaine.

        //On teste la récupération des bugs :
        NetworkService network = new NetworkService();
        network.getListExpenseReport();
        System.out.println("Test de la taille de la liste : "+network.getListExpenseReport().size());

        totalERCircle.setText(""+network.getListExpenseReport().size()+"\n note de frais");
        totalArgentCircle.setText(""+getSommeERByList(network.getListExpenseReport())+"\n d'argent de note de frais");
        totalCompanyCircle.setText(""+network.getListCompany().size()+"\n companies");
        totalBugCircle.setText(""+network.getListExpenseReport().size()+"\n bugs recensés");

        //On va Initialiser le graphique
        ObservableList<Company> dataCompany;
        //pieChart.setData();
        pieChart.setLabelsVisible(false);
        pieChart.setData(getListCompanyPieChart());
        pieChartEr.setLabelsVisible(false);
        pieChartEr.setData(this.getListCompaniePieChartER());



    }

    @FXML
    public void toBug(ActionEvent event){

        BorderPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
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
            for(Company company : listFiveCompany(network.getListCompany())){
                System.out.println(company.getName());
            }

        }
        else {
            //trier la liste des company en fonction de l'argent
            //Prendre les 5 premières
            //les affichers
            List<Company> listCompany = network.getListCompany();

        }
        return listData;
    }

    //On fait une fonction qui envoit un observable avec les compagnies en fonction du nombre de notes de frais :
    public ObservableList<PieChart.Data> getListCompaniePieChartER(){
        ObservableList<PieChart.Data> listData = FXCollections.observableArrayList();
        NetworkService network = new NetworkService();
        for(Company company:network.getListCompany()){
            if(network.getListExpenseReportByCompany(company.getIdCompany())!=null){
                listData.add(new PieChart.Data(company.getName()+" : "+network.getListExpenseReportByCompany(company.getIdCompany()).size(),getSommeERByList(network.getListExpenseReportByCompany(company.getIdCompany()))));
            }
        }
        return listData;
    }


    //On fait une fonction qui envoit un observable avec les compagnies en fonction du nombre de bugs recensés. :
    public ObservableList<PieChart.Data> getListCompaniePieChartBug(){
        ObservableList<PieChart.Data> listData = FXCollections.observableArrayList();
        NetworkService network = new NetworkService();
        for(Company company:network.getListCompany()){
            if(network.getListExpenseReportByCompany(company.getIdCompany())!=null){
                listData.add(new PieChart.Data(company.getName()+" : "+network.getListExpenseReportByCompany(company.getIdCompany()).size(),getSommeERByList(network.getListExpenseReportByCompany(company.getIdCompany()))));
            }
        }
        return listData;
    }

    //Cette fonctino renvoit les 5 plus grandes valeurs
    public List<Company> listFiveCompany(List<Company> listCompany){
        NetworkService network = new NetworkService();
        Collections.sort(listCompany,
                (o1, o2) -> new Integer(getSommeERByList(network.getListExpenseReportByCompany(o1.getIdCompany()))).compareTo(new Integer(getSommeERByList(network.getListExpenseReportByCompany(o2.getIdCompany())))));
        return listCompany;
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
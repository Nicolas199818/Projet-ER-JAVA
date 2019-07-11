package org.openjfx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import org.openjfx.data.Bug;
import org.openjfx.data.Company;
import org.openjfx.data.ExpenseReport;
import org.openjfx.data.User;
import org.openjfx.service.NetworkService;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class EvolutionActivite implements Initializable {
    //On fait un lien vers les différents éléments FXML.
    @FXML
    private StackedBarChart stackBarChartUser;
    @FXML
    private StackedBarChart stackBarChartBug;
    @FXML
    private StackedBarChart stackBarChartCompany;
    @FXML
    private StackedBarChart stackBarChartArgent;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //stackBarChartArgent.getData().add();

        stackBarChartUser.getData().add(this.getSeriesUser());
        //stackBarChartBug.getData().addAll(series3);
        stackBarChartCompany.getData().addAll(this.getSeriesCompany());
        //stackBarChartArgent.getData().addAll(series1);
        stackBarChartBug.getData().add(this.getSeriesBug());
        //stackBarChartArgent.getData().add(this.getSommeERByList(this.ge));
        stackBarChartArgent.getData().add(this.getSerieErArgent());

        getSeriesUser();
    }

    //On fait une fonction qui prend une date et qui pour les 12 derniers mois liste le nombre de user en base sous forme de Series.
    public XYChart.Series<String, Number> getSeriesUser(){
        //On boucle sur les douzes derniers mois.
        //On fera une route qui renvoit pour la date avant celle signalé.
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(date);
        XYChart.Series<String,Number> serie = new XYChart.Series();
        NetworkService network = NetworkService.getInstance();
        c.add(Calendar.MONTH, -10);
        date = c.getTime();

        for (int i = 1; i<=11 ;i++){
            //System.out.println(dateFormat.format(date));
            //C'est là que l'on va appeler la fonction et ajouter une donnée à la série.
            //Etape 1 : On récupère la liste des utilisateurs
            //List<>
            //Etape 2 : On fait une fonction qui renvoie une liste uniquement avec les données en dessous de la date indiqué.
            //Etape 3 : On ajouter les données dans le Series.
            serie.getData().add(new XYChart.Data<>(dateFormat.format(date), getListUserBefore(network.getListAllUser(),date).size()));
            c.add(Calendar.MONTH, +1);
            date = c.getTime();
        }
        //On retourne le Series et on le
        return serie;
    }

    //On fait une fonction qui ne prend que les users qui ont une date de création inférieur à celle donnée :
    public List<User> getListUserBefore(List<User> listUser, Date date){
        List<User> listUserBefore = new ArrayList<User>();
        System.out.println();
        System.out.println();
        //System.out.println(date);

        for(User user : listUser){
            if(user.isBefore(date)){
                listUserBefore.add(user);
                //System.out.println(listUserBefore.size());
                //System.out.println(date);
                System.out.println("la date du user dans getListUserBefore "+user.getCreatedAt());
            }
        }
        return listUserBefore;
    }

    public XYChart.Series<String, Number> getSeriesCompany(){
        //On boucle sur les douzes derniers mois.
        //On fera une route qui renvoit pour la date avant celle signalé.
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(date);
        XYChart.Series<String,Number> serie = new XYChart.Series();
        NetworkService network = NetworkService.getInstance();
        c.add(Calendar.MONTH, -10);
        date = c.getTime();

        for (int i = 1; i<=11 ;i++){
            //System.out.println(dateFormat.format(date));
            //C'est là que l'on va appeler la fonction et ajouter une donnée à la série.
            //Etape 1 : On récupère la liste des utilisateurs
            //List<>
            //Etape 2 : On fait une fonction qui renvoie une liste uniquement avec les données en dessous de la date indiqué.
            //Etape 3 : On ajouter les données dans le Series.
            serie.getData().add(new XYChart.Data<>(dateFormat.format(date), getListCompanyBefore(network.getListCompany(),date).size()));
            c.add(Calendar.MONTH, +1);
            date = c.getTime();
        }
        //On retourne le Series et on le
        return serie;
    }

    public List<Company> getListCompanyBefore(List<Company> listCompany, Date date){
        List<Company> listCompanyBefore = new ArrayList<Company>();
        System.out.println();
        System.out.println();
        //System.out.println(date);

        for(Company company : listCompany){
            if(company.isBefore(date)){
                listCompanyBefore.add(company);
                //System.out.println(listUserBefore.size());
                //System.out.println(date);
                System.out.println("Test des companys : "+company.getName());
            }
        }
        return listCompanyBefore;
    }

    public XYChart.Series<String, Number> getSeriesBug(){
        //On boucle sur les douzes derniers mois.
        //On fera une route qui renvoit pour la date avant celle signalé.
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(date);
        XYChart.Series<String,Number> serie = new XYChart.Series();
        NetworkService network = NetworkService.getInstance();
        c.add(Calendar.MONTH, -10);
        date = c.getTime();

        for (int i = 1; i<=11 ;i++){
            //System.out.println(dateFormat.format(date));
            //C'est là que l'on va appeler la fonction et ajouter une donnée à la série.
            //Etape 1 : On récupère la liste des utilisateurs
            //List<>
            //Etape 2 : On fait une fonction qui renvoie une liste uniquement avec les données en dessous de la date indiqué.
            //Etape 3 : On ajouter les données dans le Series.
            serie.getData().add(new XYChart.Data<>(dateFormat.format(date), getListBugBefore(network.getAllBug(),date).size()));
            c.add(Calendar.MONTH, +1);
            date = c.getTime();
        }
        //On retourne le Series et on le
        return serie;
    }

    public List<Bug> getListBugBefore(List<Bug> listBug, Date date){
        List<Bug> listBugBefore = new ArrayList<Bug>();
        System.out.println();
        System.out.println();
        //System.out.println(date);

        for(Bug bug : listBug){
            if(bug.isBefore(date)){
                listBugBefore.add(bug);
                //System.out.println(listUserBefore.size());
                //System.out.println(date);
                System.out.println("Test des companys : "+bug);
            }
        }
        return listBugBefore;
    }

    private int getSommeERByList(List<ExpenseReport> listER){
        int somme = 0;
        for(ExpenseReport er:listER){
            somme += er.getPrice();
        }
        return somme;
    }


    public XYChart.Series<String, Number> getSerieErArgent(){
        //On boucle sur les douzes derniers mois.
        //On fera une route qui renvoit pour la date avant celle signalé.
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(date);
        XYChart.Series<String,Number> serie = new XYChart.Series();
        NetworkService network = NetworkService.getInstance();
        c.add(Calendar.MONTH, -10);
        date = c.getTime();

        for (int i = 1; i<=11 ;i++){
            //System.out.println(dateFormat.format(date));
            //C'est là que l'on va appeler la fonction et ajouter une donnée à la série.
            //Etape 1 : On récupère la liste des utilisateurs
            //List<>
            //Etape 2 : On fait une fonction qui renvoie une liste uniquement avec les données en dessous de la date indiqué.
            //Etape 3 : On ajouter les données dans le Series.
            serie.getData().add(new XYChart.Data<>(dateFormat.format(date), getSommeERByList(getListErArgentBefore(network.getListExpenseReport(),date))));
            c.add(Calendar.MONTH, +1);
            date = c.getTime();
        }
        //On retourne le Series et on le
        return serie;
    }

    public List<ExpenseReport> getListErArgentBefore(List<ExpenseReport> listEr,Date date){
        List<ExpenseReport> listErBefore = new ArrayList<>();
        System.out.println();
        System.out.println();
        //System.out.println(date);

        for(ExpenseReport expenseReport: listEr){
            if(expenseReport.isBefore(date)){
                listErBefore.add(expenseReport);
                //System.out.println(listUserBefore.size());
                //System.out.println(date);
                System.out.println("Test des companys : "+expenseReport);
            }
        }
        return listErBefore;
    }

}

// Diagramme de l'évolution du nombre de user en fonction du temps : Ok ça a l'air bon.
// Diagramme de l'évolution du nombre de bug en fonction du temps :
// Diagramme de l'évolution du nombre de company en fonction du temps :
// Diagramme de l'évolution du nombre d'argent en fonction du temps :
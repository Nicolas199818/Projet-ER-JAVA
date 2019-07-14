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
import org.openjfx.data.Bug;
import org.openjfx.data.Company;
import org.openjfx.data.ExpenseReport;
import org.openjfx.data.User;
import org.openjfx.service.NetworkService;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Statistics implements Initializable {
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

    NetworkService networkService;
    List<ExpenseReport> listER;
    List<Company> listCompany;
    List<Bug> listBug;
    List<User> listUser;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        networkService = NetworkService.getInstance();
        this.listER = networkService.getListExpenseReport();
        this.listCompany = networkService.getListCompany();
        this.listBug = networkService.getAllBug();
        this.listUser = networkService.getListAllUser();

        totalERCircle.setText(""+listER.size()+"\n expense report");
        totalArgentCircle.setText(""+getSommeERByList(listER)+"\n euros");
        totalCompanyCircle.setText(""+listCompany.size()+"\n companies");
        totalBugCircle.setText(""+listBug.size()+"\n bugs");

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

    private int getSommeERByList(List<ExpenseReport> listER){
        int somme = 0;
        for(ExpenseReport er:listER){
            somme += er.getPrice();
        }
        return somme;
    }

    public ObservableList<PieChart.Data> getListCompanyPieChart(){
        ObservableList<PieChart.Data> listData = FXCollections.observableArrayList();
        if(listCompany.size()<5){
            for(Company company:listCompany){
                if(getListErByCompany(company.getIdCompany(),listER)!=null){
                    listData.add(new PieChart.Data(company.getName()+" : "+getSommeERByList(getListErByCompany(company.getIdCompany(),listER)),getSommeERByList(getListErByCompany(company.getIdCompany(),listER))));
                }
            }
        }
        else {

            Collections.sort(listCompany, new Comparator<Company>() {
                @Override
                public int compare(Company fruit2, Company fruit1)
                {
                    return  new Integer(getSommeERByList(getListErByCompany(fruit1.getIdCompany(),listER))).compareTo(new Integer(getSommeERByList(getListErByCompany(fruit2.getIdCompany(),listER))));
                }
            });
            for(int i=0;i < 5;i++){
                listData.add(new PieChart.Data(listCompany.get(i).getName()+" : "+getSommeERByList(getListErByCompany(listCompany.get(i).getIdCompany(),listER)),getSommeERByList(getListErByCompany(listCompany.get(i).getIdCompany(),listER))));
            }

        }
        return listData;
    }

    public ObservableList<PieChart.Data> getListCompaniePieChartER(){
        ObservableList<PieChart.Data> listData = FXCollections.observableArrayList();
        if(listCompany.size()<5){
            for(Company company:listCompany){
                if(getListErByCompany(company.getIdCompany(),listER)!=null){
                    listData.add(new PieChart.Data(company.getName()+" : "+getListErByCompany(company.getIdCompany(),listER).size(),getListErByCompany(company.getIdCompany(),listER).size()));
                }
            }
        }
        else {
            Collections.sort(listCompany, new Comparator<Company>() {
                @Override
                public int compare(Company company2, Company company1)
                {

                    return  new Integer(getListErByCompany(company1.getIdCompany(),listER).size()).compareTo(new Integer(getListErByCompany(company2.getIdCompany(),listER).size()));
                }
            });
            for(int i=0;i < 5;i++){
                listData.add(new PieChart.Data(listCompany.get(i).getName()+" : "+getListErByCompany(listCompany.get(i).getIdCompany(),listER).size(),getListErByCompany(listCompany.get(i).getIdCompany(),listER).size()));
            }
        }
        return listData;
    }


    public ObservableList<PieChart.Data> getListCompaniePieChartUser(){
        ObservableList<PieChart.Data> listData = FXCollections.observableArrayList();
        NetworkService network = NetworkService.getInstance();
        if(listCompany.size()<5){
            for(Company company:listCompany){
                if(getListUserByCompany(company.getIdCompany(),this.listUser)!=null){
                    listData.add(new PieChart.Data(company.getName()+" : "+getListUserByCompany(company.getIdCompany(),this.listUser).size(),getListUserByCompany(company.getIdCompany(),this.listUser).size()));
                }
            }
        }
        else {
            Collections.sort(listCompany, new Comparator<Company>() {
                @Override
                public int compare(Company company2, Company company1)
                {

                    return  new Integer(getListUserByCompany(company1.getIdCompany(),listUser).size()).compareTo(new Integer(getListUserByCompany(company2.getIdCompany(),listUser).size()));
                }
            });
            for(int i=0;i < listCompany.size();i++){
                listData.add(new PieChart.Data(listCompany.get(i).getName()+" : "+getListUserByCompany(listCompany.get(i).getIdCompany(),listUser).size(),getListUserByCompany(listCompany.get(i).getIdCompany(),listUser).size()));
            }
        }
        return listData;
    }


    public List<User> getListUserByCompany(String idCompany,List<User> listUser){
        List listUserCompany = new ArrayList();
        for(User user:listUser){
            if(user.getIdCompany().equals(idCompany)){
                listUserCompany.add(user);
            }
        }
        return listUserCompany;
    }

    // On fait une fonction qui renvoit une liste de user à partir d'un id de company :
    public List<ExpenseReport> getListErByCompany(String idCompany,List<ExpenseReport> listER){
        List listERCompany = new ArrayList();
        for(ExpenseReport er:listER){
            if(er.getCompanyID().equals(idCompany)){
                listERCompany.add(er);
            }
        }
        return listERCompany;
    }

}
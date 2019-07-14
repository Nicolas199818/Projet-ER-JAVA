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

    NetworkService networkService = NetworkService.getInstance();
    List<User> listUser;
    List<Company> listCompany;
    List<Bug> listBug;
    List<ExpenseReport> listER;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.listUser = networkService.getListAllUser();
        this.listCompany = networkService.getListCompany();
        this.listBug = networkService.getAllBug();
        this.listER = networkService.getListExpenseReport();


        stackBarChartUser.getData().add(this.getSeriesUser());
        stackBarChartCompany.getData().addAll(this.getSeriesCompany());
        stackBarChartBug.getData().add(this.getSeriesBug());
        stackBarChartArgent.getData().add(this.getSerieErArgent());

    }

    public XYChart.Series<String, Number> getSeriesUser(){
        Date date = new Date();
        XYChart.Series<String,Number> serie = new XYChart.Series();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(date);
        c.add(Calendar.MONTH, -10);
        date = c.getTime();

        for (int i = 1; i<=11 ;i++){
            serie.getData().add(new XYChart.Data<>(dateFormat.format(date), getListUserBefore(listUser,date).size()));
            c.add(Calendar.MONTH, +1);
            date = c.getTime();
        }
        return serie;
    }

    public List<User> getListUserBefore(List<User> listUser, Date date){
        List<User> listUserBefore = new ArrayList<User>();

        for(User user : listUser){
            if(user.isBefore(date)){
                listUserBefore.add(user);
            }
        }
        return listUserBefore;
    }

    public XYChart.Series<String, Number> getSeriesCompany(){
        Date date = new Date();
        XYChart.Series<String,Number> serie = new XYChart.Series();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(date);
        NetworkService network = NetworkService.getInstance();
        c.add(Calendar.MONTH, -10);
        date = c.getTime();

        for (int i = 1; i<=11 ;i++){
            serie.getData().add(new XYChart.Data<>(dateFormat.format(date), getListCompanyBefore(listCompany,date).size()));
            c.add(Calendar.MONTH, +1);
            date = c.getTime();
        }
        return serie;
    }

    public List<Company> getListCompanyBefore(List<Company> listCompany, Date date){
        List<Company> listCompanyBefore = new ArrayList<Company>();
        for(Company company : listCompany){
            if(company.isBefore(date)){
                listCompanyBefore.add(company);
            }
        }
        return listCompanyBefore;
    }

    public XYChart.Series<String, Number> getSeriesBug(){
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(date);
        XYChart.Series<String,Number> serie = new XYChart.Series();
        NetworkService network = NetworkService.getInstance();
        c.add(Calendar.MONTH, -10);
        date = c.getTime();

        for (int i = 1; i<=11 ;i++){
            serie.getData().add(new XYChart.Data<>(dateFormat.format(date), getListBugBefore(listBug,date).size()));
            c.add(Calendar.MONTH, +1);
            date = c.getTime();
        }
        return serie;
    }

    public List<Bug> getListBugBefore(List<Bug> listBug, Date date){
        List<Bug> listBugBefore = new ArrayList<Bug>();

        for(Bug bug : listBug){
            if(bug.isBefore(date)){
                listBugBefore.add(bug);
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
            serie.getData().add(new XYChart.Data<>(dateFormat.format(date), getSommeERByList(getListErArgentBefore(listER,date))));
            c.add(Calendar.MONTH, +1);
            date = c.getTime();
        }
        return serie;
    }

    public List<ExpenseReport> getListErArgentBefore(List<ExpenseReport> listEr,Date date){
        List<ExpenseReport> listErBefore = new ArrayList<>();

        for(ExpenseReport expenseReport: listEr){
            if(expenseReport.isBefore(date)){
                listErBefore.add(expenseReport);
            }
        }
        return listErBefore;
    }

}
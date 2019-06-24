package org.openjfx;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;





public class NetworkService {

    //On fait une fonction qui va permettre à un user de s'inscrire comme admin :
    public int signUpUser(String name,String firstname,String login,String password){
        HttpURLConnection con;
        String url = "http://localhost:3000/admin/signup";
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type","application/json");

            JSONObject object = new JSONObject();
            object.put("lastname",name);
            object.put("firstname",firstname);
            object.put("email",login);
            object.put("password",password);
            OutputStream os = con.getOutputStream();
            os.write(object.toString().getBytes("UTF-8"));
            os.close();

            int statusRequest =  con.getResponseCode();
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
                JSONObject obj = new JSONObject(content.toString());
                User.setToken(obj.getString("token"));
            }

            return statusRequest;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
        return 500;
    }



    public int signInUser(String email,String password){
        HttpURLConnection con;
        String url = "http://localhost:3000/admin/signin";
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type","application/json");

            JSONObject object = new JSONObject();
            object.put("email",email);
            object.put("password",password);
            OutputStream os = con.getOutputStream();
            os.write(object.toString().getBytes("UTF-8"));
            os.close();

            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
                JSONObject obj = new JSONObject(content.toString());
                User.setToken(obj.getString("token"));
            }

            return con.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
        return 500;
    }

    public ObservableList<Bug> getListBugByStatut(int status){
        HttpURLConnection con;
        String url = "http://localhost:3000/bugAdmin/getAllByStatut";
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestProperty("x-access-token",User.getToken());

            JSONObject object = new JSONObject();
            object.put("filter",status);
            OutputStream os = con.getOutputStream();
            os.write(object.toString().getBytes("UTF-8"));
            os.close();

            int statusRequest =  con.getResponseCode();
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            //System.out.println(content);
            JSONObject obj = new JSONObject(content.toString());

            JSONArray arr = obj.getJSONArray("bugs");
            ObservableList<Bug> data = FXCollections.observableArrayList();
            for (int i = 0; i < arr.length(); i++) {

                data.add(new Bug(arr.getJSONObject(i).getString("_id"),arr.getJSONObject(i).getString("createdAt"),arr.getJSONObject(i).getString("type"),arr.getJSONObject(i).getString("type"),arr.getJSONObject(i).getInt("status"),arr.getJSONObject(i).getString("userID")));

                String post_id = arr.getJSONObject(i).getString("content");
                //System.out.println(post_id);
            }

            return data;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
        return null;
    }

    public ObservableList<Bug> getListBug(){
        HttpURLConnection con;
        String url = "http://localhost:3000/bugAdmin/getAll";
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestProperty("x-access-token",User.getToken());

            int statusRequest =  con.getResponseCode();
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            //System.out.println(content);
            JSONObject obj = new JSONObject(content.toString());

            JSONArray arr = obj.getJSONArray("bugs");
            ObservableList<Bug> data = FXCollections.observableArrayList();
            for (int i = 0; i < arr.length(); i++) {

                data.add(new Bug(arr.getJSONObject(i).getString("_id"),arr.getJSONObject(i).getString("createdAt"),arr.getJSONObject(i).getString("type"),arr.getJSONObject(i).getString("type"),arr.getJSONObject(i).getInt("status"),arr.getJSONObject(i).getString("userID")));

                String post_id = arr.getJSONObject(i).getString("content");
                //System.out.println(post_id);
            }

            return data;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
        return null;
    }

    //On fait une fonction pour la réolution du bug.
    public int resolveBug(String idBug,int status){
        HttpURLConnection con;
        String url = "http://localhost:3000/bugAdmin/updateStatus";

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("PUT");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("x-access-token",User.getToken());

            JSONObject object = new JSONObject();
            object.put("_id",idBug);
            object.put("status",1);

            OutputStream os = con.getOutputStream();
            os.write(object.toString().getBytes("UTF-8"));
            os.close();

            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }

            }

            return con.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
        return 500;
    }

    //On fait une fonction qui nous permet de récupérer la liste des notes de frais :
    public List<ExpenseReport> getListExpenseReport(){
        HttpURLConnection con;
        String url = "http://localhost:3000/adminEr/getAll";
        try {
            System.out.println("Le token du user : "+User.getToken());
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestProperty("x-access-token",User.getToken());


            int statusRequest =  con.getResponseCode();
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            System.out.println("content : "+content);
            JSONObject obj = new JSONObject(content.toString());

            JSONArray arr = obj.getJSONArray("exenses");
            //On configure la classe ExpenseExport avec les différents champs et on ajoute avec les données que l'on récupère.
            List<ExpenseReport> listExpenseReport = new ArrayList<ExpenseReport>();
            for (int i = 0; i < arr.length(); i++) {
                listExpenseReport.add(new ExpenseReport(arr.getJSONObject(i).getString("_id"),"id de la compagnie",arr.getJSONObject(i).getInt("price"),arr.getJSONObject(i).getInt("vat"),arr.getJSONObject(i).getString("address"),arr.getJSONObject(i).getInt("status"),arr.getJSONObject(i).getString("createdAt"),"lastUpdatedAt","repayementAt",arr.getJSONObject(i).getString("userID"),"imageID"));

            }

            return listExpenseReport;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ExpenseReport> getListExpenseReportByCompany(String idCompany){
        HttpURLConnection con;
        String url = "http://localhost:3000/adminEr/erByCompany";
        try {
            System.out.println("Le id de la company : "+idCompany);
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestProperty("x-access-token",User.getToken());

            JSONObject object = new JSONObject();
            object.put("companyID",idCompany);
            OutputStream os = con.getOutputStream();
            os.write(object.toString().getBytes("UTF-8"));
            os.close();


            int statusRequest =  con.getResponseCode();
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            System.out.println("content : "+content);
            JSONObject obj = new JSONObject(content.toString());

            JSONArray arr = obj.getJSONArray("er");
            //On configure la classe ExpenseExport avec les différents champs et on ajoute avec les données que l'on récupère.
            List<ExpenseReport> listExpenseReport = new ArrayList<ExpenseReport>();
            for (int i = 0; i < arr.length(); i++) {
                //On ajoute les expensesReport à la liste.
                //data.add(new Bug(arr.getJSONObject(i).getString("_id"),arr.getJSONObject(i).getString("createdAt"),arr.getJSONObject(i).getString("type"),arr.getJSONObject(i).getString("type"),arr.getJSONObject(i).getInt("status"),arr.getJSONObject(i).getString("userID")));
                listExpenseReport.add(new ExpenseReport(arr.getJSONObject(i).getString("_id"),"id de la compagnie",arr.getJSONObject(i).getInt("price"),arr.getJSONObject(i).getInt("vat"),arr.getJSONObject(i).getString("address"),arr.getJSONObject(i).getInt("status"),arr.getJSONObject(i).getString("createdAt"),"lastUpdatedAt","repayementAt",arr.getJSONObject(i).getString("userID"),"imageID"));

            }

            return listExpenseReport;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    //On fait une fonction qui permet de récupérer la liste des entreprises :
    public List<Company> getListCompany(){
        HttpURLConnection con;
        String url = "http://localhost:3000/adminCompany/getAll";
        try {
            System.out.println("Le token du user : "+User.getToken());
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestProperty("x-access-token",User.getToken());


            int statusRequest =  con.getResponseCode();
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            System.out.println("content : "+content);
            JSONObject obj = new JSONObject(content.toString());

            JSONArray arr = obj.getJSONArray("companies");
            //On configure la classe ExpenseExport avec les différents champs et on ajoute avec les données que l'on récupère.
            List<Company> listCompany = new ArrayList<Company>();
            for (int i = 0; i < arr.length(); i++) {
                listCompany.add(new Company(arr.getJSONObject(i).getString("_id"),arr.getJSONObject(i).getString("name"),"image de la companie","date de création","date de modification"));
            }

            return listCompany;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }



}

//Mettre en place la base de données
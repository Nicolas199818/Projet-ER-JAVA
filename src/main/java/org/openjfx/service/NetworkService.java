package org.openjfx.service;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openjfx.data.*;


public class NetworkService {
    private String apiPath = "http://localhost:3000/";

    private static NetworkService instance;

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    private NetworkService(){

    }

    public int signUpUser(String name,String firstname,String login,String password){
        HttpURLConnection con;
        String url = apiPath+"admin/signup";
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("UserGestionToken-Agent", "Java client");
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
                UserGestionToken.setToken(obj.getString("token"));
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
        String url = apiPath+"admin/signin";
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("UserGestionToken-Agent", "Java client");
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
                UserGestionToken.setToken(obj.getString("token"));
            }

            return con.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
        return 500;
    }

    public List<Bug> getListBugByStatut(int status){
        HttpURLConnection con;
        String url = apiPath+"bugAdmin/getAllByStatut";
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("UserGestionToken-Agent", "Java client");
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestProperty("x-access-token", UserGestionToken.getToken());

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
            List<Bug> data = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                if(arr.getJSONObject(i).has("_id") && arr.getJSONObject(i).has("createdAt") && arr.getJSONObject(i).has("type") && arr.getJSONObject(i).has("status") && arr.getJSONObject(i).has("userID")){
                    data.add(new Bug(arr.getJSONObject(i).getString("_id"),arr.getJSONObject(i).getString("createdAt"),arr.getJSONObject(i).getString("content"),arr.getJSONObject(i).getString("type"),arr.getJSONObject(i).getInt("status"),arr.getJSONObject(i).getString("userID")));
                }
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
        String url = apiPath+"bugAdmin/updateStatus";

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("PUT");
            con.setRequestProperty("UserGestionToken-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("x-access-token", UserGestionToken.getToken());

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

    public List<ExpenseReport> getListExpenseReport(){
        HttpURLConnection con;
        String url = apiPath+"adminEr/getAll";
        try {
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("UserGestionToken-Agent", "Java client");
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestProperty("x-access-token", UserGestionToken.getToken());


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
            JSONObject obj = new JSONObject(content.toString());

            JSONArray arr = obj.getJSONArray("exenses");
            List<ExpenseReport> listExpenseReport = new ArrayList<ExpenseReport>();
            for (int i = 0; i < arr.length(); i++) {
                if(!arr.getJSONObject(i).isNull("_id") && !arr.getJSONObject(i).isNull("companyID") && !arr.getJSONObject(i).isNull("price") && !arr.getJSONObject(i).isNull("vat") && !arr.getJSONObject(i).isNull("address") && !arr.getJSONObject(i).isNull("status") && !arr.getJSONObject(i).isNull("createdAt") &&  !arr.getJSONObject(i).isNull("userID")) {

                    listExpenseReport.add(new ExpenseReport(arr.getJSONObject(i).getString("_id"), arr.getJSONObject(i).getString("companyID"), arr.getJSONObject(i).getInt("price"), arr.getJSONObject(i).getInt("vat"), arr.getJSONObject(i).getString("address"), arr.getJSONObject(i).getInt("status"), arr.getJSONObject(i).getString("createdAt"), "lastUpdatedAt", "repayementAt", arr.getJSONObject(i).getString("userID"), "imageID"));
                    System.out.println("VOOOUUUSS NE PASSSERRREZZZZ PASSS"+listExpenseReport.size());
                }
            }

            return listExpenseReport;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Company> getListCompany(){
        HttpURLConnection con;
        String url = apiPath+"adminCompany/getAll";
        try {
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("UserGestionToken-Agent", "Java client");
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestProperty("x-access-token", UserGestionToken.getToken());


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
            JSONObject obj = new JSONObject(content.toString());

            JSONArray arr = obj.getJSONArray("companies");
            List<Company> listCompany = new ArrayList<Company>();
            for (int i = 0; i < arr.length(); i++) {
                listCompany.add(new Company(arr.getJSONObject(i).getString("_id"),arr.getJSONObject(i).getString("name"),"image de la companie",arr.getJSONObject(i).getString("createdAt"),"date de modification"));
            }

            return listCompany;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    //On fait une fonction qui récupère la liste des user
    public List<User> getListAllUser(){
        HttpURLConnection con;
        String url = apiPath+"admin/getAllUser";
        try {
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("UserGestionToken-Agent", "Java client");
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestProperty("x-access-token", UserGestionToken.getToken());



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

            JSONArray arr = obj.getJSONArray("users");
            List<User> listUser = new ArrayList<User>();
            for (int i = 0; i < arr.length(); i++) {
                if(!arr.getJSONObject(i).isNull("_id") && !arr.getJSONObject(i).isNull("lastname") && !arr.getJSONObject(i).isNull("firstname") && !arr.getJSONObject(i).isNull("email") && !arr.getJSONObject(i).isNull("createdAt") && !arr.getJSONObject(i).isNull("companyID")) {

                    listUser.add(new User(arr.getJSONObject(i).getString("_id"), arr.getJSONObject(i).getString("lastname"), arr.getJSONObject(i).getString("firstname"), arr.getJSONObject(i).getString("email"), arr.getJSONObject(i).getString("createdAt"), arr.getJSONObject(i).getString("companyID")));
                }
            }

            return listUser;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Bug> getAllBug(){
        HttpURLConnection con;
        String url = apiPath+"bugAdmin/getAll";
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("UserGestionToken-Agent", "Java client");
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestProperty("x-access-token", UserGestionToken.getToken());

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
            JSONObject obj = new JSONObject(content.toString());

            JSONArray arr = obj.getJSONArray("bugs");
            List<Bug> listData = new ArrayList<Bug>();
            for (int i = 0; i < arr.length(); i++) {

                listData.add(new Bug(arr.getJSONObject(i).getString("_id"),arr.getJSONObject(i).getString("createdAt"),arr.getJSONObject(i).getString("type"),arr.getJSONObject(i).getString("type"),arr.getJSONObject(i).getInt("status"),arr.getJSONObject(i).getString("userID")));

                String post_id = arr.getJSONObject(i).getString("content");

            }

            return listData;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
        return null;
    }
}
//Bien check avec isNull pour l'ensemble des requetes restantes.
package org.openjfx;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;





public class NetworkService {


    public int signInUser(String email,String password){
        HttpURLConnection con;
        String url = "http://localhost:3000/users/signin";
        String urlParameters = "email="+email+"&password="+password;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
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

    public ObservableList<Bug> getListBug(int status){
        HttpURLConnection con;
        String url = "http://localhost:3000/bugs/getAll";
        String urlParameters = "{\"filter\": "+status+"}";;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type","application/json");

            OutputStream os = con.getOutputStream();
            os.write(postData);
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
    public int resolveBug(String email,String password){
        HttpURLConnection con;
        String url = "http://localhost:3000/users/signin";
        String urlParameters = "email="+email+"&password="+password;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
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



}

//Mettre en place la base de données
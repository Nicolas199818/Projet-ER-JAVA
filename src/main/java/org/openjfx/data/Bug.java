package org.openjfx.data;

//On créer une classe qui définit le modèle de données et fournit les méthodes et les
//champs en vue de travailler avec la table.

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bug {
    private SimpleStringProperty idBug;
    private SimpleStringProperty date;
    private SimpleStringProperty contenu;
    private SimpleStringProperty type;
    private SimpleStringProperty status;
    private SimpleStringProperty user;

    public Bug(String idBug, String date, String contenu, String type, int status, String user) {
        this.idBug = new SimpleStringProperty(idBug);
        this.date = new SimpleStringProperty(date);
        this.contenu = new SimpleStringProperty(contenu);
        this.type = new SimpleStringProperty(type);
        if(status == 0){
            this.status = new SimpleStringProperty("Resolution in progress");
        }
        else {
            this.status = new SimpleStringProperty("Bug resolved");
        }
        this.user = new SimpleStringProperty(user);
    }

    public String getIdBug() {
        return idBug.get();
    }

    public SimpleStringProperty idBugProperty() {
        return idBug;
    }

    public void setIdBug(String idBug) {
        this.idBug.set(idBug);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getContenu() {
        return contenu.get();
    }

    public SimpleStringProperty contenuProperty() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu.set(contenu);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getUser() {
        return user.get();
    }

    public SimpleStringProperty userProperty() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public boolean isBefore(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {
            Date userDate = dateFormat.parse(this.date.get());
            if(userDate.compareTo(date)<=0){
                return true;
            }
            else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}

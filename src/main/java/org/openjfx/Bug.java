package org.openjfx;

//On créer une classe qui définit le modèle de données et fournit les méthodes et les
//champs en vue de travailler avec la table.

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Bug {
    private SimpleIntegerProperty idBug;
    private SimpleStringProperty date;
    private SimpleStringProperty contenu;
    private SimpleStringProperty type;
    private SimpleStringProperty status;
    private SimpleIntegerProperty user;

    public Bug(int idBug, String date, String contenu, String type, String status, int user) {
        this.idBug = new SimpleIntegerProperty(idBug);
        this.date = new SimpleStringProperty(date);
        this.contenu = new SimpleStringProperty(contenu);
        this.type = new SimpleStringProperty(type);
        this.status = new SimpleStringProperty(status);
        this.user = new SimpleIntegerProperty(user);
    }

    public int getIdBug() {
        return idBug.get();
    }

    public SimpleIntegerProperty idBugProperty() {
        return idBug;
    }

    public void setIdBug(int idBug) {
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

    public int getUser() {
        return user.get();
    }

    public SimpleIntegerProperty userProperty() {
        return user;
    }

    public void setUser(int user) {
        this.user.set(user);
    }
}

package org.openjfx.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private String idUser;
    private String lastname;
    private String firstname;
    private String email;
    private String createdAt;

    public User(String idUser, String lastname, String firstname, String email, String createdAt) {
        this.idUser = idUser;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.createdAt = createdAt;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    //test si cet User a bien  une date inférieur à celle donnée.
    public boolean isBefore(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {
            Date userDate = dateFormat.parse(this.createdAt);
            System.out.println("La date du user : "+ dateFormat.parse(this.createdAt));
            //System.out.println("autre format "+date);
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

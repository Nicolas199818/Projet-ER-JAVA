package org.openjfx.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Company{
    private String idCompany;
    private String name;
    private String imageID;
    private String createdAt;
    private String lastUpdateAt;

    public Company(String idCompany,String name, String imageID, String createdAt, String lastUpdateAt) {
        this.idCompany = idCompany;
        this.name = name;
        this.imageID = imageID;
        this.createdAt = createdAt;
        this.lastUpdateAt = lastUpdateAt;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(String lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

    //test si cet User a bien  une date inférieur à celle donnée.
    public boolean isBefore(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {
            Date userDate = dateFormat.parse(this.createdAt);
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

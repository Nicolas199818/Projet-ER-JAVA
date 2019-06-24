package org.openjfx;

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

}

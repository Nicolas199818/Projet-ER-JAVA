package org.openjfx;

public class ExpenseReport {
    // On déclare nos attributs.
    private String idExpense;
    private String companyID;
    private int price;
    private int vat;
    private String address;
    private int status;
    private String createdAt;
    private String lastUpdatedAt;
    private String repayementAt;
    private String userID;
    private String imageID;

    public ExpenseReport(String idExpense, String companyID, int price, int vat, String address, int status, String createdAt, String lastUpdatedAt, String repayementAt, String userID, String imageID) {
        this.idExpense = idExpense;
        this.companyID = companyID;
        this.price = price;
        this.vat = vat;
        this.address = address;
        this.status = status;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.repayementAt = repayementAt;
        this.userID = userID;
        this.imageID = imageID;
    }

    public String getIdExpense() {
        return idExpense;
    }

    public int getPrice() {
        return price;
    }

    public int getVat() {
        return vat;
    }

    public String getAddress() {
        return address;
    }

    public String getCompanyID() {
        return companyID;
    }

    public int getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public String getRepayementAt() {
        return repayementAt;
    }

    public String getUserID() {
        return userID;
    }

    public String getImageID() {
        return imageID;
    }
}

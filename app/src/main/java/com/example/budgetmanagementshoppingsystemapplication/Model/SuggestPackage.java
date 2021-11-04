package com.example.budgetmanagementshoppingsystemapplication.Model;

public class SuggestPackage {
    String packageID;
    String productID;
    String totalPackagePrice;

    public SuggestPackage(String productID) {
        this.productID = productID;
    }

    public SuggestPackage(String packageID, String totalPackagePrice) {
        this.packageID = packageID;
        this.totalPackagePrice = totalPackagePrice;
    }

    public String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public String getTotalPackagePrice() {
        return totalPackagePrice;
    }

    public void setTotalPackagePrice(String totalPackagePrice) {
        this.totalPackagePrice = totalPackagePrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    @Override
    public String toString() {
        return "SuggestPackage{" +
                "productID='" + productID + '\'' +
                '}';
    }
}

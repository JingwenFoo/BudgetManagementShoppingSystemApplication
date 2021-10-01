package com.example.budgetmanagementshoppingsystemapplication;

public class Product {
    private String productID;
    private String category;
    private String productName;
    private String productBrand;
    private String productDescription;
    private String productImage;
    private Float productPrice;
    private Float sellingPrice;
    private String stockAvailable;

    public Product() {
    }

    public Product(String productID, String category, String productName, String productBrand, String productDescription, String productImage, Float productPrice, Float sellingPrice, String stockAvailable) {
        this.productID = productID;
        this.category = category;
        this.productName = productName;
        this.productBrand = productBrand;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.sellingPrice = sellingPrice;
        this.stockAvailable = stockAvailable;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public Float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getStockAvailable() {
        return stockAvailable;
    }

    public void setStockAvailable(String stockAvailable) {
        this.stockAvailable = stockAvailable;
    }
}

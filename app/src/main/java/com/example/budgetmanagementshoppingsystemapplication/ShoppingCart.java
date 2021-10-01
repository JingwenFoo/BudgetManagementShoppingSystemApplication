package com.example.budgetmanagementshoppingsystemapplication;

public class ShoppingCart {
    String uid;
    String productID;
    String productQuantity;
    String totalPrice;
    String discount;
    String category;

    public ShoppingCart() {
    }

    public ShoppingCart(String uid, String productID, String productQuantity, String totalPrice, String discount, String category) {
        this.uid = uid;
        this.productID = productID;
        this.productQuantity = productQuantity;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.category = category;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

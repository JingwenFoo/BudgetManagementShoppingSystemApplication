package com.example.budgetmanagementshoppingsystemapplication.Model;

import java.util.ArrayList;

public class Payment {
    String paymentID;
    String amountPay;
    String customerName;
    String datetime;
    String paymentStatus;
    String paymentType;

    public Payment() {
    }

    public Payment(String paymentID, String amountPay, String datetime) {
        this.paymentID = paymentID;
        this.amountPay = amountPay;
        this.datetime = datetime;
    }

    public Payment(String paymentID, String amountPay, String customerName, String datetime, String paymentStatus, String paymentType) {
        this.paymentID = paymentID;
        this.amountPay = amountPay;
        this.customerName = customerName;
        this.datetime = datetime;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;


    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getAmountPay() {
        return amountPay;
    }

    public void setAmountPay(String amountPay) {
        this.amountPay = amountPay;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDateTime() {
        return datetime;
    }

    public void setDateTime(String dateTime) {
        this.datetime = datetime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return "InvoiceList{" +
                "invoiceID='" + paymentID + '\'' +
                ", datetime='" + datetime + '\'' +
                ", totalPurchase='" + amountPay + '\'' +
                '}';
    }
}

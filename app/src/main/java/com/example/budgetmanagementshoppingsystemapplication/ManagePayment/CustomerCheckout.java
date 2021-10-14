package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.budgetmanagementshoppingsystemapplication.R;

public class CustomerCheckout extends AppCompatActivity {
RecyclerView recyclerViewCheckout, recyclerViewCheckoutItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_checkout);

        recyclerViewCheckout = findViewById(R.id.recyclerViewCheckout);
        recyclerViewCheckoutItem = findViewById(R.id.recyclerViewCheckoutSection);

        MainCheckoutAdapter mainCheckoutAdapter = new MainCheckoutAdapter(CustomerCheckout.this);
        recyclerViewCheckout.setAdapter(mainCheckoutAdapter);

    }
}
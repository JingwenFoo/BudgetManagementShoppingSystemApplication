package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.CustomerHomepage;
import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.MainActivity;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
import com.example.budgetmanagementshoppingsystemapplication.R;

public class CustomerCheckout extends AppCompatActivity {
RecyclerView recyclerViewCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_checkout);

        recyclerViewCheckout = findViewById(R.id.recyclerViewCheckout);

        MainCheckoutAdapter mainCheckoutAdapter = new MainCheckoutAdapter(CustomerCheckout.this);
        recyclerViewCheckout.setAdapter(mainCheckoutAdapter);

    }

}
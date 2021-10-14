package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking.MainRecylcerAdapter;
import com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking.ViewCart;
import com.example.budgetmanagementshoppingsystemapplication.Model.ShoppingCart;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerCheckout extends AppCompatActivity {
RecyclerView recyclerViewCheckout, recyclerViewCheckoutItem;

DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_checkout);

        recyclerViewCheckout = findViewById(R.id.recyclerViewCheckout);
        recyclerViewCheckoutItem = findViewById(R.id.recyclerViewCheckoutSection);
        ref = FirebaseDatabase.getInstance().getReference();



        MainCheckoutAdapter mainCheckoutAdapter = new MainCheckoutAdapter(CustomerCheckout.this);
        recyclerViewCheckout.setAdapter(mainCheckoutAdapter);
     //   recyclerViewCheckout.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));



    }
}
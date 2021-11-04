package com.example.budgetmanagementshoppingsystemapplication.ManageAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.budgetmanagementshoppingsystemapplication.ManagePayment.CustomerCheckout;
import com.example.budgetmanagementshoppingsystemapplication.ManagePayment.MainCheckoutAdapter;
import com.example.budgetmanagementshoppingsystemapplication.Model.Customer;
import com.example.budgetmanagementshoppingsystemapplication.Model.Payment;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAccountDetail extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_account_detail);
        recyclerView = findViewById(R.id.mainAccountDetailRecyclerView);

        Intent intent = getIntent();
        String uid = ""+intent.getStringExtra("uid");

        ViewAccountDetailAdapter adapter = new ViewAccountDetailAdapter(ViewAccountDetail.this, uid);
        recyclerView.setAdapter(adapter);

    }
}
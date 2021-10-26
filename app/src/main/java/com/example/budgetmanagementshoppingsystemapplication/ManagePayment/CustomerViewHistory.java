package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.budgetmanagementshoppingsystemapplication.Model.Payment;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerViewHistory extends AppCompatActivity {
RecyclerView recyclerViewHistory;
List<Payment> invoiceList = new ArrayList<>();
CustomerHistoryAdapter adapter;
DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_history);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);

        recyclerViewHistory.setHasFixedSize(true);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        ref = FirebaseDatabase.getInstance().getReference();
        adapter = new CustomerHistoryAdapter(this, invoiceList);
        recyclerViewHistory.setAdapter(adapter);

        String customerName = preferences.getDataCustomerName(this);

        ref.child("Payment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Payment invoice = snapshot1.getValue(Payment.class);
                    if(invoice.getCustomerName()!=null && invoice.getCustomerName().matches(customerName))
                    {
                        String invoiceID = snapshot1.child("paymentID").getValue(String.class);
                        String dateTime = snapshot1.child("datetime").getValue(String.class);
                        String totalPurchase = snapshot1.child("amountPay").getValue(String.class);

                        invoiceList.add(new Payment(invoiceID, totalPurchase, dateTime));

                    }
                }
                Collections.reverse(invoiceList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
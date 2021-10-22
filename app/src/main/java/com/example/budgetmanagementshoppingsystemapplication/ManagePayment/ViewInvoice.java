package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.budgetmanagementshoppingsystemapplication.Model.Customer;
import com.example.budgetmanagementshoppingsystemapplication.Model.Payment;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewInvoice extends AppCompatActivity {
    RecyclerView recyclerViewInvoice;
    List<Payment> invoiceList = new ArrayList<>();
    InvoiceListAdapter adapter;
    DatabaseReference ref;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invoice);

        recyclerViewInvoice = findViewById(R.id.invoiceListRecyclerView);
        search = findViewById(R.id.searchInvoiceList);

        recyclerViewInvoice.setHasFixedSize(true);
        recyclerViewInvoice.setLayoutManager(new LinearLayoutManager(this));
        ref = FirebaseDatabase.getInstance().getReference();
        adapter = new InvoiceListAdapter(this, invoiceList);
        recyclerViewInvoice.setAdapter(adapter);

        ref.child("Payment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Payment invoice = snapshot1.getValue(Payment.class);
                    invoiceList.add(invoice);

            }
                Collections.reverse(invoiceList);
                adapter.notifyDataSetChanged();
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchCustomer(s.toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void searchCustomer(String searchName)
    {
        Query query = FirebaseDatabase.getInstance().getReference().child("Payment").orderByChild("customerName")
                .startAt(searchName)
                .endAt(searchName+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                invoiceList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Payment inv = dataSnapshot.getValue(Payment.class);
                    invoiceList.add(inv);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
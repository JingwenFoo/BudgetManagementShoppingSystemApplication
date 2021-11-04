package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.budgetmanagementshoppingsystemapplication.R;

public class CustomerInvoice extends AppCompatActivity {
RecyclerView recyclerViewInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_invoice);

        recyclerViewInvoice = findViewById(R.id.recyclerViewCustInvoice);

        Intent intent = getIntent();
        String invoiceID = intent.getStringExtra("invoiceID");
        MainInvoiceAdapter mainInvoiceAdapter = new MainInvoiceAdapter(CustomerInvoice.this, invoiceID);
        recyclerViewInvoice.setAdapter(mainInvoiceAdapter);
    }
}
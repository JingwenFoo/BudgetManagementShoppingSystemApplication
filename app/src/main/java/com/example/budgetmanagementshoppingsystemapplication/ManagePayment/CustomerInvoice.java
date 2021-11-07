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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.homeBtn)
        {
            Intent in = new Intent(CustomerInvoice.this, CustomerHomepage.class);
            startActivity(in);
        }
        if(itemId==R.id.logoutBtn)
        {
            Intent in = new Intent(CustomerInvoice.this, MainActivity.class);
            startActivity(in);
            preferences.clearData(CustomerInvoice.this);
            finish();
        }

        return true;
    }
}
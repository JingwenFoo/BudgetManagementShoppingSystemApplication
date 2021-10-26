package com.example.budgetmanagementshoppingsystemapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.MainActivity;
import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.ViewAccountList;
import com.example.budgetmanagementshoppingsystemapplication.ManagePayment.ViewInvoice;
import com.example.budgetmanagementshoppingsystemapplication.ManageProduct.ViewList;

public class AdminHomepage extends AppCompatActivity {
Button accountListBtn, manageProductBtn, invoiceListBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);
        accountListBtn = findViewById(R.id.accountListBtn);
        manageProductBtn = findViewById(R.id.manageProductBtn);
        invoiceListBtn= findViewById(R.id.invoiceListBtn);

        manageProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productList = new Intent(AdminHomepage.this, ViewList.class);
                startActivity(productList);
            }
        });

        accountListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountList = new Intent(AdminHomepage.this, ViewAccountList.class);
                startActivity(accountList);
            }
        });

        invoiceListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent invoiceList = new Intent(AdminHomepage.this, ViewInvoice.class);
                startActivity(invoiceList);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.adminLogoutBtn)
        {
            Intent in = new Intent(AdminHomepage.this, MainActivity.class);
            startActivity(in);
            preferences.clearData(AdminHomepage.this);
            finish();
        }

        return true;
    }
}
package com.example.budgetmanagementshoppingsystemapplication.ManageAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.budgetmanagementshoppingsystemapplication.ManagePayment.ViewIncome;
import com.example.budgetmanagementshoppingsystemapplication.ManagePayment.ViewInvoice;
import com.example.budgetmanagementshoppingsystemapplication.ManageProduct.StockChecklist;
import com.example.budgetmanagementshoppingsystemapplication.ManageProduct.ViewList;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;

public class AdminHomepage extends AppCompatActivity {
Button accountListBtn, manageProductBtn, stockChecklistBtn, viewIncomeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);
        accountListBtn = findViewById(R.id.accountListBtn);
        manageProductBtn = findViewById(R.id.manageProductBtn);
        stockChecklistBtn = findViewById(R.id.stockCheckListBtn);
        viewIncomeBtn = findViewById(R.id.viewIncomeBtn);

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

        stockChecklistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stockList = new Intent(AdminHomepage.this, StockChecklist.class);
                startActivity(stockList);
            }
        });

        viewIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent income = new Intent(AdminHomepage.this, ViewIncome.class);
                startActivity(income);
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
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
            Intent in = new Intent(CustomerCheckout.this, CustomerHomepage.class);
            startActivity(in);
        }
        if(itemId==R.id.logoutBtn)
        {
            Intent in = new Intent(CustomerCheckout.this, MainActivity.class);
            startActivity(in);
            preferences.clearData(CustomerCheckout.this);
            finish();
        }

        return true;
    }
}
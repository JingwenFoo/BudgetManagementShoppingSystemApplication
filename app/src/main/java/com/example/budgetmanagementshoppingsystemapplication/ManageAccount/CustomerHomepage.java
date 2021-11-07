package com.example.budgetmanagementshoppingsystemapplication.ManageAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking.Budget;
import com.example.budgetmanagementshoppingsystemapplication.ManagePayment.CustomerViewHistory;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerHomepage extends AppCompatActivity {
Button accountBtn, shoppingBtn, historyBtn;
DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_homepage);
        accountBtn = findViewById(R.id.accountBtn);
        shoppingBtn = findViewById(R.id.shoppingBtn);
        historyBtn = findViewById(R.id.historyBtn);

        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Account").child(String.valueOf(preferences.getDataStatus(this))).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userID = snapshot.child("uid").getValue(String.class);
                preferences.setDataUserID(CustomerHomepage.this, userID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.child("Customer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String custname = snapshot.child(preferences.getDataUserID(CustomerHomepage.this)).child("name").getValue(String.class);
                preferences.setDataCustomerName(CustomerHomepage.this, custname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        shoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopping = new Intent(CustomerHomepage.this, Budget.class);
                startActivity(shopping);
            }
        });

        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(CustomerHomepage.this, ViewProfile.class);
                startActivity(profile);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHomepage.this, CustomerViewHistory.class);
                startActivity(intent);
            }
        });
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
            Intent in = new Intent(CustomerHomepage.this, CustomerHomepage.class);
            startActivity(in);
        }
        if(itemId==R.id.logoutBtn)
        {
            Intent in = new Intent(CustomerHomepage.this, MainActivity.class);
            startActivity(in);
            preferences.clearData(CustomerHomepage.this);
            finish();
        }

        return true;
    }
}
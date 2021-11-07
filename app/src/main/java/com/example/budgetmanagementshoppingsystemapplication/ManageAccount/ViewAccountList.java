package com.example.budgetmanagementshoppingsystemapplication.ManageAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.budgetmanagementshoppingsystemapplication.ManageProduct.ViewList;
import com.example.budgetmanagementshoppingsystemapplication.Model.Customer;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAccountList extends AppCompatActivity {
EditText search;
RecyclerView accountRecyclerView;
ArrayList<Customer> accountList;
AccountAdapter adapter;
DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account_list);
        search = findViewById(R.id.searchEditText);
        accountRecyclerView = findViewById(R.id.accountRecyclerView);
        accountRecyclerView.setHasFixedSize(true);
        accountRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref = FirebaseDatabase.getInstance().getReference();
        accountList = new ArrayList<>();
        adapter = new AccountAdapter(accountList,this);
        accountRecyclerView.setAdapter(adapter);

        ref.child("Customer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Customer customer = snapshot1.getValue(Customer.class);
                    accountList.add(customer);
                }
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
        Query query = FirebaseDatabase.getInstance().getReference().child("Customer").orderByChild("name")
                .startAt(searchName)
                .endAt(searchName+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accountList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Customer cust = dataSnapshot.getValue(Customer.class);
                    accountList.add(cust);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        if (itemId==R.id.homeBtn)
        {
            Intent in = new Intent(ViewAccountList.this, AdminHomepage.class);
            startActivity(in);
        }
        if(itemId==R.id.logoutBtn)
        {
            Intent in = new Intent(ViewAccountList.this, MainActivity.class);
            startActivity(in);
            preferences.clearData(ViewAccountList.this);
            finish();
        }

        return true;
    }
}
package com.example.budgetmanagementshoppingsystemapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewAccountDetail extends AppCompatActivity {
TextView name, username, id, phone, email, address;
DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account_detail);
        name = findViewById(R.id.custNameTextView);
        username = findViewById(R.id.custUsernameTextView);
        id = findViewById(R.id.custIDTextView);
        phone = findViewById(R.id.custContactTextView);
        email = findViewById(R.id.custEmailTextView);
        address = findViewById(R.id.custAddressTextView);

        Intent intent =getIntent();
        String uid = ""+intent.getStringExtra("uid");

        ref = FirebaseDatabase.getInstance().getReference();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer = snapshot.child("Customer").child(uid).getValue(Customer.class);
                name.setText(String.valueOf(customer.getName()));
                phone.setText(String.valueOf(customer.getPhone()));
                email.setText(String.valueOf(customer.getEmail()));
                address.setText(String.valueOf(customer.getAddress()));
                id.setText(uid);
                username.setText(String.valueOf(customer.getUsername()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
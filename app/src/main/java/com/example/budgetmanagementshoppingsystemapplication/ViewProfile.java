package com.example.budgetmanagementshoppingsystemapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewProfile extends AppCompatActivity {
Button editProfileBtn;
TextView name, username, phone, address, cardNumber, email;
DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        editProfileBtn = findViewById(R.id.btnEditProfile);
        name = findViewById(R.id.textViewName);
        username = findViewById(R.id.custUsername);
        phone = findViewById(R.id.custPhone);
        address = findViewById(R.id.custAddress);
        cardNumber = findViewById(R.id.custCard);
        email = findViewById(R.id.custEmail);

        ref = FirebaseDatabase.getInstance().getReference();
        username.setText(preferences.getDataStatus(ViewProfile.this));

        ref.child("Account").child(preferences.getDataStatus(this)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               String uid = snapshot.child("uid").getValue(String.class);
               preferences.setDataUserID(ViewProfile.this, uid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ref.child("Customer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer = snapshot.child(preferences.getDataUserID(ViewProfile.this)).getValue(Customer.class);
                name.setText(customer.getName());
                phone.setText(String.valueOf(customer.getPhone()));
                email.setText(String.valueOf(customer.getEmail()));
                address.setText(String.valueOf(customer.getAddress()));
                cardNumber.setText(String.valueOf(customer.getPaymentCard()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfile = new Intent(ViewProfile.this, EditProfile.class);
                startActivity(editProfile);
            }
        });
    }
}
package com.example.budgetmanagementshoppingsystemapplication.ManageAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetmanagementshoppingsystemapplication.Model.Customer;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ForgetPassword extends AppCompatActivity {
EditText email;
Button sendBtn;
TextView selectAccountTextView;
RecyclerView recyclerView;
DatabaseReference ref;
SelectAccountAdapter adapter;
ArrayList<Customer> selectAccountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        email = findViewById(R.id.editTextEmail);
        sendBtn = findViewById(R.id.sendBtn);
        selectAccountTextView = findViewById(R.id.selectAccount);
        recyclerView = findViewById(R.id.selectAccountRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref = FirebaseDatabase.getInstance().getReference();
        selectAccountList = new ArrayList<>();
        adapter = new SelectAccountAdapter(selectAccountList,this);
        recyclerView.setAdapter(adapter);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Customer").orderByChild("email")
                        .equalTo(email.getText().toString());

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        selectAccountList.clear();
                        for(DataSnapshot snapshot1:snapshot.getChildren())
                        {
                            Customer customer = snapshot1.getValue(Customer.class);
                            selectAccountList.add(customer);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}
package com.example.budgetmanagementshoppingsystemapplication.ManageAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetmanagementshoppingsystemapplication.Model.Customer;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
    Button updateProfileBtn;
    TextView edit_username, edit_name;
    EditText edit_phone, edit_address, edit_email;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        updateProfileBtn = findViewById(R.id.btnUpdateProfile);
        edit_name = findViewById(R.id.editName);
        edit_username = findViewById(R.id.custUsernameEdit);
        edit_phone = findViewById(R.id.custPhoneEdit);
        edit_address = findViewById(R.id.custAddressEdit);
        edit_email = findViewById(R.id.custEmailEdit);

        ref = FirebaseDatabase.getInstance().getReference();
        edit_username.setText(String.valueOf(preferences.getDataStatus(EditProfile.this)));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uid = snapshot.child("Account").child(edit_username.getText().toString()).child("uid").getValue(String.class);
                Customer customer =snapshot.child("Customer").child(uid).getValue(Customer.class);
                edit_name.setText(String.valueOf(customer.getName()));
                edit_phone.setText(String.valueOf(customer.getPhone()));
                edit_email.setText(String.valueOf(customer.getEmail()));
                edit_address.setText(String.valueOf(customer.getAddress()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String uid = snapshot.child("Account").child(preferences.getDataStatus(EditProfile.this)).child("uid").getValue(String.class);
                        ref.child("Customer").child(uid).child("phone").setValue(edit_phone.getText().toString());
                        ref.child("Customer").child(uid).child("email").setValue(edit_email.getText().toString());
                        ref.child("Customer").child(uid).child("address").setValue(edit_address.getText().toString());

                        Toast.makeText(EditProfile.this,"Profile updated successfully",Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(EditProfile.this, ViewProfile.class);
                        startActivity(in);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
}
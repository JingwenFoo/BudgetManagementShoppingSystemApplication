package com.example.budgetmanagementshoppingsystemapplication.ManageAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.budgetmanagementshoppingsystemapplication.AdminHomepage;
import com.example.budgetmanagementshoppingsystemapplication.CustomerHomepage;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
EditText username, password;
Button loginBtn, signUpBtn;
DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        loginBtn = (Button) findViewById(R.id.btnLogin);
        signUpBtn = (Button) findViewById(R.id.btnSignUp);

        ref = FirebaseDatabase.getInstance().getReference();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().length() != 0 && password.getText().length() != 0) {
                    String Username = username.getText().toString();
                    String Password = password.getText().toString();

                    ref.child("Account").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child(Username).exists())
                            {
                                if(snapshot.child(Username).child("password").getValue(String.class).equals(Password))
                                {
                                    if(snapshot.child(Username).child("username").getValue(String.class).equals("Admin")) {
                                        preferences.setDataLogin(MainActivity.this, true);
                                        preferences.setDataStatus(MainActivity.this, Username);
                                        Intent in = new Intent(MainActivity.this, AdminHomepage.class);
                                        startActivity(in);
                                    }
                                    else {
                                        preferences.setDataLogin(MainActivity.this,true);
                                        preferences.setDataStatus(MainActivity.this,Username);
                                        Intent in = new Intent(MainActivity.this, CustomerHomepage.class);
                                        startActivity(in);
                                    }
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this,"Invalid password",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Invalid username",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Missing data",Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpPage = new Intent(MainActivity.this, Register.class);
                startActivity(signUpPage);
            }
        });
    }

    protected void onStart()
    {
        super.onStart();
        if(preferences.getDataLogin(this))
        {
            if(preferences.getDataStatus(this).equals("Admin"))
            {
                Intent in = new Intent(MainActivity.this,AdminHomepage.class);
                startActivity(in);
                finish();
            }
            else
            {
                Intent in = new Intent(MainActivity.this,CustomerHomepage.class);
                startActivity(in);
                finish();
            }
        }
    }
}
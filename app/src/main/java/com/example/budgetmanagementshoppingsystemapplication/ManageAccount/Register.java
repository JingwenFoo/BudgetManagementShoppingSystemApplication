package com.example.budgetmanagementshoppingsystemapplication.ManageAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetmanagementshoppingsystemapplication.Model.Customer;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
EditText username, password, confirmPassword, name, contactNo, email, address;
Button register;
CheckBox checkBox;
TextView termsAndConditions, policy;
FirebaseAuth mAuth;
DatabaseReference ref = FirebaseDatabase.getInstance("https://bmssa-e95ff-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
Customer customer;

private static final Pattern PASSWORD_PATTERN =
        Pattern.compile("^"+
                "(?=.*[0-9])"+              //at least 1 digit
                "(?=.*[a-z])"+              //at least 1 lower case letter
                "(?=.*[A-Z])"+              //at least 1 upper case letter
                "(?=.*[@#$%^&*+=])"+        //at least 1 special character
                "(?=\\S+$)"+                //no white spaces
                ".{6,}"+                    //at least 6 characters
                "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.editTextRUsername);
        password = (EditText) findViewById(R.id.editTextRPass);
        confirmPassword = (EditText) findViewById(R.id.editTextRCPass);
        name = (EditText) findViewById(R.id.editTextRName);
        contactNo = (EditText) findViewById(R.id.editTextRPhone);
        email = (EditText) findViewById(R.id.editTextREmail);
        address = (EditText) findViewById(R.id.editTextRAddress);
        register = (Button) findViewById(R.id.btnRegister);
        checkBox = (CheckBox) findViewById(R.id.checkboxAgree);
        termsAndConditions = (TextView) findViewById(R.id.termsAndCondition);
        policy = (TextView) findViewById(R.id.policy);

        customer = new Customer();
        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        termsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this)
                        .setTitle("Terms and Conditions")
                        .setMessage(Html.fromHtml("<h5>"+"Welcome to Budget Management Shopping System Application!"+"</h5>"+
                                "<br>"+"These terms and conditions outline the rules and regulations for the use of Budget Management Shopping System Application." +
                                "<br><br>"+"By accessing this application, we assume you accept these terms and conditions. Do not continue to use Budget Management Shopping System Application if you do not agree to take all of the terms and conditions stated on this page." +
                                "<br><br>"+"The following terminology applies to these "+"<b>"+"Terms and Conditions"+"</b>"+","+"<b>"+"Privacy Statement and Disclaimer Notice"+"</b>"+" and "+"<b>"+"all Agreements"+"</b>"+". All terms refer to the offer, acceptance and consideration of payment necessary to undertake the process of our assistance to you in the most appropriate manner for the express purpose of meeting your needs."));
                builder.setPositiveButton("Ok", null);
                builder.create().show();
            }
        });

        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this)
                        .setTitle("Privacy Policy")
                        .setMessage(Html.fromHtml("At "+"<b>"+"Budget Management Shopping System Application"+"</b>"+", we collect and manage user data according to the following Privacy Policy."+
                                "<br>"+"We collect information you provide directly to use. For example, we collect information when you create an account of Budget Management Shopping System Application." +
                                "<br><br>"+"The type of information we may collect include your name, email address, postal address, and other contact or identifying information you choose to provide."));
                builder.setPositiveButton("Ok", null);
                builder.create().show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.length()==0)
                {
                    username.setError("Enter username");
                }
                if(name.length()==0)
                {
                    name.setError("Enter name");
                }
                if(contactNo.length()==0)
                {
                    contactNo.setError("Enter contact number");
                }
                if(email.length()==0)
                {
                    email.setError("Enter email");
                }
                if(address.length()==0)
                {
                    address.setError("Enter address");
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
                    email.setError("Please enter a valid email address");
                }
                if(!password.getText().toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*+=])(?=\\S+$).{6,}$"))
                {
                    password.setError("Password too weak");
                }
                if(!confirmPassword.getText().toString().matches(password.getText().toString()))
                {
                    confirmPassword.setError("Confirm password must be the same as Password");
                }
                if (!checkBox.isChecked())
                {
                    Toast.makeText(Register.this, "You have not accept our terms and conditions",Toast.LENGTH_SHORT).show();
                }
                if(checkBox.isChecked() && username.length()!=0 && name.length()!=0 && contactNo.length()!=0 && address.length()!=0 && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches() && password.getText().toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*+=])(?=\\S+$).{6,}$") && confirmPassword.getText().toString().matches(password.getText().toString()))
                {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                String uid = task.getResult().getUser().getUid();

                                customer.setUid(uid);
                                customer.setEmail(email.getText().toString());
                                customer.setPassword(password.getText().toString());
                                customer.setName(name.getText().toString().toUpperCase());
                                customer.setPhone(contactNo.getText().toString());
                                customer.setAddress(address.getText().toString());

                                Customer account = new Customer(uid, username.getText().toString(), password.getText().toString());
                                Customer customer = new Customer(uid, username.getText().toString(), email.getText().toString(), name.getText().toString().toUpperCase(), contactNo.getText().toString(), address.getText().toString(), "null");
                                ref.child("Account").child(username.getText().toString()).setValue(account);
                                ref.child("Customer").child(uid).setValue(customer);
                                Toast.makeText(Register.this, "Register successfully", Toast.LENGTH_SHORT).show();
                                Intent login = new Intent(Register.this, MainActivity.class);
                                startActivity(login);
                            } else {
                                Toast.makeText(Register.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else
                    Toast.makeText(Register.this, "All field must be filled up",Toast.LENGTH_SHORT).show();

            }
        });

    }

}
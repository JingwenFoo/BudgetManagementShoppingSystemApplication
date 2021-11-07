package com.example.budgetmanagementshoppingsystemapplication.ManageAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateNewPassword extends AppCompatActivity {
EditText newPassword, confirmNewPassword;
Button resetBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_new_password);
        newPassword = findViewById(R.id.editnewPassword);
        confirmNewPassword = findViewById(R.id.editconfirmNewPassword);
        resetBtn = findViewById(R.id.btnResetPasswrd);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newPassword.getText().toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*+=])(?=\\S+$).{6,}$"))
                {
                    if(newPassword.getText().toString().matches(confirmNewPassword.getText().toString()))
                    {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Account").child(username);
                        ref.child("password").setValue(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(UpdateNewPassword.this, "Password reset successfully",Toast.LENGTH_SHORT).show();
                                Intent backToLogin = new Intent(UpdateNewPassword.this, MainActivity.class);
                                startActivity(backToLogin);
                            }
                        });
                    }else
                        Toast.makeText(UpdateNewPassword.this, "Confirm new password must be the same as New Password",Toast.LENGTH_SHORT).show();
                }
                else
                    newPassword.setError("Password too weak");
            }

        });


    }
}
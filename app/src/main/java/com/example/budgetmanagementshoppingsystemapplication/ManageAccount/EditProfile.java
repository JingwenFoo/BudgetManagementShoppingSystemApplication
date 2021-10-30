package com.example.budgetmanagementshoppingsystemapplication.ManageAccount;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetmanagementshoppingsystemapplication.ManageProduct.EditForm;
import com.example.budgetmanagementshoppingsystemapplication.ManageProduct.ViewForm;
import com.example.budgetmanagementshoppingsystemapplication.Model.Customer;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity {
    Button updateProfileBtn, uploadProfilePicBtn;
    TextView edit_username, edit_name;
    EditText edit_phone, edit_address, edit_email;
    DatabaseReference ref;
    ImageView profilePic;
    StorageReference storageReference;
    ProgressDialog progressDialogUpdate;
    Uri profileUriUpdate;
    String urlUpdate;
    public static final int PICK_IMAGE=100;

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
        uploadProfilePicBtn = findViewById(R.id.editProficePic);
        profilePic = findViewById(R.id.editdisplayprofilePic);

        progressDialogUpdate = new ProgressDialog(this);

        ref = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile images/");
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
                if(!customer.getProfilePic().matches("null"))
                    Picasso.get().load(customer.getProfilePic()).into(profilePic);
                else
                    profilePic.setImageResource(R.drawable.profile_icon);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ActivityResultLauncher<Intent> pickImageResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK)
                        {
                            Intent data = result.getData();
                            profileUriUpdate=data.getData();
                            profilePic.setImageURI(profileUriUpdate);
                        }
                    }
                });

        uploadProfilePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                pickImageResultLauncher.launch(intent);
            }
        });

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_address.getText().length()!=0 && edit_email.getText().length()!=0 && edit_phone.getText().length()!=0)
                {
                    progressDialogUpdate.setTitle("Update Profile");
                    progressDialogUpdate.setCanceledOnTouchOutside(false);
                    progressDialogUpdate.show();
                    if (profileUriUpdate != null)
                    {
                        StorageReference sRef = storageReference.child(System.currentTimeMillis() + "." + getExtensionFile(profileUriUpdate));
                        sRef.putFile(profileUriUpdate).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        urlUpdate = uri.toString();

                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("email", edit_email.getText().toString());
                                        map.put("phone", edit_phone.getText().toString());
                                        map.put("address", edit_address.getText().toString());
                                        map.put("profilePic", urlUpdate);

                                        progressDialogUpdate.dismiss();
                                        ref.child("Customer").child(preferences.getDataUserID(EditProfile.this)).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(EditProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(EditProfile.this, ViewProfile.class));
                                                    finish();
                                                } else {
                                                    Toast.makeText(EditProfile.this, "Failed update profile", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EditProfile.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressDialogUpdate.dismiss();
                                    }
                                });
                            }
                        });
                    }
                    else
                    {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("email", edit_email.getText().toString());
                        map.put("phone", edit_phone.getText().toString());
                        map.put("address", edit_address.getText().toString());

                        progressDialogUpdate.dismiss();
                        ref.child("Customer").child(preferences.getDataUserID(EditProfile.this)).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(EditProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(EditProfile.this, ViewProfile.class));
                                    finish();
                                } else
                                    Toast.makeText(EditProfile.this, "Failed update profile", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else
                {
                    Toast.makeText(EditProfile.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }
    public String getExtensionFile(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return  map.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
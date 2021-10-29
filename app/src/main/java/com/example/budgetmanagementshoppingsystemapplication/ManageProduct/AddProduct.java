package com.example.budgetmanagementshoppingsystemapplication.ManageProduct;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

public class AddProduct extends AppCompatActivity {

    ImageView addProImg;
    Button AddUploadBtn, AddScanBtn, AddProBtn;
    EditText ed_AddProCode, ed_AddProName, ed_AddProCategDetail, ed_AddProBrand, ed_AddProPrice, ed_SellPrice, ed_AddProDescp, ed_AddProStock;
    DatabaseReference ref;
    StorageReference storageReference;
    Uri imageUri;
    String url;
    Spinner mySpinner;
    public static final int PICK_IMAGE = 100;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        addProImg = findViewById(R.id.productImage);
        AddUploadBtn = findViewById(R.id.uploadImageBtn);
        AddScanBtn = findViewById(R.id.scanBtn);
        AddProBtn = findViewById(R.id.addProductBtn);
        ed_AddProCode = findViewById(R.id.editTextProductID);
        ed_AddProName = findViewById(R.id.editTextProductName);
        ed_AddProCategDetail = findViewById(R.id.editTextCategoryDetail);
        ed_AddProBrand = findViewById(R.id.editTextBrand);
        ed_AddProPrice = findViewById(R.id.editTextPrice);
        ed_SellPrice = findViewById(R.id.editTextSellingPrice);
        ed_AddProDescp = findViewById(R.id.editTextDescp);
        ed_AddProStock = findViewById(R.id.editTextStock);
        mySpinner = findViewById(R.id.spinnerCategory);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddProduct.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.category));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        progressDialog = new ProgressDialog(this);
        storageReference = FirebaseStorage.getInstance().getReference().child("Product images/");

        ActivityResultLauncher<Intent> pickImageResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageUri = data.getData();
                            addProImg.setImageURI(imageUri);
                        } else {
                            Toast.makeText(AddProduct.this, "No image is selected", Toast.LENGTH_SHORT).show();
                            addProImg.setImageResource(R.drawable.ic_image_loading);
                        }
                    }
                });

        AddUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                pickImageResultLauncher.launch(intent);
            }
        });

        AddProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_AddProCode.getText().length() != 0 && imageUri != null && ed_AddProName.getText().length() != 0 && ed_AddProBrand.getText().length() != 0 && ed_AddProPrice.getText().length() != 0 && ed_AddProDescp.getText().length() != 0 && ed_AddProStock.getText().length() != 0) {
                    addNewProduct();
                } else {
                    Toast.makeText(AddProduct.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });


        AddScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });
    }

    private void addNewProduct() {
        progressDialog.setTitle("Add New Product");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StorageReference sRef = storageReference.child(System.currentTimeMillis() + "." + getExtensionFile(imageUri));
        sRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        url = uri.toString();
                        ref = FirebaseDatabase.getInstance().getReference().child("Product");

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("productID", ed_AddProCode.getText().toString());
                        map.put("productName", ed_AddProName.getText().toString());
                        map.put("productBrand", ed_AddProBrand.getText().toString());
                        map.put("category",String.valueOf(mySpinner.getSelectedItem()));
                        map.put("categoryDetail", ed_AddProCategDetail.getText().toString());
                        map.put("productPrice", Float.valueOf(ed_AddProPrice.getText().toString()));
                        map.put("sellingPrice", Float.valueOf(ed_SellPrice.getText().toString()));
                        map.put("productImage", url);
                        map.put("productDescription", ed_AddProDescp.getText().toString());
                        map.put("stockAvailable", ed_AddProStock.getText().toString());

                        progressDialog.dismiss();
                        ref.child(ed_AddProCode.getText().toString()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AddProduct.this, "New product added successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddProduct.this, ViewList.class));
                                    finish();
                                } else {
                                    Toast.makeText(AddProduct.this, "Failed add new product", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProduct.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }

    public String getExtensionFile(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return  map.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void scanCode()
    {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(CaptureAct.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scanning Code");
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null)
        {
            if(result.getContents() != null)
            {
                ed_AddProCode.setText(result.getContents());
            }
            else
            {
                Toast.makeText(AddProduct.this, "No result",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
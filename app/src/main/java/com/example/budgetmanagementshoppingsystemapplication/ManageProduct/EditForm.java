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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class EditForm extends AppCompatActivity {

    ImageView Product_img;
    Button UploadBtn, UpdateBtn;
    TextView ed_ProCode;
    EditText ed_ProName, ed_Category, ed_Brand, ed_Price, ed_SellingPrice, ed_Descp, ed_Stock;
    ProgressDialog progressDialogUpdate;

    Uri imageUriUpdate;
    String urlUpdate;
    public static final int PICK_IMAGE=100;

    DatabaseReference ref;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);

        Product_img = findViewById(R.id.productImageUpdate);
        UploadBtn = findViewById(R.id.uploadImageBtnUpdate);
        UpdateBtn = findViewById(R.id.updateProductBtn);
        ed_ProCode = findViewById(R.id.editTextProductIDUpdate);
        ed_ProName = findViewById(R.id.editTextProductNameUpdate);
        ed_Category = findViewById(R.id.editTextCategoryUpdate);
        ed_Brand = findViewById(R.id.editTextBrandUpdate);
        ed_Price = findViewById(R.id.editTextPriceUpdate);
        ed_SellingPrice = findViewById(R.id.editTextSellingPriceUpdate);
        ed_Descp = findViewById(R.id.editTextDescpUpdate);
        ed_Stock = findViewById(R.id.editTextStockUpdate);

        progressDialogUpdate = new ProgressDialog(this);

        ref = FirebaseDatabase.getInstance().getReference().child("Product");
        storageReference = FirebaseStorage.getInstance().getReference().child("Product images/");

        Intent intent = getIntent();
        String editPID = ""+intent.getStringExtra("editPID");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Product productDetail = snapshot.child(editPID).getValue(Product.class);
                ed_ProName.setText(String.valueOf(productDetail.getProductName()));
                ed_ProCode.setText(editPID);
                ed_Category.setText(String.valueOf(productDetail.getCategory()));
                ed_Brand.setText(String.valueOf(productDetail.getProductBrand()));
                ed_Price.setText(String.format("%.2f",productDetail.getProductPrice()));
                ed_SellingPrice.setText(String.format("%.2f",productDetail.getSellingPrice()));
                ed_Stock.setText(String.valueOf(productDetail.getStockAvailable()));
                ed_Descp.setText(String.valueOf(productDetail.getProductDescription()));
                Picasso.get().load(String.valueOf(productDetail.getProductImage())).into(Product_img);
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
                            imageUriUpdate=data.getData();
                            Product_img.setImageURI(imageUriUpdate);
                        }
                    }
                });

        UploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                pickImageResultLauncher.launch(intent);
            }
        });

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_ProCode.getText().length() != 0 && ed_ProName.getText().length() != 0 && ed_Brand.getText().length() != 0 && ed_Category.getText().length() != 0 && ed_Price.getText().length() != 0 && ed_SellingPrice.getText().length()!=0 && ed_Descp.getText().length() != 0 && ed_Stock.getText().length() != 0) {
                    updateProduct();
                } else {
                    Toast.makeText(EditForm.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateProduct() {
        progressDialogUpdate.setTitle("Update Product");
        progressDialogUpdate.setCanceledOnTouchOutside(false);
        progressDialogUpdate.show();

        StorageReference sRef = storageReference.child(System.currentTimeMillis() + "." + getExtensionFile(imageUriUpdate));
        sRef.putFile(imageUriUpdate).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        urlUpdate = uri.toString();
                        ref = FirebaseDatabase.getInstance().getReference().child("Product");

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("productID", ed_ProCode.getText().toString());
                        map.put("productName", ed_ProName.getText().toString());
                        map.put("productBrand", ed_Brand.getText().toString());
                        map.put("category", ed_Category.getText().toString());
                        map.put("productPrice", Float.valueOf(ed_Price.getText().toString()));
                        map.put("sellingPrice", Float.valueOf(ed_SellingPrice.getText().toString()));
                        map.put("productImage", urlUpdate);
                        map.put("productDescription", ed_Descp.getText().toString());
                        map.put("stockAvailable", ed_Stock.getText().toString());

                        progressDialogUpdate.dismiss();
                        ref.child(ed_ProCode.getText().toString()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(EditForm.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(EditForm.this, ViewForm.class));
                                    finish();
                                } else {
                                    Toast.makeText(EditForm.this, "Failed update product", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditForm.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialogUpdate.dismiss();
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null)
        {
            if(result.getContents() != null)
            {
                ed_ProCode.setText(result.getContents());
            }
            else
            {
                Toast.makeText(EditForm.this, "No result",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
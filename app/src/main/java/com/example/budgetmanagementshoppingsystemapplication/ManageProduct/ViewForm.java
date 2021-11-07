package com.example.budgetmanagementshoppingsystemapplication.ManageProduct;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.AdminHomepage;
import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.MainActivity;
import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewForm extends AppCompatActivity {

    ImageView DisplayProImg;
    Button editProBtn;
    TextView ProName, ProCode, ProCateg, ProCategDetail, ProPrice, ProBrand, ProDesp, ProStock, ProSellingPrice;

    Uri imageUriView;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);

        DisplayProImg = findViewById(R.id.productImageView);
        editProBtn = findViewById(R.id.editProductBtn);
        ProName = findViewById(R.id.textViewProductName);
        ProCode = findViewById(R.id.textViewProductID);
        ProCateg = findViewById(R.id.textViewCategory);
        ProCategDetail = findViewById(R.id.textViewCategoryDetail);
        ProBrand = findViewById(R.id.textViewBrand);
        ProPrice = findViewById(R.id.textViewPrice);
        ProSellingPrice = findViewById(R.id.textViewSellingPrice);
        ProDesp = findViewById(R.id.textViewDescp);
        ProStock = findViewById(R.id.textViewStock);

        ref = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        String productID = ""+intent.getStringExtra("pid");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product productDetail = snapshot.child("Product").child(productID).getValue(Product.class);
                ProName.setText(String.valueOf(productDetail.getProductName()));
                ProCode.setText(productID);
                ProCateg.setText(String.valueOf(productDetail.getCategory()));
                ProCategDetail.setText(String.valueOf(productDetail.getCategoryDetail()));
                ProBrand.setText(String.valueOf(productDetail.getProductBrand()));
                ProPrice.setText(String.format("%.2f",productDetail.getProductPrice()));
                ProSellingPrice.setText(String.format("%.2f",productDetail.getSellingPrice()));
                ProStock.setText(String.valueOf(productDetail.getStockAvailable()));
                ProDesp.setText(String.valueOf(productDetail.getProductDescription()));
                Picasso.get().load(String.valueOf(productDetail.getProductImage())).into(DisplayProImg);

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
                            imageUriView=data.getData();
                            DisplayProImg.setImageURI(imageUriView);
                        }
                    }
                });

        editProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(ViewForm.this, EditForm.class);
                edit.putExtra("editPID",productID);
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId==R.id.homeBtn)
        {
            Intent in = new Intent(ViewForm.this, AdminHomepage.class);
            startActivity(in);
        }
        if(itemId==R.id.logoutBtn)
        {
            Intent in = new Intent(ViewForm.this, MainActivity.class);
            startActivity(in);
            preferences.clearData(ViewForm.this);
            finish();
        }

        return true;
    }
}
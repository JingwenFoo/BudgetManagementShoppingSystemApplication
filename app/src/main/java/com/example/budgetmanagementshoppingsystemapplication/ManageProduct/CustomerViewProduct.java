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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.CustomerHomepage;
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

public class CustomerViewProduct extends AppCompatActivity {

    ImageView proimg;
    TextView tv_ProCode, tv_ProName, tv_Category, tv_CategoryDetail, tv_Brand, tv_Price, tv_Descp, tv_Stock;

    DatabaseReference ref;
    Uri imageUriView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_product);

        proimg = findViewById(R.id.CustproductImageView);
        tv_ProCode = findViewById(R.id.CusttextViewProductID);
        tv_ProName = findViewById(R.id.CusttextViewProductName);
        tv_Category = findViewById(R.id.CusttextViewCategory);
        tv_CategoryDetail = findViewById(R.id.CusttextViewCategoryDetail);
        tv_Brand = findViewById(R.id.CusttextViewBrand);
        tv_Price = findViewById(R.id.CusttextViewPrice);
        tv_Descp = findViewById(R.id.CusttextViewDescp);
        tv_Stock = findViewById(R.id.CusttextViewStock);

        ref = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        String productID = ""+intent.getStringExtra("prodID");
        System.out.println("ID = "+productID);
        ref.child("Product").child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product productDetail = snapshot.getValue(Product.class);
                tv_ProName.setText(String.valueOf(productDetail.getProductName()));
                tv_ProCode.setText(productID);
                tv_Category.setText(String.valueOf(productDetail.getCategory()));
                tv_CategoryDetail.setText(String.valueOf(productDetail.getCategoryDetail()));
                tv_Brand.setText(String.valueOf(productDetail.getProductBrand()));
                tv_Price.setText(String.format("%.2f",productDetail.getProductPrice()));
                if (Integer.parseInt(productDetail.getStockAvailable())<=0)
                    tv_Stock.setText("Out of Stock");
                else
                    tv_Stock.setText(String.valueOf(productDetail.getStockAvailable()));
                tv_Descp.setText(String.valueOf(productDetail.getProductDescription()));
                Picasso.get().load(String.valueOf(productDetail.getProductImage())).into(proimg);

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
                            proimg.setImageURI(imageUriView);
                        }
                    }
                });

    }
}
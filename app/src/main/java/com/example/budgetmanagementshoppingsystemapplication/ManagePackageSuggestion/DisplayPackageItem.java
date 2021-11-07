package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.CustomerHomepage;
import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.MainActivity;
import com.example.budgetmanagementshoppingsystemapplication.ManagePayment.MainCheckoutAdapter;
import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.Model.SuggestPackage;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayPackageItem extends AppCompatActivity {
TextView packageNum;
RecyclerView recyclerView;
MainPackageAdapter adapter;
DatabaseReference refPackage, refProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_package_item);

        packageNum = findViewById(R.id.packageselectedNum);
        recyclerView = findViewById(R.id.packageselectedRecycleView);

        Intent intent = getIntent();
        List<SuggestPackage> packageItemList = new ArrayList<>();
        List<Product> packageItemProductList = new ArrayList<>();
        String packageNumber = intent.getStringExtra("packageNum");
        String packageID = intent.getStringExtra("packageID");
        packageNum.setText(packageNumber);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        refPackage = FirebaseDatabase.getInstance().getReference().child("SuggestPackage").child(preferences.getDataUserID(this)).child(packageID).child("packageItem");
        refProduct = FirebaseDatabase.getInstance().getReference().child("Product");

        refPackage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String packageItemID = dataSnapshot.child("productID").getValue(String.class);
                    packageItemList.add(new SuggestPackage(packageItemID));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        refProduct.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Product product = dataSnapshot.getValue(Product.class);
                    for (int i=0; i<packageItemList.size(); i++)
                    {
                        if (product.getProductID().matches(packageItemList.get(i).getProductID()))
                        {
                            packageItemProductList.add(product);
                        }
                    }


                }
                adapter = new MainPackageAdapter(DisplayPackageItem.this, packageItemProductList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
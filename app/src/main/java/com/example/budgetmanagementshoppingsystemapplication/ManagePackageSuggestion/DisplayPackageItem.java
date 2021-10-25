package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.budgetmanagementshoppingsystemapplication.ManagePayment.MainCheckoutAdapter;
import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;

import java.util.ArrayList;
import java.util.List;

public class DisplayPackageItem extends AppCompatActivity {
TextView packageNum;
RecyclerView recyclerView;
MainPackageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_package_item);

        packageNum = findViewById(R.id.packageselectedNum);
        recyclerView = findViewById(R.id.packageselectedRecycleView);

        Intent intent = getIntent();
        List<Product> packageList = new ArrayList<>();
        packageList = (List<Product>)intent.getSerializableExtra("packageItem");
        String packageNumber = intent.getStringExtra("packageNum");
        String totalPrice = intent.getStringExtra("totalPricePackage");

        packageNum.setText(packageNumber);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainPackageAdapter(this, packageList);
        System.out.println(totalPrice);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;

import java.util.ArrayList;
import java.util.List;

public class PackageSuggestion extends AppCompatActivity {
    RecyclerView recyclerView;
    PackageSuggestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_suggestion);

        recyclerView = findViewById(R.id.packagelistRecycleview);

        Intent intent = getIntent();
        ArrayList<List<Product>> packageList = new ArrayList<>();
        packageList = (ArrayList<List<Product>>)intent.getSerializableExtra("packageData");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PackageSuggestionAdapter(this, packageList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;

import java.util.ArrayList;
import java.util.List;

public class OnPromotionDisplay extends AppCompatActivity {
RecyclerView recyclerView;
OnPromotionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_promotion_display);

        recyclerView = findViewById(R.id.recyclerViewOnPromotionDisplay);

        Intent intent = getIntent();
        List<Product> promotionList = new ArrayList<>();
        promotionList = (ArrayList<Product>)intent.getSerializableExtra("promotionList");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OnPromotionAdapter(this, promotionList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
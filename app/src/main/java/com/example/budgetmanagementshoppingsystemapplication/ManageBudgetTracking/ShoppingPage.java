package com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion.SelectCategory;
import com.example.budgetmanagementshoppingsystemapplication.R;

public class ShoppingPage extends AppCompatActivity {
Button shoppingNowBtn, packageSuggestBtn, viewPromotionBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_page);
        shoppingNowBtn = findViewById(R.id.shoppingNowBtn);
        packageSuggestBtn = findViewById(R.id.packageSuggestBtn);
        viewPromotionBtn = findViewById(R.id.viewPromotionBtn);

        shoppingNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopNow = new Intent(ShoppingPage.this, ViewCart.class);
                startActivity(shopNow);
            }
        });

        viewPromotionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent packageSuggest = new Intent(ShoppingPage.this, SelectCategory.class);
                startActivity(packageSuggest);
            }
        });
    }
}
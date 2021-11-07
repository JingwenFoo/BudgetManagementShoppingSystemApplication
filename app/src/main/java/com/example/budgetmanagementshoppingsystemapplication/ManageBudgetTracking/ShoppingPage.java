package com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.CustomerHomepage;
import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.MainActivity;
import com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion.OnPromotionDisplay;
import com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion.SelectCategory;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
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

        packageSuggestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent packageSuggest = new Intent(ShoppingPage.this, SelectCategory.class);
                startActivity(packageSuggest);
            }
        });

        viewPromotionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewPromotion = new Intent(ShoppingPage.this, OnPromotionDisplay.class);
                startActivity(viewPromotion);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.homeBtn)
        {
            Intent in = new Intent(ShoppingPage.this, CustomerHomepage.class);
            startActivity(in);
        }
        if(itemId==R.id.logoutBtn)
        {
            Intent in = new Intent(ShoppingPage.this, MainActivity.class);
            startActivity(in);
            preferences.clearData(ShoppingPage.this);
            finish();
        }

        return true;
    }
}
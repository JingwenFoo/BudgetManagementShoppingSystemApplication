package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectCategory extends AppCompatActivity {
RecyclerView recyclerViewCategoryDetail;
Button viewBtn;
DatabaseReference ref;
List<String> categoryItemList = new ArrayList<>();
List<Product> promotionProduct = new ArrayList<>();
CategoryItemAdapter adapter;
ArrayList<Product> freshList, grocList, bevList, houseList, pCareList, clothList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        recyclerViewCategoryDetail = findViewById(R.id.categoryitemRecycleView);
        viewBtn = findViewById(R.id.viewBtn);
        ref = FirebaseDatabase.getInstance().getReference();

        recyclerViewCategoryDetail.setHasFixedSize(true);
        recyclerViewCategoryDetail.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new CategoryItemAdapter(this, categoryItemList);
        recyclerViewCategoryDetail.setAdapter(adapter);

        ref.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Product categoryItem = dataSnapshot.getValue(Product.class);
                    if (!categoryItemList.contains(categoryItem.getCategoryDetail()))
                    {
                        categoryItemList.add(categoryItem.getCategoryDetail());
                    }
                }

                Collections.sort(categoryItemList, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                });
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedCategory = adapter.getCategorySelectedArrayList();
               System.out.println("Selected checkbox = " + selectedCategory);

                Float budget = Float.parseFloat(preferences.getDataBudget(SelectCategory.this));
                Float freshBud = Float.parseFloat(preferences.getDataFreshBudget(SelectCategory.this));
                Float groBud = Float.parseFloat(preferences.getDataGroBudget(SelectCategory.this));
                Float bevBud = Float.parseFloat(preferences.getDataBevBudget(SelectCategory.this));
                Float houseBud = Float.parseFloat(preferences.getDataHouseBudget(SelectCategory.this));
                Float PCareBud = Float.parseFloat(preferences.getDataPCareBudget(SelectCategory.this));
                Float clothBud = Float.parseFloat(preferences.getDataClothBudget(SelectCategory.this));

                Float fresh = (freshBud / 100) * budget;
                Float groc = (groBud / 100) * budget;
                Float bev = (bevBud / 100) * budget;
                Float house = (houseBud / 100) * budget;
                Float pCare = (PCareBud / 100) * budget;
                Float cloth = (clothBud / 100) * budget;

                Float freshTotal = Float.parseFloat(preferences.getDataFreshBudgetTotal(SelectCategory.this));
                Float groTotal = Float.parseFloat(preferences.getDataGroBudgetTotal(SelectCategory.this));
                Float bevTotal = Float.parseFloat(preferences.getDataBevBudgetTotal(SelectCategory.this));
                Float houseTotal = Float.parseFloat(preferences.getDataHouseBudgetTotal(SelectCategory.this));
                Float PCareTotal = Float.parseFloat(preferences.getDataPCareBudgetTotal(SelectCategory.this));
                Float clothTotal = Float.parseFloat(preferences.getDataClothBudgetTotal(SelectCategory.this));

                grocList = new ArrayList<>();
                freshList = new ArrayList<>();
                bevList = new ArrayList<>();
                houseList = new ArrayList<>();
                pCareList = new ArrayList<>();
                clothList = new ArrayList<>();

                for (int i = 0; i < selectedCategory.size(); i++) {
                    String selectedCategoryItem = selectedCategory.get(i);

                    ref.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                Product product = snapshot1.getValue(Product.class);

                                if (product.getCategoryDetail() != null && product.getCategoryDetail().matches(selectedCategoryItem)) {
                                    if (product.getCategory().matches("Fresh") && product.getSellingPrice()<product.getProductPrice())
                                        freshList.add(product);
                                    if (product.getCategory().matches("Groceries") && product.getSellingPrice()<product.getProductPrice())
                                        grocList.add(product);
                                    if (product.getCategory().matches("Beverages") && product.getSellingPrice()<product.getProductPrice())
                                        bevList.add(product);
                                    if (product.getCategory().matches("Household") && product.getSellingPrice()<product.getProductPrice())
                                        houseList.add(product);
                                    if (product.getCategory().matches("Personal Care") && product.getSellingPrice()<product.getProductPrice())
                                        pCareList.add(product);
                                    if (product.getCategory().matches("Clothes") && product.getSellingPrice()<product.getProductPrice())
                                        clothList.add(product);

                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                ref.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        promotionProduct.clear();
                        promotionProduct.addAll(freshList);
                        promotionProduct.addAll(grocList);
                        promotionProduct.addAll(bevList);
                        promotionProduct.addAll(houseList);
                        promotionProduct.addAll(pCareList);
                        promotionProduct.addAll(clothList);

                        ArrayList<List<Product>> packageData = new ArrayList<List<Product>>();
                        packageData.add(grocList);
                        packageData.add(freshList);
                        packageData.add(0,houseList);
                        System.out.println("All= "+packageData);
                        System.out.println("Position 0="+grocList.get(0));

                        Intent intent = new Intent(SelectCategory.this, PackageSuggestion.class);
                        intent.putExtra("promotionList", (Serializable) promotionProduct);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
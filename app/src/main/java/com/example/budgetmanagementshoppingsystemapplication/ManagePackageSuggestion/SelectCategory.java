package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectCategory extends AppCompatActivity {
RecyclerView recyclerViewCategoryDetail;
Button suggestBtn;
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
        suggestBtn = findViewById(R.id.suggestBtn);
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

        suggestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedCategory = adapter.getCategorySelectedArrayList();

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
                                    if (product.getCategory().matches("Fresh"))
                                    {
                                        if(product.getSellingPrice()+freshTotal<fresh)
                                                freshList.add(product);

                                    }

                                    if (product.getCategory().matches("Groceries"))
                                    {
                                        if(product.getSellingPrice()+groTotal<groc)
                                            grocList.add(product);
                                    }

                                    if (product.getCategory().matches("Beverages"))
                                    {
                                        if(product.getSellingPrice()+bevTotal<bev)
                                            bevList.add(product);
                                    }

                                    if (product.getCategory().matches("Household"))
                                    {
                                        if(product.getSellingPrice()+houseTotal<house)
                                            houseList.add(product);
                                    }

                                    if (product.getCategory().matches("Personal Care"))
                                    {
                                        if(product.getSellingPrice()+PCareTotal<pCare)
                                            pCareList.add(product);
                                    }

                                    if (product.getCategory().matches("Clothes"))
                                    {
                                        if(product.getSellingPrice()+clothTotal<cloth)
                                            clothList.add(product);
                                    }


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
                        List<Product> tempPackage = new ArrayList<>();
                        List<Product> tempFreshList = new ArrayList<>();
                        List<Product> tempGrocList = new ArrayList<>();
                        List<Product> tempBevList = new ArrayList<>();
                        List<Product> tempHouseList = new ArrayList<>();
                        List<Product> tempPCareList = new ArrayList<>();
                        List<Product> tempClothList = new ArrayList<>();

                        if(freshList.size()!=0)
                        {
                            for(int i=0; i<freshList.size()-1; i++)
                            {
                                for(int j=i+1; j<freshList.size(); j++)
                                {
                                    if (freshList.get(i).getCategoryDetail().equalsIgnoreCase(freshList.get(j).getCategoryDetail()))
                                    {
                                        tempFreshList.add(freshList.get(j));
                                        freshList.remove(j);
                                    }
                                }
                            }
                            tempPackage.addAll(freshList);

                        }

                        if(grocList.size()!=0)
                        {
                            for(int i=0; i<grocList.size()-1; i++)
                            {
                                for(int j=i+1; j<grocList.size(); j++)
                                {
                                    if (grocList.get(i).getCategoryDetail().equalsIgnoreCase(grocList.get(j).getCategoryDetail()))
                                    {
                                        tempGrocList.add(grocList.get(j));
                                        grocList.remove(j);
                                    }
                                }
                            }
                            tempPackage.addAll(grocList);
                        }

                        if(bevList.size()!=0)
                        {
                            for(int i=0; i<bevList.size()-1; i++)
                            {
                                for(int j=i+1; j<bevList.size(); j++)
                                {
                                    if (bevList.get(i).getCategoryDetail().equalsIgnoreCase(bevList.get(j).getCategoryDetail()))
                                    {
                                        tempBevList.add(bevList.get(j));
                                        bevList.remove(j);
                                    }
                                }
                            }
                            tempPackage.addAll(bevList);
                        }

                        if(houseList.size()!=0)
                        {
                            for(int i=0; i<houseList.size()-1; i++)
                            {
                                for(int j=i+1; j<houseList.size(); j++)
                                {
                                    if (houseList.get(i).getCategoryDetail().equalsIgnoreCase(houseList.get(j).getCategoryDetail()))
                                    {
                                        tempHouseList.add(houseList.get(j));
                                        houseList.remove(j);
                                    }
                                }
                            }
                            tempPackage.addAll(houseList);
                        }

                        if(pCareList.size()!=0)
                        {
                            for(int i=0; i<pCareList.size()-1; i++)
                            {
                                for(int j=i+1; j<pCareList.size(); j++)
                                {
                                    if (pCareList.get(i).getCategoryDetail().equalsIgnoreCase(pCareList.get(j).getCategoryDetail()))
                                    {
                                        tempPCareList.add(pCareList.get(j));
                                        pCareList.remove(j);
                                    }
                                }
                            }
                            tempPackage.addAll(pCareList);
                        }

                        if(clothList.size()!=0)
                        {
                            for(int i=0; i<clothList.size()-1; i++)
                            {
                                for(int j=i+1; j<clothList.size(); j++)
                                {
                                    if (clothList.get(i).getCategoryDetail().equalsIgnoreCase(clothList.get(j).getCategoryDetail()))
                                    {
                                        tempClothList.add(clothList.get(j));
                                        clothList.remove(j);
                                    }
                                }
                            }
                            tempPackage.addAll(clothList);
                        }

                        Collections.sort(tempPackage, new Comparator<Product>() {
                            @Override
                            public int compare(Product o1, Product o2) {
                                return o1.getCategoryDetail().compareTo(o2.getCategoryDetail());
                            }
                        });
                        packageData.add(tempPackage);

                        if(tempFreshList.size()>0 || tempGrocList.size()>0 || tempBevList.size()>0 || tempHouseList.size()>0 || tempPCareList.size()>0 || tempClothList.size()>0)
                        {
                            List<Product> tempPackage1 = new ArrayList<>();
                            tempPackage1.addAll(tempPackage);

                            if(tempFreshList.size()!=0)
                            {
                                for(int i=0; i<tempPackage1.size(); i++)
                                {
                                    for(int j=0; j<tempFreshList.size(); j++)
                                    {
                                        if(tempPackage1.get(i).getCategoryDetail().matches(tempFreshList.get(j).getCategoryDetail()))
                                        {
                                            tempPackage1.add(tempFreshList.get(j));
                                            tempPackage1.remove(i);
                                            tempFreshList.remove(j);
                                        }
                                    }
                                }
                            }

                            if(tempGrocList.size()!=0)
                            {
                                for(int i=0; i<tempPackage1.size(); i++)
                                {
                                    for(int j=0; j<tempGrocList.size(); j++)
                                    {
                                        if(tempPackage1.get(i).getCategoryDetail().matches(tempGrocList.get(j).getCategoryDetail()))
                                        {
                                            tempPackage1.add(tempGrocList.get(j));
                                            tempPackage1.remove(i);
                                            tempGrocList.remove(j);
                                        }
                                    }
                                }
                            }

                            if(tempBevList.size()!=0)
                            {
                                for(int i=0; i<tempPackage1.size(); i++)
                                {
                                    for(int j=0; j<tempBevList.size(); j++)
                                    {
                                        if(tempPackage1.get(i).getCategoryDetail().matches(tempBevList.get(j).getCategoryDetail()))
                                        {
                                            tempPackage1.add(tempBevList.get(j));
                                            tempPackage1.remove(i);
                                            tempBevList.remove(j);
                                        }
                                    }
                                }
                            }

                            if(tempHouseList.size()!=0)
                            {
                                for(int i=0; i<tempPackage1.size(); i++)
                                {
                                    for(int j=0; j<tempHouseList.size(); j++)
                                    {
                                        if(tempPackage1.get(i).getCategoryDetail().matches(tempHouseList.get(j).getCategoryDetail()))
                                        {
                                            tempPackage1.add(tempHouseList.get(j));
                                            tempPackage1.remove(i);
                                            tempHouseList.remove(j);
                                        }
                                    }
                                }
                            }

                            if(tempPCareList.size()!=0)
                            {
                                for(int i=0; i<tempPackage1.size(); i++)
                                {
                                    for(int j=0; j<tempPCareList.size(); j++)
                                    {
                                        if(tempPackage1.get(i).getCategoryDetail().matches(tempPCareList.get(j).getCategoryDetail()))
                                        {
                                            tempPackage1.add(tempPCareList.get(j));
                                            tempPackage1.remove(i);
                                            tempPCareList.remove(j);
                                        }
                                    }
                                }
                            }

                            if(tempClothList.size()!=0)
                            {
                                for(int i=0; i<tempPackage1.size(); i++)
                                {
                                    for(int j=0; j<tempClothList.size(); j++)
                                    {
                                        if(tempPackage1.get(i).getCategoryDetail().matches(tempClothList.get(j).getCategoryDetail()))
                                        {
                                            tempPackage1.add(tempClothList.get(j));
                                            tempPackage1.remove(i);
                                            tempClothList.remove(j);
                                        }
                                    }
                                }
                            }
                            Collections.sort(tempPackage1, new Comparator<Product>() {
                                @Override
                                public int compare(Product o1, Product o2) {
                                    return o1.getCategoryDetail().compareTo(o2.getCategoryDetail());
                                }
                            });

                            packageData.add(tempPackage1);
                        }

                        Intent intent = new Intent(SelectCategory.this, PackageSuggestion.class);
                        intent.putExtra("packageData", (Serializable) packageData);
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
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
                        ArrayList<List<Product>> newFreshPackage = new ArrayList<List<Product>>();
                        ArrayList<List<Product>> newGroPackage = new ArrayList<List<Product>>();
                        ArrayList<List<Product>> newBevPackage = new ArrayList<List<Product>>();
                        ArrayList<List<Product>> newHousePackage = new ArrayList<List<Product>>();
                        ArrayList<List<Product>> newPCarePackage = new ArrayList<List<Product>>();
                        ArrayList<List<Product>> newClothPackage = new ArrayList<List<Product>>();
                        List<Product> tempPackage = new ArrayList<>();
                        List<Product> tempFreshList = new ArrayList<>();
                        List<Product> newFreshList = new ArrayList<>();
                        List<Product> tempGrocList = new ArrayList<>();
                        List<Product> newGroList = new ArrayList<>();
                        List<Product> tempBevList = new ArrayList<>();
                        List<Product> newBevList = new ArrayList<>();
                        List<Product> tempHouseList = new ArrayList<>();
                        List<Product> newHouseList = new ArrayList<>();
                        List<Product> tempPCareList = new ArrayList<>();
                        List<Product> newPCareList = new ArrayList<>();
                        List<Product> tempClothList = new ArrayList<>();
                        List<Product> newClothList = new ArrayList<>();

                        if(freshList.size()!=0)
                        {
                            Collections.sort(freshList, new Comparator<Product>() {
                                @Override
                                public int compare(Product o1, Product o2) {
                                    return o1.getSellingPrice().compareTo(o2.getSellingPrice());
                                }
                            });

                            for(int i=0; i<freshList.size()-1; i++)
                            {
                                for(int j=i+1; j<freshList.size(); j++)
                                {
                                    if (freshList.get(i).getCategoryDetail().equalsIgnoreCase(freshList.get(j).getCategoryDetail()))
                                    {
                                        tempFreshList.add(freshList.get(j));
                                        freshList.remove(j);
                                        j--;
                                    }
                                }
                            }
                            tempPackage.addAll(freshList);

                            while(tempFreshList.size()>0)
                            {
                                for(int i=0; i<freshList.size(); i++)
                                {
                                    for(int j=0; j<tempFreshList.size(); j++)
                                    {
                                        if (freshList.get(i).getCategoryDetail().equalsIgnoreCase(tempFreshList.get(j).getCategoryDetail()))
                                        {
                                            newFreshList = new ArrayList<>();
                                            newFreshList.addAll(freshList);
                                            newFreshList.set(i, tempFreshList.get(j));
                                            tempFreshList.remove(j);
                                        }
                                    }
                                }
                                newFreshPackage.add(newFreshList);
                            }
                        }

                        if(grocList.size()!=0)
                        {
                            Collections.sort(grocList, new Comparator<Product>() {
                                @Override
                                public int compare(Product o1, Product o2) {
                                    return o1.getSellingPrice().compareTo(o2.getSellingPrice());
                                }
                            });

                            for(int i=0; i<grocList.size()-1; i++)
                            {
                                for(int j=i+1; j<grocList.size(); j++)
                                {
                                    if (grocList.get(i).getCategoryDetail().equalsIgnoreCase(grocList.get(j).getCategoryDetail()))
                                    {
                                        tempGrocList.add(grocList.get(j));
                                        grocList.remove(j);
                                        j--;
                                    }
                                }
                            }
                            tempPackage.addAll(grocList);

                            while(tempGrocList.size()>0)
                            {
                                for(int i=0; i<grocList.size(); i++)
                                {
                                    for(int j=0; j<tempGrocList.size(); j++)
                                    {
                                        if (grocList.get(i).getCategoryDetail().equalsIgnoreCase(tempGrocList.get(j).getCategoryDetail()))
                                        {
                                            newGroList = new ArrayList<>();
                                            newGroList.addAll(grocList);
                                            newGroList.set(i, tempGrocList.get(j));
                                            tempGrocList.remove(j);
                                        }
                                    }
                                }
                                newGroPackage.add(newGroList);
                            }
                        }

                        if(bevList.size()!=0)
                        {
                            Collections.sort(bevList, new Comparator<Product>() {
                                @Override
                                public int compare(Product o1, Product o2) {
                                    return o1.getSellingPrice().compareTo(o2.getSellingPrice());
                                }
                            });

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

                            while(tempBevList.size()>0)
                            {
                                for(int i=0; i<bevList.size(); i++)
                                {
                                    for(int j=0; j<tempBevList.size(); j++)
                                    {
                                        if (bevList.get(i).getCategoryDetail().equalsIgnoreCase(tempBevList.get(j).getCategoryDetail()))
                                        {
                                            newBevList = new ArrayList<>();
                                            newBevList.addAll(bevList);
                                            newBevList.set(i, tempBevList.get(j));
                                            tempBevList.remove(j);
                                        }
                                    }
                                }
                                newBevPackage.add(newBevList);
                            }
                        }

                        if(houseList.size()!=0)
                        {
                            Collections.sort(houseList, new Comparator<Product>() {
                                @Override
                                public int compare(Product o1, Product o2) {
                                    return o1.getSellingPrice().compareTo(o2.getSellingPrice());
                                }
                            });

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

                            while(tempHouseList.size()>0)
                            {
                                for(int i=0; i<houseList.size(); i++)
                                {
                                    for(int j=0; j<tempHouseList.size(); j++)
                                    {
                                        if (houseList.get(i).getCategoryDetail().equalsIgnoreCase(tempHouseList.get(j).getCategoryDetail()))
                                        {
                                            newHouseList = new ArrayList<>();
                                            newHouseList.addAll(houseList);
                                            newHouseList.set(i, tempHouseList.get(j));
                                            tempHouseList.remove(j);
                                        }
                                    }
                                }
                                newHousePackage.add(newHouseList);
                            }
                        }

                        if(pCareList.size()!=0)
                        {
                            Collections.sort(pCareList, new Comparator<Product>() {
                                @Override
                                public int compare(Product o1, Product o2) {
                                    return o1.getSellingPrice().compareTo(o2.getSellingPrice());
                                }
                            });

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

                            while(tempPCareList.size()>0)
                            {
                                for(int i=0; i<pCareList.size(); i++)
                                {
                                    for(int j=0; j<tempPCareList.size(); j++)
                                    {
                                        if (pCareList.get(i).getCategoryDetail().equalsIgnoreCase(tempPCareList.get(j).getCategoryDetail()))
                                        {
                                            newPCareList = new ArrayList<>();
                                            newPCareList.addAll(pCareList);
                                            newPCareList.set(i, tempPCareList.get(j));
                                            tempPCareList.remove(j);
                                        }
                                    }
                                }
                                newPCarePackage.add(newPCareList);
                            }
                        }

                        if(clothList.size()!=0)
                        {
                            Collections.sort(clothList, new Comparator<Product>() {
                                @Override
                                public int compare(Product o1, Product o2) {
                                    return o1.getSellingPrice().compareTo(o2.getSellingPrice());
                                }
                            });

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

                            while(tempClothList.size()>0)
                            {
                                for(int i=0; i<clothList.size(); i++)
                                {
                                    for(int j=0; j<tempClothList.size(); j++)
                                    {
                                        if (clothList.get(i).getCategoryDetail().equalsIgnoreCase(tempClothList.get(j).getCategoryDetail()))
                                        {
                                            newClothList = new ArrayList<>();
                                            newClothList.addAll(clothList);
                                            newClothList.set(i, tempClothList.get(j));
                                            tempClothList.remove(j);
                                        }
                                    }
                                }
                                newClothPackage.add(newClothList);
                            }
                        }

                        Collections.sort(tempPackage, new Comparator<Product>() {
                            @Override
                            public int compare(Product o1, Product o2) {
                                return o1.getCategoryDetail().compareTo(o2.getCategoryDetail());
                            }
                        });
                        packageData.add(tempPackage);

                        if(newFreshPackage.size()!=0 || newGroPackage.size()!=0 || newBevPackage.size()!=0 || newHousePackage.size()!=0 || newPCarePackage.size()!=0 || newClothPackage.size()!=0)
                        {
                            if(newFreshPackage.size()>=newGroPackage.size() && newFreshPackage.size()>=newBevPackage.size() && newFreshPackage.size()>newHousePackage.size() && newFreshPackage.size()>newPCarePackage.size() && newFreshPackage.size()>newClothPackage.size() )
                            {
                                for(int i=0; i<newFreshPackage.size(); i++)
                                {
                                    if(newGroPackage.size()!=0)
                                    {
                                        for(int j=0; j<newGroPackage.size(); j++)
                                        {
                                            newFreshPackage.get(i).addAll(newGroPackage.get(j));
                                        }
                                    }
                                    else
                                        newFreshPackage.get(i).addAll(grocList);

                                    if(newBevPackage.size()!=0)
                                    {
                                        for(int j=0; j<newBevPackage.size(); j++)
                                        {
                                            newFreshPackage.get(i).addAll(newBevPackage.get(j));
                                        }
                                    }
                                    else
                                        newFreshPackage.get(i).addAll(bevList);

                                    if(newHousePackage.size()!=0)
                                    {
                                        for(int j=0; j<newHousePackage.size(); j++)
                                        {
                                            newFreshPackage.get(i).addAll(newHousePackage.get(j));
                                        }
                                    }
                                    else
                                        newFreshPackage.get(i).addAll(houseList);

                                    if(newPCarePackage.size()!=0)
                                    {
                                        for(int j=0; j<newPCarePackage.size(); j++)
                                        {
                                            newFreshPackage.get(i).addAll(newPCarePackage.get(j));
                                        }
                                    }
                                    else
                                        newFreshPackage.get(i).addAll(pCareList);

                                    if(newClothPackage.size()!=0)
                                    {
                                        for(int j=0; j<newClothPackage.size(); j++)
                                        {
                                            newFreshPackage.get(i).addAll(newClothPackage.get(j));
                                        }
                                    }
                                    else
                                        newFreshPackage.get(i).addAll(clothList);
                                }
                                packageData.addAll(newFreshPackage);
                            }
                            else if(newGroPackage.size()>=newFreshPackage.size() && newGroPackage.size()>=newBevPackage.size() && newGroPackage.size()>newHousePackage.size() && newGroPackage.size()>newPCarePackage.size() && newGroPackage.size()>newClothPackage.size() )
                            {
                                for(int i=0; i<newGroPackage.size(); i++)
                                {
                                    if(newFreshPackage.size()!=0)
                                    {
                                        for(int j=0; j<newFreshPackage.size(); j++)
                                        {
                                            newGroPackage.get(i).addAll(newFreshPackage.get(j));
                                        }
                                    }
                                    else
                                        newGroPackage.get(i).addAll(freshList);


                                    if(newBevPackage.size()!=0)
                                    {
                                        for(int j=0; j<newBevPackage.size(); j++)
                                        {
                                            newGroPackage.get(i).addAll(newBevPackage.get(j));
                                        }
                                    }
                                    else
                                        newGroPackage.get(i).addAll(bevList);

                                    if(newHousePackage.size()!=0)
                                    {
                                        for(int j=0; j<newHousePackage.size(); j++)
                                        {
                                            newGroPackage.get(i).addAll(newHousePackage.get(j));
                                        }
                                    }
                                    else
                                        newGroPackage.get(i).addAll(houseList);

                                    if(newPCarePackage.size()!=0)
                                    {
                                        for(int j=0; j<newPCarePackage.size(); j++)
                                        {
                                            newGroPackage.get(i).addAll(newPCarePackage.get(j));
                                        }
                                    }
                                    else
                                        newGroPackage.get(i).addAll(pCareList);

                                    if(newClothPackage.size()!=0)
                                    {
                                        for(int j=0; j<newClothPackage.size(); j++)
                                        {
                                            newGroPackage.get(i).addAll(newClothPackage.get(j));
                                        }
                                    }
                                    else
                                        newGroPackage.get(i).addAll(clothList);
                                }
                                packageData.addAll(newGroPackage);
                            }
                            else if(newBevPackage.size()>=newFreshPackage.size() && newBevPackage.size()>=newGroPackage.size() && newBevPackage.size()>newHousePackage.size() && newBevPackage.size()>newPCarePackage.size() && newBevPackage.size()>newClothPackage.size() )
                            {
                                for(int i=0; i<newBevPackage.size(); i++)
                                {
                                    if(newFreshPackage.size()!=0)
                                    {
                                        for(int j=0; j<newFreshPackage.size(); j++)
                                        {
                                            newBevPackage.get(i).addAll(newFreshPackage.get(j));
                                        }
                                    }
                                    else
                                        newBevPackage.get(i).addAll(freshList);


                                    if(newGroPackage.size()!=0)
                                    {
                                        for(int j=0; j<newGroPackage.size(); j++)
                                        {
                                            newBevPackage.get(i).addAll(newGroPackage.get(j));
                                        }
                                    }
                                    else
                                        newBevPackage.get(i).addAll(grocList);

                                    if(newHousePackage.size()!=0)
                                    {
                                        for(int j=0; j<newHousePackage.size(); j++)
                                        {
                                            newBevPackage.get(i).addAll(newHousePackage.get(j));
                                        }
                                    }
                                    else
                                        newBevPackage.get(i).addAll(houseList);

                                    if(newPCarePackage.size()!=0)
                                    {
                                        for(int j=0; j<newPCarePackage.size(); j++)
                                        {
                                            newBevPackage.get(i).addAll(newPCarePackage.get(j));
                                        }
                                    }
                                    else
                                        newBevPackage.get(i).addAll(pCareList);

                                    if(newClothPackage.size()!=0)
                                    {
                                        for(int j=0; j<newClothPackage.size(); j++)
                                        {
                                            newBevPackage.get(i).addAll(newClothPackage.get(j));
                                        }
                                    }
                                    else
                                        newBevPackage.get(i).addAll(clothList);
                                }
                                packageData.addAll(newBevPackage);
                            }
                            else if(newHousePackage.size()>=newFreshPackage.size() && newHousePackage.size()>=newBevPackage.size() && newHousePackage.size()>newGroPackage.size() && newHousePackage.size()>newPCarePackage.size() && newHousePackage.size()>newClothPackage.size() )
                            {
                                for(int i=0; i<newHousePackage.size(); i++)
                                {
                                    if(newFreshPackage.size()!=0)
                                    {
                                        for(int j=0; j<newFreshPackage.size(); j++)
                                        {
                                            newHousePackage.get(i).addAll(newFreshPackage.get(j));
                                        }
                                    }
                                    else
                                        newHousePackage.get(i).addAll(freshList);


                                    if(newBevPackage.size()!=0)
                                    {
                                        for(int j=0; j<newBevPackage.size(); j++)
                                        {
                                            newHousePackage.get(i).addAll(newBevPackage.get(j));
                                        }
                                    }
                                    else
                                        newHousePackage.get(i).addAll(bevList);

                                    if(newGroPackage.size()!=0)
                                    {
                                        for(int j=0; j<newGroPackage.size(); j++)
                                        {
                                            newHousePackage.get(i).addAll(newGroPackage.get(j));
                                        }
                                    }
                                    else
                                        newHousePackage.get(i).addAll(grocList);

                                    if(newPCarePackage.size()!=0)
                                    {
                                        for(int j=0; j<newPCarePackage.size(); j++)
                                        {
                                            newHousePackage.get(i).addAll(newPCarePackage.get(j));
                                        }
                                    }
                                    else
                                        newHousePackage.get(i).addAll(pCareList);

                                    if(newClothPackage.size()!=0)
                                    {
                                        for(int j=0; j<newClothPackage.size(); j++)
                                        {
                                            newHousePackage.get(i).addAll(newClothPackage.get(j));
                                        }
                                    }
                                    else
                                        newHousePackage.get(i).addAll(clothList);
                                }
                                packageData.addAll(newHousePackage);
                            }
                            else if(newPCarePackage.size()>=newFreshPackage.size() && newPCarePackage.size()>=newBevPackage.size() && newPCarePackage.size()>newHousePackage.size() && newPCarePackage.size()>newGroPackage.size() && newPCarePackage.size()>newClothPackage.size() )
                            {
                                for(int i=0; i<newPCarePackage.size(); i++)
                                {
                                    if(newFreshPackage.size()!=0)
                                    {
                                        for(int j=0; j<newFreshPackage.size(); j++)
                                        {
                                            newPCarePackage.get(i).addAll(newFreshPackage.get(j));
                                        }
                                    }
                                    else
                                        newPCarePackage.get(i).addAll(freshList);


                                    if(newBevPackage.size()!=0)
                                    {
                                        for(int j=0; j<newBevPackage.size(); j++)
                                        {
                                            newPCarePackage.get(i).addAll(newBevPackage.get(j));
                                        }
                                    }
                                    else
                                        newPCarePackage.get(i).addAll(bevList);

                                    if(newGroPackage.size()!=0)
                                    {
                                        for(int j=0; j<newGroPackage.size(); j++)
                                        {
                                            newPCarePackage.get(i).addAll(newGroPackage.get(j));
                                        }
                                    }
                                    else
                                        newPCarePackage.get(i).addAll(grocList);

                                    if(newHousePackage.size()!=0)
                                    {
                                        for(int j=0; j<newHousePackage.size(); j++)
                                        {
                                            newPCarePackage.get(i).addAll(newHousePackage.get(j));
                                        }
                                    }
                                    else
                                        newPCarePackage.get(i).addAll(houseList);

                                    if(newClothPackage.size()!=0)
                                    {
                                        for(int j=0; j<newClothPackage.size(); j++)
                                        {
                                            newPCarePackage.get(i).addAll(newClothPackage.get(j));
                                        }
                                    }
                                    else
                                        newPCarePackage.get(i).addAll(clothList);
                                }
                                packageData.addAll(newPCarePackage);
                            }
                            else
                            {
                                for(int i=0; i<newClothPackage.size(); i++)
                                {
                                    if(newFreshPackage.size()!=0)
                                    {
                                        for(int j=0; j<newFreshPackage.size(); j++)
                                        {
                                            newClothPackage.get(i).addAll(newFreshPackage.get(j));
                                        }
                                    }
                                    else
                                        newClothPackage.get(i).addAll(freshList);


                                    if(newBevPackage.size()!=0)
                                    {
                                        for(int j=0; j<newBevPackage.size(); j++)
                                        {
                                            newClothPackage.get(i).addAll(newBevPackage.get(j));
                                        }
                                    }
                                    else
                                        newClothPackage.get(i).addAll(bevList);

                                    if(newGroPackage.size()!=0)
                                    {
                                        for(int j=0; j<newGroPackage.size(); j++)
                                        {
                                            newClothPackage.get(i).addAll(newGroPackage.get(j));
                                        }
                                    }
                                    else
                                        newClothPackage.get(i).addAll(grocList);

                                    if(newHousePackage.size()!=0)
                                    {
                                        for(int j=0; j<newHousePackage.size(); j++)
                                        {
                                            newClothPackage.get(i).addAll(newHousePackage.get(j));
                                        }
                                    }
                                    else
                                        newClothPackage.get(i).addAll(houseList);

                                    if(newClothPackage.size()!=0)
                                    {
                                        for(int j=0; j<newPCarePackage.size(); j++)
                                        {
                                            newClothPackage.get(i).addAll(newPCarePackage.get(j));
                                        }
                                    }
                                    else
                                        newClothPackage.get(i).addAll(pCareList);
                                }
                                packageData.addAll(newClothPackage);
                            }
                        }

                        float[] totalPrice = new float[packageData.size()];

                        for(int i=0; i<packageData.size(); i++)
                        {
                            List<Product> packageDataList = new ArrayList<>();
                            packageDataList.addAll(packageData.get(i));
                            for(int j=0; j<packageDataList.size(); j++)
                                totalPrice[i]+=packageDataList.get(j).getSellingPrice();
                        }
                        System.out.println(packageData);
                        for(int j=0; j<packageData.size()-1; j++)
                        {
                            for(int k=j+1; k<packageData.size(); k++)
                            {
                                if (totalPrice[k]<totalPrice[j])
                                {
                                    List<Product> tempPackageData = new ArrayList<>();
                                    tempPackageData.addAll(packageData.get(j));
                                    packageData.remove(j);
                                    packageData.add(k,tempPackageData);
                                }
                            }
                        }
                        System.out.println(packageData);
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